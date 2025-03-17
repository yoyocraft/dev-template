package com.youyi.runner.aspect;

import com.youyi.common.base.Result;
import com.youyi.common.exception.AppBizException;
import com.youyi.common.type.RequestState;
import com.youyi.common.type.ServerType;
import com.youyi.common.util.GsonUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import static com.youyi.common.constant.ErrorCodeConstant.SYSTEM_ERROR_RETRY_LATER;
import static com.youyi.common.constant.ErrorCodeConstant.SYSTEM_ERROR_RETRY_LATER_MESSAGE;
import static com.youyi.common.constant.LogFormatterConstant.REQUEST_FAIL_LOG_FORMATTER;
import static com.youyi.common.util.LogUtil.serverExpLog;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/07
 */
@Aspect
@Component
@RequiredArgsConstructor
public class ExceptionHandlerAspect implements Ordered {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAspect.class);

    @Pointcut("execution(* com.youyi.runner.*.api..*.*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object handleControllerExceptions(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            Object[] args = joinPoint.getArgs();
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Result<?> response;
            if (e instanceof AppBizException biz) {
                response = Result.fail(biz.getCode(), biz.getMessage());
            } else {
                response = Result.fail(SYSTEM_ERROR_RETRY_LATER, SYSTEM_ERROR_RETRY_LATER_MESSAGE, RequestState.UNKNOWN);
                serverExpLog(logger, ServerType.HTTP, methodName, GsonUtil.toJson(args), e);
            }
            logger.error(REQUEST_FAIL_LOG_FORMATTER, className, methodName, GsonUtil.toJson(args), GsonUtil.toJson(response), e);
            return response;
        }
    }

    @Override
    public int getOrder() {
        return AspectOrdered.EXCEPTION_HANDLER.getOrder();
    }
}
