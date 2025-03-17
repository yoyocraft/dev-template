package com.youyi.domain.conf.request;

import com.google.gson.annotations.SerializedName;
import com.youyi.common.base.BaseRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/30
 */
@Getter
@Setter
public class ConfigUpdateRequest extends BaseRequest {

    @SerializedName("configKey")
    private String configKey;

    @SerializedName("newConfigValue")
    private String newConfigValue;

    @SerializedName("currVersion")
    private Integer currVersion;

    @SerializedName("configDesc")
    private String configDesc;
}
