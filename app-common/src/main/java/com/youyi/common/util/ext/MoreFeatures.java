package com.youyi.common.util.ext;

import com.youyi.common.exception.AppSystemException;
import org.slf4j.Logger;

import static com.youyi.common.type.ReturnCode.UNKNOWN;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/17
 */
public class MoreFeatures {

    public static void runCatching(ThrowingRunnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            throw AppSystemException.of(UNKNOWN, e);
        }
    }

    public static <T> T runCatching(ThrowingSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            throw AppSystemException.of(UNKNOWN, e);
        }
    }

    public static void runWithCost(Logger logger, Runnable action, String loggerMessage) {
        long start = System.currentTimeMillis();
        try {
            action.run();
        } catch (Exception e) {
            logger.error("[runWithCost]{}, execute with error", loggerMessage, e);
            throw e;
        } finally {
            long cost = System.currentTimeMillis() - start;
            logger.info("[runWithCost]{}, cost:{}ms", loggerMessage, cost);
        }
    }
}
