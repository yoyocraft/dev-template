package com.youyi.common.wrapper;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/05
 */
@Getter
@Setter
public class ThreadPoolConfigWrapper {
    private int corePoolSize;
    private int maximumPoolSize;
    private long keepAliveTime;
    private int queueCapacity;
    private String threadNameFormat;
    /**
     * @see QueueType
     */
    private String queueType;
    /**
     * @see RejectedExecutionHandlerType
     */
    private String rejectedHandlerType;

    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;

    public BlockingQueue<Runnable> getQueue() {
        QueueType type = QueueType.of(queueType);
        switch (type) {
            case LINKED_BLOCKING_QUEUE -> {
                return new LinkedBlockingDeque<>(queueCapacity);
            }
            case PRIORITY_BLOCKING_QUEUE -> {
                return new PriorityBlockingQueue<>(queueCapacity);
            }
            case SYNCHRONOUS_QUEUE -> {
                return new SynchronousQueue<>();
            }
            case LINKED_TRANSFER_QUEUE -> {
                return new LinkedTransferQueue<>();
            }
            default -> {
                return new ArrayBlockingQueue<>(queueCapacity);
            }
        }
    }

    public RejectedExecutionHandler getRejectedHandler() {
        RejectedExecutionHandlerType type = RejectedExecutionHandlerType.of(rejectedHandlerType);
        switch (type) {
            case CALLER_RUNS_POLICY -> {
                return new ThreadPoolExecutor.CallerRunsPolicy();
            }
            case DISCARD_POLICY -> {
                return new ThreadPoolExecutor.DiscardPolicy();
            }
            case DISCARD_OLDEST_POLICY -> {
                return new ThreadPoolExecutor.DiscardOldestPolicy();
            }
            default -> {
                return new ThreadPoolExecutor.AbortPolicy();
            }
        }
    }

    public ThreadFactory getThreadFactory(Logger logger) {
        return new ThreadFactoryBuilder()
            .setNameFormat(threadNameFormat)
            .setUncaughtExceptionHandler((t, e) -> logger.error("Thread {} error", t.getName(), e))
            .build();
    }

    /**
     * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
     * @date 2025/01/05
     */
    public enum QueueType {

        ARRAY_BLOCKING_QUEUE,
        LINKED_BLOCKING_QUEUE,
        PRIORITY_BLOCKING_QUEUE,
        SYNCHRONOUS_QUEUE,
        LINKED_TRANSFER_QUEUE,
        ;

        public static QueueType of(String queueType) {
            return EnumUtils.getEnum(QueueType.class, queueType, ARRAY_BLOCKING_QUEUE);
        }
    }

    /**
     * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
     * @date 2025/01/05
     */
    public enum RejectedExecutionHandlerType {
        ABORT_POLICY,
        CALLER_RUNS_POLICY,
        DISCARD_POLICY,
        DISCARD_OLDEST_POLICY,

        ;

        public static RejectedExecutionHandlerType of(String rejectedHandlerType) {
            return EnumUtils.getEnum(RejectedExecutionHandlerType.class, rejectedHandlerType, ABORT_POLICY);
        }
    }
}
