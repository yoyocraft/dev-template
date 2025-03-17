package com.youyi.common.util;

import com.youyi.common.wrapper.ThreadPoolConfigWrapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/05
 */
class GsonUtilTest {

    @Test
    void testToJson() {
        ThreadPoolConfigWrapper configWrapper = buildThreadPoolConfigWrapper();

        String json = Assertions.assertDoesNotThrow(() -> GsonUtil.toJson(configWrapper));
        Assertions.assertTrue(StringUtils.isNotBlank(json));
        String prettyJson = Assertions.assertDoesNotThrow(() -> GsonUtil.toPrettyJson(configWrapper));
        Assertions.assertTrue(StringUtils.isNotBlank(prettyJson));
        ThreadPoolConfigWrapper wrapper = Assertions.assertDoesNotThrow(() -> GsonUtil.fromJson(json, ThreadPoolConfigWrapper.class));
        Assertions.assertAll(
            () -> Assertions.assertEquals(configWrapper.getCorePoolSize(), wrapper.getCorePoolSize()),
            () -> Assertions.assertEquals(configWrapper.getMaximumPoolSize(), wrapper.getMaximumPoolSize()),
            () -> Assertions.assertEquals(configWrapper.getKeepAliveTime(), wrapper.getKeepAliveTime()),
            () -> Assertions.assertEquals(configWrapper.getQueueCapacity(), wrapper.getQueueCapacity()),
            () -> Assertions.assertEquals(configWrapper.getThreadNameFormat(), wrapper.getThreadNameFormat()),
            () -> Assertions.assertEquals(configWrapper.getQueueType(), wrapper.getQueueType()),
            () -> Assertions.assertEquals(configWrapper.getRejectedHandlerType(), wrapper.getRejectedHandlerType())
        );
    }

    private ThreadPoolConfigWrapper buildThreadPoolConfigWrapper() {
        ThreadPoolConfigWrapper configWrapper = new ThreadPoolConfigWrapper();
        configWrapper.setCorePoolSize(1);
        configWrapper.setMaximumPoolSize(2);
        configWrapper.setKeepAliveTime(100_000L);
        configWrapper.setQueueCapacity(1000);
        configWrapper.setThreadNameFormat("async-record-log-%s");
        configWrapper.setQueueType(ThreadPoolConfigWrapper.QueueType.LINKED_BLOCKING_QUEUE.name());
        configWrapper.setRejectedHandlerType(ThreadPoolConfigWrapper.RejectedExecutionHandlerType.CALLER_RUNS_POLICY.name());
        return configWrapper;
    }

    @Test
    void testFromLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String dateJson = Assertions.assertDoesNotThrow(() -> GsonUtil.toJson(localDateTime));
        Assertions.assertTrue(StringUtils.isNotBlank(dateJson));
    }
}

// Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme