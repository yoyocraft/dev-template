package com.youyi.infra.conf.repository;

import com.youyi.common.base.BaseRepository;
import com.youyi.common.type.InfraCode;
import com.youyi.common.type.InfraType;
import com.youyi.infra.conf.repository.mapper.ConfigMapper;
import com.youyi.infra.conf.repository.po.ConfigPO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.youyi.common.constant.RepositoryConstant.SINGLE_DML_AFFECTED_ROWS;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/29
 */
@Repository
@RequiredArgsConstructor
public class ConfigRepository extends BaseRepository {

    private static final Logger logger = LoggerFactory.getLogger(ConfigRepository.class);

    private final ConfigMapper configMapper;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    protected InfraType getInfraType() {
        return InfraType.MYSQL;
    }

    @Override
    protected InfraCode getInfraCode() {
        return InfraCode.MYSQL_ERROR;
    }

    public void insert(ConfigPO po) {
        checkNotNull(po);
        int ret = executeWithExceptionHandling(() -> configMapper.insert(po));
        checkState(ret == SINGLE_DML_AFFECTED_ROWS);
    }

    public ConfigPO queryByConfigKey(String configKey) {
        checkState(StringUtils.isNoneBlank(configKey));
        return executeWithExceptionHandling(() -> configMapper.queryByConfigKey(configKey, false));
    }

    public List<ConfigPO> queryByCursor(Long cursor, Integer size) {
        checkState(cursor != null && size > 0);
        return executeWithExceptionHandling(() -> configMapper.queryByCursor(cursor, size, true));
    }

    public void updateConfigValue(ConfigPO po) {
        checkNotNull(po);
        executeWithExceptionHandling(() -> configMapper.updateConfig(po));
    }

    public void deleteByConfigKey(ConfigPO po) {
        checkNotNull(po);
        executeWithExceptionHandling(() -> configMapper.deleteByConfigKey(po));
    }

    public List<ConfigPO> queryAllConfig() {
        return executeWithExceptionHandling(() -> configMapper.queryAll(false));
    }

}
