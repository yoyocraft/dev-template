package com.youyi.infra.localmsg.repository;

import com.youyi.common.base.BaseRepository;
import com.youyi.common.type.InfraCode;
import com.youyi.common.type.InfraType;
import com.youyi.infra.localmsg.repository.mapper.LocalMessageMapper;
import com.youyi.infra.localmsg.repository.po.LocalMessagePO;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/03/30
 */
// @Repository
@RequiredArgsConstructor
public class LocalMessageRepository extends BaseRepository {

    private static final Logger logger = LoggerFactory.getLogger(LocalMessageRepository.class);

    private final LocalMessageMapper localMessageMapper;

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

    public void save(LocalMessagePO localMessageDO) {
        executeWithExceptionHandling(() -> localMessageMapper.insert(localMessageDO));
    }

    public void updateById(LocalMessagePO localMessageDO) {
        executeWithExceptionHandling(() -> localMessageMapper.updateById(localMessageDO));
    }

    public List<LocalMessagePO> loadWaitRetryRecords(List<String> status, Long nextRetryTime, int retryIntervalMinutes) {
        checkState(CollectionUtils.isNotEmpty(status) && Objects.nonNull(nextRetryTime));
        return executeWithExceptionHandling(() -> localMessageMapper.loadWaitRetryRecords(status, nextRetryTime, retryIntervalMinutes));
    }

}
