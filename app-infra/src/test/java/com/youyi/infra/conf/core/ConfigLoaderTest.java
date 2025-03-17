package com.youyi.infra.conf.core;

import com.google.common.collect.ImmutableMap;
import com.youyi.infra.conf.repository.ConfigRepository;
import com.youyi.infra.conf.repository.po.ConfigPO;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.doReturn;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/01
 */
class ConfigLoaderTest {

    @Mock
    ConfigRepository configRepository;
    @InjectMocks
    ConfigLoader configLoader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRefreshCache() {
        doReturn(buildConfigs()).when(configRepository).queryAllConfig();
        Assertions.assertDoesNotThrow(() -> configLoader.refreshCache());
    }

    List<ConfigPO> buildConfigs() {
        Map<String, String> dataMap = ImmutableMap.of(
            "STRING_KEY", "STRING_VALUE",
            "BOOLEAN_KEY", "true",
            "LONG_KEY", "123",
            "JSON_KEY", "{\"key\":\"value\"}",
            "LIST_KEY", "[1,2,3]",
            "MAP_KEY", "{\"key\":\"value\"}"
        );

        return dataMap.entrySet().stream()
            .map(entry -> buildConfigPO(entry.getKey(), entry.getValue()))
            .toList();
    }

    ConfigPO buildConfigPO(String key, String value) {
        ConfigPO configPO = new ConfigPO();
        configPO.setConfigKey(key);
        configPO.setConfigValue(value);
        configPO.setVersion(0);
        return configPO;
    }
}

// Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme