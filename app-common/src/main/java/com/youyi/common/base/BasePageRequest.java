package com.youyi.common.base;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/24
 */
@Getter
@Setter
public abstract class BasePageRequest extends BaseRequest {

    @SerializedName("page")
    private Integer page;

    @SerializedName("size")
    private Integer size;
}
