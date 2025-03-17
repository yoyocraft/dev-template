package com.youyi.common.util;

import com.youyi.common.exception.AppSystemException;
import com.youyi.common.type.InfraCode;
import com.youyi.common.type.InfraType;
import com.youyi.common.type.ServerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkState;
import static com.youyi.common.constant.LogFormatterConstant.INFRA_LOG_FORMATTER;
import static com.youyi.common.constant.LogFormatterConstant.SERVER_EXP_LOG_FORMATTER;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/29
 */
public class LogUtil {

    private static final Logger utilLogger = LoggerFactory.getLogger(LogUtil.class);

    private LogUtil() {
    }

    public static void infraLog(Logger logger, InfraType infraType, InfraCode infraCode) {
        infraLog(logger, infraType, infraCode, infraCode.getMessage());
    }

    public static void infraLog(Logger logger, InfraType infraType, InfraCode infraCode, String message) {
        infraLog(logger, infraType, infraCode, message, null);
    }

    public static void infraLog(Logger logger, InfraType infraType, InfraCode infraCode, Throwable throwable) {
        infraLog(logger, infraType, infraCode, infraCode.getMessage(), throwable);
    }

    public static void infraLog(Logger logger, InfraType infraType, InfraCode infraCode, String message, Throwable throwable) {
        try {
            checkState(logger != null && infraType != null && infraCode != null);

            if (InfraCode.INFRA_SUCCESS == infraCode) {
                logger.info(INFRA_LOG_FORMATTER, infraType, infraCode.getCode(), message);
                return;
            }
            if (throwable instanceof AppSystemException) {
                logger.warn(INFRA_LOG_FORMATTER, infraType, infraCode.getCode(), throwable.getMessage());
                return;
            }

            logger.error(INFRA_LOG_FORMATTER, infraType, infraCode.getCode(), message, throwable);
        } catch (Exception e) {
            utilLogger.error("[LogUtil] infraLog exp", e);
        }
    }

    public static void serverExpLog(Logger logger, ServerType serverType, String method, String request, Throwable throwable) {

        try {
            checkState(logger != null && serverType != null && method != null && request != null && throwable != null);

            if (throwable instanceof AppSystemException) {
                logger.warn(SERVER_EXP_LOG_FORMATTER, "AppSystemException", serverType, method, request, throwable.getMessage());
                return;
            }

            logger.error(SERVER_EXP_LOG_FORMATTER, "Exception", serverType, method, request, throwable.getMessage());
        } catch (Exception e) {
            utilLogger.error("[LogUtil] serverExpLog exp", e);
        }
    }
}
