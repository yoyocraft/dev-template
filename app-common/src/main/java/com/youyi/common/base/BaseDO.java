package com.youyi.common.base;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/27
 */
@Getter
@Setter
public class BaseDO {

    private Long id;

    private Long gmtCreate;

    private Long gmtModified;

    private Integer deleted;

    private String extraData;
}
