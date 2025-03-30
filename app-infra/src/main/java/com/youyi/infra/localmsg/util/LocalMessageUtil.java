package com.youyi.infra.localmsg.util;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/03/16
 */
public class LocalMessageUtil {

    public static Long offsetTimestamp(long minutes) {
        return System.currentTimeMillis() + minutes * 60 * 1000;
    }
}
