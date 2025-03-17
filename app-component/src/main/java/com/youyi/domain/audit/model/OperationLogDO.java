package com.youyi.domain.audit.model;

import com.youyi.common.type.OperationType;
import com.youyi.common.util.GsonUtil;
import com.youyi.domain.audit.repository.po.OperationLogPO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/05
 */
@Getter
@Setter
public class OperationLogDO {

    private OperationType operationType;
    private String operatorId;
    private String operatorName;
    private OperationLogExtraData extraData;

    public OperationLogPO buildToSaveOperationLog() {
        OperationLogPO toSaveOperationLog = new OperationLogPO();
        toSaveOperationLog.setOperationType(operationType.name());
        toSaveOperationLog.setOperatorId(operatorId);
        toSaveOperationLog.setOperatorName(operatorName);
        toSaveOperationLog.setExtraData(GsonUtil.toJson(extraData));
        return toSaveOperationLog;
    }

    public void fillWithOperationLogPO(OperationLogPO operationLogPO) {
        this.operationType = OperationType.of(operationLogPO.getOperationType());
        this.operatorId = operationLogPO.getOperatorId();
        this.operatorName = operationLogPO.getOperatorName();
        this.extraData = GsonUtil.fromJson(operationLogPO.getExtraData(), OperationLogExtraData.class);
    }

}
