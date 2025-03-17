package com.youyi.runner.config.util;

import com.youyi.common.base.PageCursorResult;
import com.youyi.common.base.Result;
import com.youyi.common.util.GsonUtil;
import com.youyi.domain.conf.model.ConfigDO;
import com.youyi.domain.conf.request.ConfigCreateRequest;
import com.youyi.domain.conf.request.ConfigDeleteRequest;
import com.youyi.domain.conf.request.ConfigQueryRequest;
import com.youyi.domain.conf.request.ConfigUpdateRequest;
import com.youyi.runner.config.model.ConfigInfoResponse;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.youyi.infra.conf.util.CommonConfUtil.checkHasMore;
import static com.youyi.runner.config.util.ConfigConverter.CONFIG_CONVERTER;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/30
 */
public class ConfigResponseUtil {

    private static final Logger logger = LoggerFactory.getLogger(ConfigResponseUtil.class);

    public static Result<Boolean> createSuccess(ConfigCreateRequest request) {
        Result<Boolean> response = Result.success(Boolean.TRUE);
        logger.info("create config, request:{}, response: {}", GsonUtil.toJson(request), GsonUtil.toJson(response));
        return response;
    }

    public static Result<ConfigInfoResponse> querySuccess(ConfigDO configDO, ConfigQueryRequest request) {
        Result<ConfigInfoResponse> response = Result.success(CONFIG_CONVERTER.toResponse(configDO));
        logger.info("query config, request:{}, response:{}", GsonUtil.toJson(request), GsonUtil.toJson(response));
        return response;
    }

    public static Result<PageCursorResult<Long, ConfigInfoResponse>> listConfigSuccess(List<ConfigDO> configDOList,
        ConfigQueryRequest request) {
        Long cursor = Optional.ofNullable(configDOList.isEmpty() ? null : configDOList.get(0).getCursor()).orElse(Long.MAX_VALUE);
        List<ConfigInfoResponse> configInfoResponseList = configDOList.stream().map(CONFIG_CONVERTER::toResponse).toList();
        Result<PageCursorResult<Long, ConfigInfoResponse>> response = Result.success(PageCursorResult.of(configInfoResponseList, cursor, checkHasMore(configInfoResponseList)));
        logger.info("query config for main page, request:{}, response:{}", GsonUtil.toJson(request), GsonUtil.toJson(response));
        return response;
    }

    public static Result<Boolean> updateSuccess(ConfigUpdateRequest request) {
        Result<Boolean> response = Result.success(Boolean.TRUE);
        logger.info("update config, request:{}, response:{}", GsonUtil.toJson(request), GsonUtil.toJson(response));
        return response;
    }

    public static Result<Boolean> deleteSuccess(ConfigDeleteRequest request) {
        Result<Boolean> response = Result.success(Boolean.TRUE);
        logger.info("delete config, request:{}, response:{}", GsonUtil.toJson(request), GsonUtil.toJson(response));
        return response;
    }
}
