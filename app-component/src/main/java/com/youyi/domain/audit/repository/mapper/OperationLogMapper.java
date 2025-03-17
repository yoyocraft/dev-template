package com.youyi.domain.audit.repository.mapper;

import com.youyi.domain.audit.repository.po.OperationLogPO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/05
 */
@Mapper
public interface OperationLogMapper {

    int insert(OperationLogPO po);

    List<OperationLogPO> queryByOperationTypeAndOperatorId(@Param("operationType") String operationType, @Param("operatorId") Long operatorId);
}
