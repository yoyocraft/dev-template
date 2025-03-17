package com.youyi.domain.conf.request;

import com.google.gson.annotations.SerializedName;
import com.youyi.common.base.BaseRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/29
 */
@Getter
@Setter
public class ConfigCreateRequest extends BaseRequest {

    @SerializedName("configKey")
    private String configKey;

    @SerializedName("configValue")
    private String configValue;

    @SerializedName("configType")
    private String configType;

    @SerializedName("configDesc")
    private String configDesc;
}
