package com.youyi.infra.localmsg.core;

import com.youyi.common.util.GsonUtil;
import com.youyi.infra.localmsg.repository.po.LocalMessagePO;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.youyi.infra.localmsg.core.LocalMessageService.RETRY_INTERVAL_MINUTES;
import static com.youyi.infra.localmsg.util.LocalMessageUtil.offsetTimestamp;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/03/16
 */
@Aspect
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LocalMessageAspect {

    private static final Logger logger = LoggerFactory.getLogger(LocalMessageAspect.class);
    private final LocalMessageService localMessageService;

    @Around("@annotation(com.youyi.infra.localmsg.core.LocalMessage)")
    public Object doAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        LocalMessage localMessage = AnnotationUtils.findAnnotation(method, LocalMessage.class);
        if (Objects.isNull(localMessage)) {
            return joinPoint.proceed();
        }
        // 正在执行
        if (InvokeStatusHolder.inInvoke()) {
            return joinPoint.proceed();
        }
        boolean async = localMessage.async();
        String bizCode = localMessage.bizCode();
        // 方法参数
        List<String> params = Arrays.stream(method.getParameterTypes())
            .map(Class::getName)
            .collect(Collectors.toList());
        // 记录执行上下文
        InvokeCtx ctx = InvokeCtx.builder()
            .className(method.getDeclaringClass().getName())
            .methodName(method.getName())
            .paramTypes(GsonUtil.toJson(params))
            .args(GsonUtil.toJson(joinPoint.getArgs()))
            .build();
        LocalMessagePO localMessagePO = new LocalMessagePO(
            bizCode,
            GsonUtil.toJson(ctx),
            localMessage.maxRetryTimes(),
            offsetTimestamp(RETRY_INTERVAL_MINUTES)
        );
        logger.info("record local message, record: {}, async: {}", GsonUtil.toJson(localMessagePO), async);
        localMessageService.invoke(localMessagePO, async);
        // 被切方法的逻辑交给 Task 去执行，这里返回 null
        return null;
    }
}
