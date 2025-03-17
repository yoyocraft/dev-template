package com.youyi.runner.config.model;

import com.google.gson.annotations.SerializedName;
import com.youyi.common.base.BaseResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/30
 */
@Getter
@Setter
public class ConfigInfoResponse extends BaseResponse {

    @SerializedName("configId")
    private Long configId;

    @SerializedName("configKey")
    private String configKey;

    @SerializedName("configValue")
    private String configValue;

    @SerializedName("version")
    private Integer version;

    @SerializedName("configType")
    private String configType;

    @SerializedName("lastModified")
    private Long lastModified;

    @SerializedName("deleted")
    private Boolean deleted;

    @SerializedName("configDesc")
    private String configDesc;
}
