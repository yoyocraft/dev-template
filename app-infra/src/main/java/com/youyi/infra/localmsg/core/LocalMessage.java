package com.youyi.infra.localmsg.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/03/16
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LocalMessage {

    /**
     * 业务编码，默认为DEFAULT
     */
    String bizCode();

    /**
     * 默认3次
     *
     * @return 最大重试次数(包括第一次正常执行)
     */
    int maxRetryTimes() default 3;

    /**
     * 默认异步执行，先入库，后续异步执行
     *
     * @return 是否异步执行
     */
    boolean async() default true;
}
