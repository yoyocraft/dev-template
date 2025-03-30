package com.youyi.infra.localmsg.type;

import org.apache.commons.lang3.EnumUtils;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/03/16
 */
public enum TaskStatus {

    INIT,

    SUCCESS,

    FAIL,

    RETRY,

    ;

    public static TaskStatus of(String status) {
        return EnumUtils.getEnum(TaskStatus.class, status, INIT);
    }
}
