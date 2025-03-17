package com.youyi.infra.conf.core;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.Map;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/11
 */
public class ConfigCache {

    private static final int CONFIG_CACHE_SIZE = 10000;

    private static final Cache<String, String> CONFIG_CENTER = CacheBuilder
        .newBuilder()
        .maximumSize(CONFIG_CACHE_SIZE)
        .build();

    private ConfigCache() {
    }

    public static void putAll(Map<String, String> configMap) {
        CONFIG_CENTER.putAll(configMap);
    }

    public static String getCacheRawValue(String key) {
        return CONFIG_CENTER.getIfPresent(key);
    }
}
