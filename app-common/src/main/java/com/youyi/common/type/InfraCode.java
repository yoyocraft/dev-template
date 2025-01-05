package com.youyi.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/29
 */
@Getter
@AllArgsConstructor
public enum InfraCode implements ErrorCode {
    INFRA_SUCCESS("INFRA_SUCCESS", "Success"),
    MYSQL_ERROR("MYSQL_ERROR", "mysql error"),
    CONFIG_ERROR("CONFIG_ERROR", "config error"),
    ;

    private final String code;
    private final String message;
}
