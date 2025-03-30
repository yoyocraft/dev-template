package com.youyi.common.base;

import com.youyi.common.exception.AppSystemException;
import com.youyi.common.type.InfraCode;
import com.youyi.common.type.InfraType;
import org.slf4j.Logger;

import static com.youyi.common.util.LogUtil.infraLog;

/**
 * 基础仓储类，提供通用的异常处理和日志记录
 *
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/03/25
 */
public abstract class BaseRepository {

    protected abstract Logger getLogger();

    protected abstract InfraType getInfraType();

    protected abstract InfraCode getInfraCode();

    protected <T> T executeWithExceptionHandling(RepositoryOperation<T> operation) {
        try {
            return operation.execute();
        } catch (Exception e) {
            infraLog(getLogger(), getInfraType(), getInfraCode(), e);
            throw AppSystemException.of(getInfraCode(), e);
        }
    }

    protected void executeWithExceptionHandling(VoidRepositoryOperation operation) {
        try {
            operation.execute();
        } catch (Exception e) {
            infraLog(getLogger(), getInfraType(), getInfraCode(), e);
            throw AppSystemException.of(getInfraCode(), e);
        }
    }

    @FunctionalInterface
    public interface RepositoryOperation<T> {
        T execute() throws Exception;
    }

    @FunctionalInterface
    public interface VoidRepositoryOperation {
        void execute() throws Exception;
    }
}