package com.youyi.domain.conf.model;

import com.youyi.infra.conf.core.ConfigType;
import com.youyi.infra.conf.repository.po.ConfigPO;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

import static com.youyi.common.constant.RepositoryConstant.INIT_VERSION;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/29
 */
@Getter
@Setter
public class ConfigDO {

    private Long configId;
    private String configKey;
    private String configValue;
    private ConfigType configType;
    private Integer version;
    private String configDesc;
    private Long deletedAt;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    private Long cursor;
    private Integer size;

    public ConfigPO buildToSaveConfig() {
        ConfigPO toSaveConfig = new ConfigPO();
        toSaveConfig.setConfigKey(configKey);
        toSaveConfig.setConfigValue(configValue);
        toSaveConfig.setVersion(INIT_VERSION);
        toSaveConfig.setConfigType(configType.name());
        toSaveConfig.setConfigDesc(configDesc);
        return toSaveConfig;
    }

    public ConfigPO buildToUpdateOrDeletePO() {
        ConfigPO toUpdateConfig = new ConfigPO();
        toUpdateConfig.setId(configId);
        toUpdateConfig.setVersion(version);
        toUpdateConfig.setConfigKey(configKey);
        toUpdateConfig.setConfigValue(configValue);
        toUpdateConfig.setConfigDesc(configDesc);
        toUpdateConfig.setDeletedAt(deletedAt);
        return toUpdateConfig;
    }

    public void delete() {
        this.deletedAt = System.currentTimeMillis();
    }

    public void fillWithConfigPO(ConfigPO configPO) {
        this.configId = configPO.getId();
        this.configKey = configPO.getConfigKey();
        this.configValue = configPO.getConfigValue();
        this.configType = ConfigType.of(configPO.getConfigType());
        this.version = configPO.getVersion();
        this.configDesc = configPO.getConfigDesc();
        this.deletedAt = configPO.getDeletedAt();
        this.gmtCreate = configPO.getGmtCreate();
        this.gmtModified = configPO.getGmtModified();
    }
}
