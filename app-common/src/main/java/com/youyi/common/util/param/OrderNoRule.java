package com.youyi.common.util.param;

import com.google.common.base.Suppliers;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

import static com.youyi.common.util.param.ValidateRegex.COMMON_NO_REGEX;
import static com.youyi.common.util.param.ValidateRegex.VALIDATE_EMAIL_REGEX;
import static com.youyi.common.util.param.ValidateRegex.VALIDATE_SIX_DIGITS_REGEX;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/02/13
 */
public enum OrderNoRule {

    EMAIL_RULE(
        Suppliers.memoize(() -> Pattern.compile(VALIDATE_EMAIL_REGEX)),
        32
    ),

    COMMON_ID_RULE_64(64),

    CAPTCHA_RULE(
        Suppliers.memoize(() -> Pattern.compile(VALIDATE_SIX_DIGITS_REGEX)),
        6
    ),

    ;

    private Supplier<Pattern> patternSupplier = Suppliers.memoize(() -> Pattern.compile(COMMON_NO_REGEX));

    private final int maxLength;

    OrderNoRule(int maxLength) {
        this.maxLength = maxLength;
    }

    OrderNoRule(Supplier<Pattern> patternSupplier, int maxLength) {
        this.patternSupplier = patternSupplier;
        this.maxLength = maxLength;
    }

    public boolean isValid(String orderNo) {
        return isValid(orderNo, patternSupplier.get(), maxLength);
    }

    private boolean isValid(String orderNo, Pattern pattern, int maxLength) {
        return StringUtils.isNotBlank(orderNo)
            && orderNo.length() <= maxLength
            && isMatchRule(orderNo, pattern);
    }

    private boolean isMatchRule(String orderNo, Pattern pattern) {
        return pattern.matcher(orderNo).matches();
    }

}
