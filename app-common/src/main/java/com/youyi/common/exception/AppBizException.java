package com.youyi.common.exception;

import com.youyi.common.type.ErrorCode;
import lombok.Getter;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/27
 */
@Getter
public class AppBizException extends RuntimeException {

    private final String code;
    private final Long timestamp;

    private AppBizException(String code, String message) {
        super(message);
        this.code = code;
        this.timestamp = System.currentTimeMillis();
    }

    private AppBizException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.timestamp = System.currentTimeMillis();
    }

    private AppBizException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.timestamp = System.currentTimeMillis();
    }

    private AppBizException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
        this.timestamp = System.currentTimeMillis();
    }

    private AppBizException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.code = errorCode.getCode();
        this.timestamp = System.currentTimeMillis();
    }


    public static AppBizException of(String code, String message) {
        return new AppBizException(code, message);
    }

    public static AppBizException of(String code, String message, Throwable cause) {
        return new AppBizException(code, message, cause);
    }

    public static AppBizException of(ErrorCode errorCode) {
        return new AppBizException(errorCode);
    }

    public static AppBizException of(ErrorCode errorCode, String message) {
        return new AppBizException(errorCode, message);
    }

    public static AppBizException of(ErrorCode errorCode, Throwable cause) {
        return new AppBizException(errorCode, cause);
    }
}
