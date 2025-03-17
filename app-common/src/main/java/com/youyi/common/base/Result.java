package com.youyi.common.base;

import com.google.gson.annotations.SerializedName;
import com.youyi.common.type.RequestState;
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

    @SerializedName("data")
    private T data;

    /**
     * {@link ReturnCode}
     */
    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    /**
     * {@link RequestState}
     */
    @SerializedName("bizState")
    private String bizState;

    @SerializedName("timestamp")
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
        return new Result<>(ReturnCode.SUCCESS, RequestState.SUCCESS.name());
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data, ReturnCode.SUCCESS, RequestState.SUCCESS.name());
    }

    public static <T> Result<T> fail(ErrorCode errorCode) {
        return new Result<>(errorCode, RequestState.FAILED.name());
    }

    public static <T> Result<T> fail(String code, String message) {
        return new Result<>(code, message, RequestState.FAILED.name());
    }

    public static <T> Result<T> fail(String code, String message, RequestState state) {
        return new Result<>(code, message, state.name());
    }
}
