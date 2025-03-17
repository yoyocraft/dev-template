package com.youyi.common.base;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Getter;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/27
 */
@Getter
public class PageResult<T> {

    @SerializedName("total")
    private final Long total;

    @SerializedName("pageNo")
    private final Long pageNo;

    @SerializedName("pageSize")
    private final Long pageSize;

    @SerializedName("data")
    private final List<T> data;

    private PageResult(Long total, Long pageNo, Long pageSize, List<T> data) {
        this.total = total;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.data = data;
    }

    public static <T> PageResult<T> of(Long total, Long pageNo, Long pageSize, List<T> data) {
        return new PageResult<>(total, pageNo, pageSize, data);
    }
}
