package com.youyi.infra.conf.core;

import com.youyi.infra.conf.repository.ConfigRepository;
import com.youyi.infra.conf.repository.po.ConfigPO;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.youyi.common.util.ext.MoreFeatures.runWithCost;

/**
 * Config loader using local memory cache
 *
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/01
 */
@Component
@RequiredArgsConstructor
public class ConfigLoader implements SmartInitializingSingleton {

    private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);
    private static final long CONFIG_CACHE_REFRESH_INTERVAL = 10L * 1000;

    private final ConfigRepository configRepository;

    @Override
    public void afterSingletonsInstantiated() {
        refreshCache();
    }

    @Scheduled(initialDelay = CONFIG_CACHE_REFRESH_INTERVAL, fixedDelay = CONFIG_CACHE_REFRESH_INTERVAL)
    public void refreshCache() {
        try {
            runWithCost(logger, this::doRefreshConfigCache, "refreshConfigCache");
        } catch (Exception e) {
            logger.error("[ConfigLoader] refreshCache exp", e);
        }
    }

    private void doRefreshConfigCache() {
        List<ConfigPO> configPOList = configRepository.queryAllConfig();
        if (CollectionUtils.isEmpty(configPOList)) {
            return;
        }

        Map<String, String> configMap = configPOList.stream()
            .collect(
                Collectors.toMap(
                    ConfigPO::getConfigKey,
                    ConfigPO::getConfigValue,
                    (o, n) -> n
                )
            );

        ConfigCache.putAll(configMap);
    }
}
