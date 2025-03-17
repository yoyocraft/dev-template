package com.youyi.domain.audit.helper;

import com.youyi.domain.audit.model.OperationLogDO;
import com.youyi.domain.audit.repository.OperationLogRepository;
import com.youyi.domain.audit.repository.po.OperationLogPO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/05
 */
@Component
@RequiredArgsConstructor
public class OperationLogHelper {

    private final OperationLogRepository operationLogRepository;

    public void recordOperationLog(OperationLogDO operationLogDO) {
        OperationLogPO toSaveOperationLog = operationLogDO.buildToSaveOperationLog();
        operationLogRepository.insert(toSaveOperationLog);
    }

    public List<OperationLogDO> queryByTypeAndOperatorId(String operationType, Long operatorId) {
        List<OperationLogPO> opLogPos = operationLogRepository.queryByTypeAndOperatorId(operationType, operatorId);
        return opLogPos.stream()
            .map(po -> {
                OperationLogDO operationLogDO = new OperationLogDO();
                operationLogDO.fillWithOperationLogPO(po);
                return operationLogDO;
            })
            .collect(Collectors.toList());
    }
}
