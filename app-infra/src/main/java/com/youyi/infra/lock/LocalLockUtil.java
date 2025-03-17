package com.youyi.infra.lock;

import com.youyi.common.constant.SymbolConstant;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.youyi.infra.conf.core.Conf.getLongConfig;
import static com.youyi.infra.conf.core.ConfigKey.DEFAULT_LOCAL_LOCK_TIMEOUT;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/11
 */
public class LocalLockUtil {

    private static final Logger logger = LoggerFactory.getLogger(LocalLockUtil.class);

    private static final long DEFAULT_TIMEOUT = 5000L;

    /**
     * 使用 ConcurrentHashMap 存储按 key 分组的锁
     */
    private static final ConcurrentHashMap<String, ReentrantLock> LOCK_MAP = new ConcurrentHashMap<>();

    public static void runWithLockFailSafe(Runnable runWithLock, Runnable runWithFailedGotLock, String... keys) {
        runWithLockFailSafe(runWithLock, runWithFailedGotLock, getLongConfig(DEFAULT_LOCAL_LOCK_TIMEOUT, DEFAULT_TIMEOUT), keys);
    }

    public static void runWithLockFailSafe(Runnable runWithLock, Runnable runWithFailedGotLock, long timeout, String... keys) {
        if (keys == null || keys.length == 0 && StringUtils.isNoneBlank(keys)) {
            throw new IllegalArgumentException("Keys cannot be null or empty");
        }

        String key = StringUtils.join(keys, SymbolConstant.HASH).toUpperCase(Locale.ROOT);

        ReentrantLock lock = LOCK_MAP.computeIfAbsent(key, k -> new ReentrantLock());
        boolean acquired = false;

        try {
            acquired = lock.tryLock(timeout, TimeUnit.MILLISECONDS);
            if (acquired) {
                runWithLock.run();
            } else {
                runWithFailedGotLock.run();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Interrupted while trying to acquire lock for key: {}", key, e);
            runWithFailedGotLock.run();
        } finally {
            if (acquired) {
                lock.unlock();
            }
            // 避免锁对象过多，清理无用的锁
            LOCK_MAP.computeIfPresent(key, (k, v) -> v.hasQueuedThreads() ? v : null);
        }
    }
}

