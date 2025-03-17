package com.youyi.infra.conf.repository.po;

import com.youyi.common.base.BasePO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/29
 */
@Getter
@Setter
public class ConfigPO extends BasePO {

    private String configKey;

    private String configValue;

    private String configType;

    private String configDesc;

    private Integer version;
}
