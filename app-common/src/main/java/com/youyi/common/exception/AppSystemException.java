package com.youyi.common.exception;

import com.youyi.common.type.ErrorCode;
import com.youyi.common.type.ReturnCode;
import lombok.Getter;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/27
 */
@Getter
public class AppSystemException extends RuntimeException {

    private final String code;
    private final Long timestamp;

    private AppSystemException(String message) {
        super(message);
        this.code = ReturnCode.SYSTEM_ERROR.getCode();
        this.timestamp = System.currentTimeMillis();
    }

    private AppSystemException(String message, Throwable cause) {
        super(message, cause);
        this.code = ReturnCode.SYSTEM_ERROR.getCode();
        this.timestamp = System.currentTimeMillis();
    }

    private AppSystemException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.timestamp = System.currentTimeMillis();
    }

    private AppSystemException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
        this.timestamp = System.currentTimeMillis();
    }

    private AppSystemException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.code = errorCode.getCode();
        this.timestamp = System.currentTimeMillis();
    }


    public static AppSystemException of(String message) {
        return new AppSystemException(message);
    }

    public static AppSystemException of(String message, Throwable cause) {
        return new AppSystemException(message, cause);
    }

    public static AppSystemException of(ErrorCode errorCode) {
        return new AppSystemException(errorCode);
    }

    public static AppSystemException of(ErrorCode errorCode, String message) {
        return new AppSystemException(errorCode, message);
    }

    public static AppSystemException of(ErrorCode errorCode, Throwable cause) {
        return new AppSystemException(errorCode, cause);
    }
}
