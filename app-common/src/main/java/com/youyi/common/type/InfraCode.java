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

    AI_ERROR("AI_ERROR", "ai error"),
    MYSQL_ERROR("MYSQL_ERROR", "mysql error"),
    REDIS_ERROR("REDIS_ERROR", "redis error"),
    MONGODB_ERROR("MONGODB_ERROR", "mongodb error"),
    CONFIG_ERROR("CONFIG_ERROR", "config error"),
    ENCRYPT_ERROR("ENCRYPT_ERROR", "encrypt error"),
    DECRYPT_ERROR("DECRYPT_ERROR", "decrypt error"),
    SEND_EMAIL_ERROR("SEND_EMAIL_ERROR", "send email error"),
    ;

    private final String code;
    private final String message;
}
