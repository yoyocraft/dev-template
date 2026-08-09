package com.youyi.infra.localmsg.core;

import cn.hutool.core.util.ReflectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.youyi.common.util.GsonUtil;
import com.youyi.infra.localmsg.repository.LocalMessageRepository;
import com.youyi.infra.localmsg.repository.po.LocalMessagePO;
import com.youyi.infra.localmsg.type.TaskStatus;
import com.youyi.infra.tx.MoreTxFeature;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static com.youyi.infra.localmsg.util.LocalMessageUtil.offsetTimestamp;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/03/16
 */
// @Component
@RequiredArgsConstructor
public class LocalMessageService {

    private static final Logger logger = LoggerFactory.getLogger(LocalMessageService.class);
    public static final int RETRY_INTERVAL_MINUTES = 2;
    private static final long RETRY_TASK_INTERVAL = 2L * 60 * 1000;
    private static final long INIT_DELAY_TIME = 5L * 1000;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final List<String> needRetryTaskStatus = Lists.newArrayList(
        TaskStatus.INIT.name(),
        TaskStatus.RETRY.name()
    );

    private final ApplicationContext applicationContext;

    private final LocalMessageRepository localMessageRepository;

    private final ExecutorService localMessageThreadPool;

    // @Scheduled(initialDelay = INIT_DELAY_TIME, fixedRate = 2L * 1000) // for test
    @Scheduled(initialDelay = INIT_DELAY_TIME, fixedRate = RETRY_TASK_INTERVAL)
    public void compensation() {
        logger.info("[local message]compensation start");
        loadWaitRetryRecords().forEach(this::doAsyncInvoke);
    }

    public void invoke(LocalMessagePO localMessagePO, boolean async) {
        save(localMessagePO);
        boolean inTx = TransactionSynchronizationManager.isActualTransactionActive();
        if (inTx) {
            // 解决分布式事务基于此即可
            // 事务中，延迟到事务提交后，手动执行一次被切方法
            // 无需等到下一个定时任务周期再调度
            MoreTxFeature.doAfterTxCompletion(() -> execute(localMessagePO, async));
        } else {
            // 非事务中，立即执行
            execute(localMessagePO, async);
        }
    }

    private void execute(LocalMessagePO localMessageDO, boolean async) {
        if (async) {
            doAsyncInvoke(localMessageDO);
        } else {
            // NOTE: 同步执行可能会出现 JDBC 链接被占用问题
            // org.springframework.transaction.support.AbstractPlatformTransactionManager#processCommit
            doInvoke(localMessageDO);
        }
    }

    public void doAsyncInvoke(LocalMessagePO localMessageDO) {
        localMessageThreadPool.execute(() -> doInvoke(localMessageDO));
    }

