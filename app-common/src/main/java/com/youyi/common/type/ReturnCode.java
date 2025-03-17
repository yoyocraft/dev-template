package com.youyi.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/27
 */
@Getter
@AllArgsConstructor
public enum ReturnCode implements ErrorCode {

    SUCCESS("SUCCESS", ""),

    SYSTEM_ERROR("SYSTEM_ERROR", "系统繁忙，请稍后再试"),
    INVALID_PARAM("INVALID_PARAM", "参数非法"),
    CAPTCHA_EXPIRED("CAPTCHA_EXPIRED", "验证码过期，请重新获取"),
    CAPTCHA_ERROR("CAPTCHA_ERROR", "验证码错误"),
    ILLEGAL_OPERATION("ILLEGAL_OPERATION", "非法操作"),
    VERIFY_TOKEN_EXPIRED("VERIFY_TOKEN_EXPIRED", "操作时间过长，请重新操作"),
    TOO_MANY_REQUEST("TOO_MANY_REQUEST", "操作频繁，请稍后再试"),

    NOT_LOGIN("NOT_LOGIN", "未登录"),
    USER_NOT_EXIST("USER_NOT_EXIST", "用户不存在，请使用验证码登录（登录即注册）"),
    PASSWORD_ERROR("PASSWORD_ERROR", "密码错误，请重试"),
    PERMISSION_DENIED("PERMISSION_DENIED", "权限不足"),

    OPERATION_DENIED("OPERATION_DENIED", "操作被拒绝"),

    UNKNOWN("UNKNOWN", "未知错误"),
    ;

    private final String code;

    private final String message;
}
