package com.youyi.core.config.core;

import com.google.common.collect.ImmutableMap;
import com.youyi.common.constant.SymbolConstant;
import com.youyi.common.type.Env;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/01
 */
class ConfigCacheServiceTest {
    @Mock
    ConfigLoader configLoader;
    @InjectMocks
    ConfigCacheService configCacheService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // init data
        Map<String, String> dataMap = ImmutableMap.of(
            buildCacheKey("STRING_KEY"), "STRING_VALUE",
            buildCacheKey("BOOLEAN_KEY"), "true",
            buildCacheKey("LONG_KEY"), "123",
            buildCacheKey("JSON_KEY"), "{\"key\":\"value\"}",
            buildCacheKey("LIST_KEY"), "[1,2,3]",
            buildCacheKey("MAP_KEY"), "{\"key\":\"value\"}"
        );

        when(configLoader.getCacheRawValue(anyString())).thenAnswer(invocation -> {
            String cacheKey = invocation.getArgument(0, String.class);
            return dataMap.get(cacheKey);
        });
    }

    @Test
    void testGetCacheValue() {
        Boolean booleanConfig = configCacheService.getBooleanConfig("BOOLEAN_KEY");
        Assertions.assertTrue(booleanConfig);

        List<Integer> listConfig = configCacheService.getListConfig("LIST_KEY", Integer.class);
        Assertions.assertTrue(
            listConfig != null
                && listConfig.size() == 3
                && listConfig.get(0) == 1
                && listConfig.get(1) == 2
                && listConfig.get(2) == 3
        );

        Map<String, String> mapConfig = configCacheService.getMapConfig("MAP_KEY", String.class, String.class);
        Assertions.assertTrue(
            mapConfig != null
                && mapConfig.size() == 1
                && "value".equals(mapConfig.get("key"))
        );

        String stringConfig = configCacheService.getStringConfig("STRING_KEY");
        Assertions.assertTrue(StringUtils.isNotBlank(stringConfig) && "STRING_VALUE".equals(stringConfig));

        Long longConfig = configCacheService.getLongConfig("LONG_KEY");
        Assertions.assertTrue(longConfig != null && longConfig == 123L);

        TestEntry entryConfig = configCacheService.getCacheValue("JSON_KEY", TestEntry.class);
        Assertions.assertTrue(entryConfig != null && "value".equals(entryConfig.getKey()));
    }

    @Getter
    @Setter
    static class TestEntry {
        private String key;
    }

    private String buildCacheKey(String key) {
        return key + SymbolConstant.COLON + Env.LOCAL.name();
    }
}

// Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme