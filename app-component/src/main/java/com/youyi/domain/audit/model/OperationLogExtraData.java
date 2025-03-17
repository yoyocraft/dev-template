package com.youyi.domain.audit.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/17
 */
@Getter
@Setter
@Builder
public class OperationLogExtraData {

    private String method;
    private String className;
    private String argType;
    private String argName;
    private String argValue;
    private String errorMessage;
    private String returnValue;
}
