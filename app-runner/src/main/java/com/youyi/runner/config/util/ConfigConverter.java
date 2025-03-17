package com.youyi.runner.config.util;

import com.youyi.common.util.CommonOperationUtil;
import com.youyi.domain.conf.model.ConfigDO;
import com.youyi.runner.config.model.ConfigInfoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/31
 */
@Mapper(
    imports = {
        CommonOperationUtil.class
    }
)
public interface ConfigConverter {

    ConfigConverter CONFIG_CONVERTER = Mappers.getMapper(ConfigConverter.class);

    @Mappings({
        @Mapping(target = "lastModified", expression = "java(CommonOperationUtil.date2Timestamp(configDO.getGmtModified()))"),
        @Mapping(target = "configType", expression = "java(configDO.getConfigType().name())"),
        @Mapping(target = "deleted", expression = "java(configDO.getDeletedAt() != 0L)")
    })
    ConfigInfoResponse toResponse(ConfigDO configDO);

}
