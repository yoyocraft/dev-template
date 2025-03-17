package com.youyi.runner.aspect;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/08
 */
@Getter
@AllArgsConstructor
public enum AspectOrdered {

    EXCEPTION_HANDLER(1),
    RECORD_OP_LOG(2),
    ;

    private final int order;
}