    public void doInvoke(LocalMessagePO localMessageDO) {
        String snapshot = localMessageDO.getReqSnapshot();
        if (StringUtils.isBlank(snapshot)) {
            logger.warn("Request snapshot is blank, recordId: {}", localMessageDO.getId());
            invokeFail(localMessageDO, "Request snapshot is blank");
            return;
        }

        InvokeCtx ctx = GsonUtil.fromJson(snapshot, InvokeCtx.class);
        try {
            InvokeStatusHolder.startInvoke();

            Class<?> target = Class.forName(ctx.getClassName());
            Object bean = applicationContext.getBean(target);

            List<Class<?>> paramTypes = getParamTypes(GsonUtil.fromJson(ctx.getParamTypes(), List.class, String.class));
            Method method = ReflectUtil.getMethod(target, ctx.getMethodName(), paramTypes.toArray(new Class[0]));
            Object[] args = getArgs(paramTypes, ctx.getArgs());

            method.invoke(bean, args);

            invokeSuccess(localMessageDO);
        } catch (ClassNotFoundException e) {
            logger.error("Class not found for invocation, className: {}, recordId: {}", ctx.getClassName(), localMessageDO.getId(), e);
            invokeFail(localMessageDO, e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("argument illegal for invocation, methodName: {}, recordId: {}", ctx.getMethodName(), localMessageDO.getId(), e);
            invokeFail(localMessageDO, e.getMessage());
        } catch (InvocationTargetException e) {
            // 这里拿到原始异常
            Throwable cause = e.getCause();
            String errorMsg = cause != null ? cause.getMessage() : "Unknown error";
            logger.error("Invocation failed: {}.{}, recordId: {}, cause: {}",
                ctx.getClassName(), ctx.getMethodName(), localMessageDO.getId(), errorMsg, cause);
            retry(localMessageDO, errorMsg);
        } catch (Throwable e) {
            logger.error("Invocation failed, recordId: {}, error: {}", localMessageDO.getId(), e.getMessage(), e);
            retry(localMessageDO, e.getMessage());
        } finally {
            InvokeStatusHolder.endInvoke();
        }
    }

    public void save(LocalMessagePO localMessageDO) {
        localMessageRepository.save(localMessageDO);
    }

    private List<LocalMessagePO> loadWaitRetryRecords() {
        // 查询 RETRY_INTERVAL_MINUTES 分钟之前需要重试的任务
        // 加入 create_time 查询条件，避免刚入库的数据被查询出来
        return localMessageRepository.loadWaitRetryRecords(
            needRetryTaskStatus,
            System.currentTimeMillis(),
            RETRY_INTERVAL_MINUTES
            // 0 // for test
        );
    }

    private void retry(LocalMessagePO localMessageDO, String errorMsg) {
        Integer retryTimes = localMessageDO.getRetryTimes() + 1;
        LocalMessagePO updateDO = new LocalMessagePO();
        updateDO.setId(localMessageDO.getId());
        updateDO.setFailReason(errorMsg);
        updateDO.setNextRetryTime(getNextRetryTime(retryTimes));
        if (retryTimes >= localMessageDO.getMaxRetryTimes()) {
            updateDO.setStatus(TaskStatus.FAIL.name());
        } else {
            updateDO.setRetryTimes(retryTimes);
            updateDO.setStatus(TaskStatus.RETRY.name());
        }
        localMessageRepository.updateById(updateDO);
    }

    public void invokeSuccess(LocalMessagePO localMessageDO) {
        localMessageDO.setStatus(TaskStatus.SUCCESS.name());
        localMessageRepository.updateById(localMessageDO);
    }

    public void invokeFail(LocalMessagePO localMessageDO, String errorMsg) {
        localMessageDO.setStatus(TaskStatus.FAIL.name());
        localMessageDO.setFailReason(errorMsg);
        localMessageRepository.updateById(localMessageDO);
    }

    private List<Class<?>> getParamTypes(List<String> params) {
        return params.stream()
            .map(name -> {
                try {
                    return Class.forName(name);
                } catch (ClassNotFoundException e) {
                    logger.warn("Parameter class not found: {}", name, e);
                    throw new IllegalArgumentException("Parameter class not found: " + name, e);
                }
            })
            .collect(Collectors.toList());
    }

    private Object[] getArgs(List<Class<?>> paramTypes, String argsJson) throws IOException {
        List<Object> args = GsonUtil.fromJson(argsJson, List.class, Object.class);
        return IntStream.range(0, paramTypes.size())
            .mapToObj(i -> convertArg(paramTypes.get(i), args.get(i)))
            .toArray();
    }

    private Object convertArg(Class<?> targetType, Object arg) {
        if (targetType.isAssignableFrom(arg.getClass())) {
            return arg;
        }
        try {
            byte[] paramBytes = objectMapper.writeValueAsBytes(arg);
            return objectMapper.readValue(paramBytes, targetType);
        } catch (IOException e) {
            logger.error("Failed to convert argument: {}", arg, e);
            throw new IllegalArgumentException("Argument conversion failed", e);
        }
    }

    /**
     * 根据重试次数计算下一次重试的时间
     * 本方法通过重试次数计算出需要等待的分钟数，从而确定下一次重试的具体时间
     * 这是为了避免短时间内多次重试，通过增加等待时间来逐步减少重试频率
     *
     * @param retryTimes 重试次数，用于计算等待时间
     * @return 下一次重试的时间
     */
    private Long getNextRetryTime(Integer retryTimes) {
        // 计算等待分钟数，使用重试间隔的指数增长来增加等待时间
        double waitMinutes = Math.pow(RETRY_INTERVAL_MINUTES, retryTimes);
        return offsetTimestamp((long) waitMinutes);
    }
}
