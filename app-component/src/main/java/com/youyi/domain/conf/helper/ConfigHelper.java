package com.youyi.domain.conf.helper;

import com.youyi.domain.conf.model.ConfigDO;
import com.youyi.infra.conf.repository.ConfigRepository;
import com.youyi.infra.conf.repository.po.ConfigPO;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/30
 */
@Service
@RequiredArgsConstructor
public class ConfigHelper {

    private final ConfigRepository configRepository;

    public void createConfig(ConfigDO configDO) {
        ConfigPO toSaveConfig = configDO.buildToSaveConfig();
        configRepository.insert(toSaveConfig);
    }

    public void queryConfig(ConfigDO configDO) {
        ConfigPO configPO = configRepository.queryByConfigKey(configDO.getConfigKey());
        configDO.fillWithConfigPO(configPO);
    }

    public List<ConfigDO> listConfig(ConfigDO configDO) {
        List<ConfigPO> poList = configRepository.queryByCursor(configDO.getCursor(), configDO.getSize());
        final Long nextCursor = Optional.ofNullable(
            CollectionUtils.isEmpty(poList)
                ? null
                : poList.get(poList.size() - 1).getId()
        ).orElse(Long.MAX_VALUE);
        return poList.stream()
            .map(po -> {
                ConfigDO config = new ConfigDO();
                config.fillWithConfigPO(po);
                config.setCursor(nextCursor);
                return config;
            }).toList();
    }

    public void updateConfig(ConfigDO configDO) {
        ConfigPO configPO = configDO.buildToUpdateOrDeletePO();
        configRepository.updateConfigValueAndEnv(configPO);
    }

    public void deleteConfig(ConfigDO configDO) {
        configDO.delete();
        ConfigPO configPO = configDO.buildToUpdateOrDeletePO();
        configRepository.deleteByConfigKey(configPO);
    }
}
