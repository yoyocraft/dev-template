package com.youyi.infra.localmsg.repository.po;

import com.youyi.common.base.BasePO;
import com.youyi.infra.localmsg.type.TaskStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocalMessagePO extends BasePO {

    private String reqSnapshot;

    private String status;

    private Long nextRetryTime;

    private Integer retryTimes;

    private Integer maxRetryTimes;

    private String failReason;

    private String bizCode;

    public LocalMessagePO() {
    }

    public LocalMessagePO(String bizCode, String reqSnapshot, Integer maxRetryTimes, Long nextRetryTime) {
        this.status = TaskStatus.INIT.name();
        this.bizCode = bizCode;
        this.reqSnapshot = reqSnapshot;
        this.maxRetryTimes = maxRetryTimes;
        this.nextRetryTime = nextRetryTime;
        this.retryTimes = 0;
    }

}