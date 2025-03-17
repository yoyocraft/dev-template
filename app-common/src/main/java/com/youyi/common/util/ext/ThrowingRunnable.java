package com.youyi.common.util.ext;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/17
 */
@FunctionalInterface
public interface ThrowingRunnable {
    void run() throws Exception;
}