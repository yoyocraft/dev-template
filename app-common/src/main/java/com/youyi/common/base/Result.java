package com.youyi.common.base;

import com.youyi.common.constant.CommonBizState;
import com.youyi.common.type.ErrorCode;
import com.youyi.common.type.ReturnCode;
import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/27
 */
@Getter
@Setter
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private T data;

    /**
     * {@link ReturnCode}
     */
    private String code;

    private String message;

    private String bizState;

    private Long timestamp;

    private Result(ErrorCode errorCode, String bizState) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.bizState = bizState;
        this.timestamp = System.currentTimeMillis();
    }

    private Result(T data, ErrorCode errorCode, String bizState) {
        this.data = data;
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.bizState = bizState;
        this.timestamp = System.currentTimeMillis();
    }

    private Result(String code, String message, String bizState) {
        this.code = code;
        this.message = message;
        this.bizState = bizState;
        this.timestamp = System.currentTimeMillis();
    }

    private Result(T data, String code, String message, String bizState) {
        this.data = data;
        this.code = code;
        this.message = message;
        this.bizState = bizState;
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> Result<T> of(T data, String code, String message, String bizState) {
        return new Result<>(data, code, message, bizState);
    }

    public static <T> Result<T> of(String code, String message, String bizState) {
        return new Result<>(code, message, bizState);
    }

    public static <T> Result<T> success() {
        return new Result<>(ReturnCode.SUCCESS, CommonBizState.SUCCESS.name());
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data, ReturnCode.SUCCESS, CommonBizState.SUCCESS.name());
    }

    public static <T> Result<T> fail(ErrorCode errorCode) {
        return new Result<>(errorCode, CommonBizState.FAILED.name());
    }

    public static <T> Result<T> fail(String code, String message) {
        return new Result<>(code, message, CommonBizState.FAILED.name());
    }
}
