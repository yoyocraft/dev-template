package com.youyi.core.config.repository;

import com.youyi.common.exception.AppSystemException;
import com.youyi.common.type.InfraCode;
import com.youyi.common.type.InfraType;
import com.youyi.core.config.repository.mapper.ConfigMapper;
import com.youyi.core.config.repository.po.ConfigPO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.youyi.common.constant.RepositoryConstant.SINGLE_DML_AFFECTED_ROWS;
import static com.youyi.common.util.LogUtil.infraLog;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/29
 */
@Repository
@RequiredArgsConstructor
public class ConfigRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigRepository.class);

    private final ConfigMapper configMapper;

    public void insert(ConfigPO po) {
        try {
            checkNotNull(po);
            int ret = configMapper.insert(po);
            checkState(ret == SINGLE_DML_AFFECTED_ROWS);
        } catch (Exception e) {
            infraLog(LOGGER, InfraType.MYSQL, InfraCode.MYSQL_ERROR, e);
            throw AppSystemException.of(InfraCode.MYSQL_ERROR, e);
        }
    }

    public ConfigPO queryByConfigKeyAndEnv(String configKey, String env) {
        try {
            checkState(StringUtils.isNoneBlank(configKey, env));
            return configMapper.queryByConfigKeyAndEnv(configKey, env, false);
        } catch (Exception e) {
            infraLog(LOGGER, InfraType.MYSQL, InfraCode.MYSQL_ERROR, e);
            throw AppSystemException.of(InfraCode.MYSQL_ERROR, e);
        }
    }

    public void updateConfigValueAndEnv(ConfigPO po) {
        try {
            checkNotNull(po);
            configMapper.updateConfigValueAndEnv(po);
        } catch (Exception e) {
            infraLog(LOGGER, InfraType.MYSQL, InfraCode.MYSQL_ERROR, e);
            throw AppSystemException.of(InfraCode.MYSQL_ERROR, e);
        }
    }

    public void deleteByConfigKeyAndEnv(ConfigPO po) {
        try {
            checkNotNull(po);
            configMapper.deleteByConfigKeyAndEnv(po);
        } catch (Exception e) {
            infraLog(LOGGER, InfraType.MYSQL, InfraCode.MYSQL_ERROR, e);
            throw AppSystemException.of(InfraCode.MYSQL_ERROR, e);
        }
    }

    public List<ConfigPO> queryAllConfig() {
        try {
            return configMapper.queryAll(false);
        } catch (Exception e) {
            infraLog(LOGGER, InfraType.MYSQL, InfraCode.MYSQL_ERROR, e);
            throw AppSystemException.of(InfraCode.MYSQL_ERROR, e);
        }
    }

}
