package com.youyi.infra.conf.core;

import org.apache.commons.lang3.EnumUtils;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/02/02
 */
public enum ConfigType {

    STRING,
    JSON,
    INTEGER,
    BOOLEAN,
    LONG,
    MAP,
    LIST,
    HTML,
    ;

    public static ConfigType of(String type) {
        return EnumUtils.getEnum(ConfigType.class, type, STRING);
    }
}
