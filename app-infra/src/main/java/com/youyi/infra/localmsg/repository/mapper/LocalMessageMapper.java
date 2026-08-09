package com.youyi.infra.localmsg.repository.mapper;

import com.youyi.infra.localmsg.repository.po.LocalMessagePO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/03/30
 */
// @Mapper
public interface LocalMessageMapper {

    void insert(LocalMessagePO localMessagePO);

    int updateById(LocalMessagePO localMessagePO);

    List<LocalMessagePO> loadWaitRetryRecords(
        @Param("status") List<String> status,
        @Param("nextRetryTime") Long nextRetryTime,
        @Param("retryIntervalMinutes") int retryIntervalMinutes
    );
}
