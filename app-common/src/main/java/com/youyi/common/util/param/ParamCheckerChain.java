package com.youyi.common.util.param;

import com.youyi.common.exception.AppBizException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BooleanSupplier;
import javax.annotation.Nonnull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/28
 */
public class ParamCheckerChain {

    private final List<Pair<ParamChecker<?>, Object>> paramCheckerChain;

    private ParamCheckerChain(List<Pair<ParamChecker<?>, Object>> paramCheckerChain) {
        this.paramCheckerChain = paramCheckerChain;
    }

    public static ParamCheckerChain newCheckerChain() {
        return new ParamCheckerChain(new ArrayList<>());
    }

    public <T> ParamCheckerChain put(@Nonnull ParamChecker<? super T> checker, T param) {
        paramCheckerChain.add(Pair.of(checker, param));
        return this;
    }

    public <T> ParamCheckerChain putBatch(@Nonnull ParamChecker<? super T> checker, Collection<T> params) {
        if (CollectionUtils.isNotEmpty(params)) {
            params.forEach(param -> paramCheckerChain.add(Pair.of(checker, param)));
        }
        return this;
    }

    public <T> ParamCheckerChain putBatchIf(@Nonnull BooleanSupplier condition, @Nonnull ParamChecker<? super T> checker, Collection<T> params) {
        if (!condition.getAsBoolean()) {
            return this;
        }
        if (CollectionUtils.isNotEmpty(params)) {
            params.forEach(param -> paramCheckerChain.add(Pair.of(checker, param)));
        }
        return this;
    }

    public <T extends CharSequence> ParamCheckerChain putIfNotBlank(@Nonnull ParamChecker<? super T> checker, T param) {
        if (StringUtils.isNotBlank(param)) {
            paramCheckerChain.add(Pair.of(checker, param));
        }
        return this;
    }

    public <T> ParamCheckerChain putIfNotNull(@Nonnull ParamChecker<? super T> checker, T param) {
        if (param != null) {
            paramCheckerChain.add(Pair.of(checker, param));
        }
        return this;
    }

    public <T> ParamCheckerChain putIf(@Nonnull BooleanSupplier condition, @Nonnull ParamChecker<? super T> checker, T param) {
        if (condition.getAsBoolean()) {
            paramCheckerChain.add(Pair.of(checker, param));
        }
        return this;
    }

    public void validateWithThrow() {
        CheckResult result = paramCheckerChain.stream()
            .map(pair -> validateParam(pair.getLeft(), pair.getRight()))
            .filter(ret -> !CheckResult.isSuccess(ret))
            .findFirst()
            .orElseGet(CheckResult::success);

        if (!CheckResult.isSuccess(result)) {
            throw AppBizException.of(result.getCode(), result.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private <T> CheckResult validateParam(ParamChecker<?> checker, Object param) {
        return ((ParamChecker<T>) checker).validateWithLog((T) param);
    }
}
