package com.youyi.domain.conf.request;

import com.google.gson.annotations.SerializedName;
import com.youyi.common.base.BaseRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/31
 */
@Getter
@Setter
public class ConfigDeleteRequest extends BaseRequest {

    @SerializedName("configKey")
    private String configKey;
}
