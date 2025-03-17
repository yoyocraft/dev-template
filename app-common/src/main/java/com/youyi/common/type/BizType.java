package com.youyi.common.type;

import org.apache.commons.lang3.EnumUtils;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/12
 */
public enum BizType {

    UNKNOWN,
    LOGIN,
    SET_PWD,
    ;

    public static BizType of(String type) {
        return EnumUtils.getEnum(BizType.class, type, UNKNOWN);
    }
}
