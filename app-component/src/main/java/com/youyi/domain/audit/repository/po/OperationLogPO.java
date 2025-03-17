package com.youyi.domain.audit.repository.po;

import com.youyi.common.base.BasePO;
import com.youyi.common.type.OperationType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/05
 */
@Getter
@Setter
public class OperationLogPO extends BasePO {

    /**
     * @see OperationType
     */
    private String operationType;
    private String operatorId;
    private String operatorName;
}
