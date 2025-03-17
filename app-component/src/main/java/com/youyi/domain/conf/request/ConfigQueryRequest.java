package com.youyi.domain.conf.request;

import com.google.gson.annotations.SerializedName;
import com.youyi.common.base.BasePageRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/30
 */
@Getter
@Setter
public class ConfigQueryRequest extends BasePageRequest {

    @SerializedName("key")
    private String key;

    @SerializedName("cursor")
    private Long cursor;
}
