package com.youyi.core.config.core;

import com.google.gson.reflect.TypeToken;
import com.youyi.common.constant.SymbolConstant;
import com.youyi.common.exception.AppSystemException;
import com.youyi.common.type.Env;
import com.youyi.common.util.GsonUtil;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.youyi.common.type.InfraCode.CONFIG_ERROR;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/01
 */
@Component
@RequiredArgsConstructor
public class ConfigCacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigCacheService.class);

    private final ConfigLoader configLoader;

    public <T> T getCacheValue(String configKey, Class<T> type) {
        return getCacheValue(configKey, Env.LOCAL.name(), type);
    }

    public <T> T getCacheValue(String configKey, String env, Class<T> type) {
        String key = buildCacheKey(configKey, env);
        String value = configLoader.getCacheRawValue(key);

        String errorMsg = "Config not found for key: " + key + ", env: " + env;
        if (value == null) {
            throw AppSystemException.of(CONFIG_ERROR, errorMsg);
        }

        try {
            if (type.isPrimitive() || type == String.class) {
                return type.cast(value);
            }
            return GsonUtil.fromJson(value, type);
        } catch (Exception e) {
            LOGGER.error("Error parsing cache value for key: {}, expected type: {}", key, type.getName(), e);
            throw AppSystemException.of(CONFIG_ERROR, errorMsg);
        }
    }

    public <T> T getCacheValue(String configKey, Type type) {
        return getCacheValue(configKey, Env.LOCAL.name(), type);
    }

    public <T> T getCacheValue(String configKey, String env, Type type) {
        String key = buildCacheKey(configKey, env);
        String value = configLoader.getCacheRawValue(key);

        String errorMsg = "Config not found for key: " + key + ", env: " + env;
        if (value == null) {
            throw AppSystemException.of(CONFIG_ERROR, errorMsg);
        }

        try {
            return GsonUtil.fromJson(value, type);
        } catch (Exception e) {
            LOGGER.error("Error parsing cache value for key: {}, expected type: {}", key, type.getTypeName(), e);
            throw AppSystemException.of(CONFIG_ERROR, errorMsg);
        }
    }

    public <K, V> Map<K, V> getMapConfig(String configKey, Class<K> keyType, Class<V> valueType) {
        Type mapType = TypeToken.getParameterized(Map.class, keyType, valueType).getType();
        return getCacheValue(configKey, mapType);
    }

    public <T> List<T> getListConfig(String configKey, Class<T> valueType) {
        Type listType = TypeToken.getParameterized(List.class, valueType).getType();
        return getCacheValue(configKey, listType);
    }

    public Boolean getBooleanConfig(String key) {
        return getCacheValue(key, Boolean.class);
    }

    public String getStringConfig(String key) {
        return getCacheValue(key, String.class);
    }

    public Long getLongConfig(String key) {
        return getCacheValue(key, Long.class);
    }

    private String buildCacheKey(String configKey, String env) {
        return configKey + SymbolConstant.COLON + env;
    }
}

