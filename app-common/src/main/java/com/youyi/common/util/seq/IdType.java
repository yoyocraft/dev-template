package com.youyi.common.util.seq;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/03/10
 */
@Getter
@AllArgsConstructor
public enum IdType {
    USER_ID(1L),
    UGC_ID(2L),
    UGC_CATEGORY_ID(3L),
    UGC_TAG_ID(4L),
    COMMENTARY_ID(5L),
    SYS_TASK_ID(6L),
    NOTIFICATION_ID(7L),
    MEDIA_RESOURCE_KEY(8L),
    MEDIA_RESOURCE_NAME(9L),
    IMAGE_CAPTCHA_ID(10L),
    ;

    private final long workerId;
}
