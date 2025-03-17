package com.youyi.domain.audit.repository;

import com.youyi.common.exception.AppSystemException;
import com.youyi.common.type.InfraCode;
import com.youyi.common.type.InfraType;
import com.youyi.domain.audit.repository.po.OperationLogPO;
import com.youyi.domain.audit.repository.mapper.OperationLogMapper;
import java.util.List;
import java.util.Objects;
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
 * @date 2025/01/05
 */
@Repository
@RequiredArgsConstructor
public class OperationLogRepository {

    private static final Logger logger = LoggerFactory.getLogger(OperationLogRepository.class);

    private final OperationLogMapper operationLogMapper;

    public void insert(OperationLogPO po) {
        try {
            checkNotNull(po);
            int ret = operationLogMapper.insert(po);
            checkState(ret == SINGLE_DML_AFFECTED_ROWS);
        } catch (Exception e) {
            infraLog(logger, InfraType.MYSQL, InfraCode.MYSQL_ERROR, e);
            throw AppSystemException.of(InfraCode.MYSQL_ERROR, e);
        }
    }

    public List<OperationLogPO> queryByTypeAndOperatorId(String operationType, Long operatorId) {
        try {
            checkState(StringUtils.isNotBlank(operationType) && Objects.nonNull(operatorId));
            return operationLogMapper.queryByOperationTypeAndOperatorId(operationType, operatorId);
        } catch (Exception e) {
            infraLog(logger, InfraType.MYSQL, InfraCode.MYSQL_ERROR, e);
            throw AppSystemException.of(InfraCode.MYSQL_ERROR, e);
        }
    }
}
