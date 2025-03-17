package com.youyi.common.util.param;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/09
 */
public class ValidateRegex {

    /**
     * 邮箱正则
     */
    public static final String VALIDATE_EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    /**
     * 六位数字正则，用于验证码的验证
     */
    public static final String VALIDATE_SIX_DIGITS_REGEX = "^\\d{6}$";

    /**
     * 通用ID正则
     */
    public static final String COMMON_NO_REGEX = "^[0-9a-zA-Z\\-]+$";
}
