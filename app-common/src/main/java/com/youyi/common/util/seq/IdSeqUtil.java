package com.youyi.common.util.seq;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import java.util.EnumMap;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/09
 */
public class IdSeqUtil {

    // ============================= Snowflake Instances ==============================
    private static final EnumMap<IdType, Snowflake> SNOWFLAKE_MAP = new EnumMap<>(IdType.class);

    static {
        for (IdType type : IdType.values()) {
            SNOWFLAKE_MAP.put(type, IdUtil.getSnowflake(type.getWorkerId()));
        }
    }

    // ============================= Constants ==============================
    private static final int EMAIL_CAPTCHA_LENGTH = 6;
    private static final int USER_NICKNAME_SUFFIX_LENGTH = 8;
    private static final int USER_VERIFY_CAPTCHA_TOKEN_LENGTH = 64;
    private static final int USER_PWD_SALT_LENGTH = 64;
    private static final String USER_NICK_NAME_PREFIX = "social_x_";

    // ============================= Private Constructor ==============================
    private IdSeqUtil() {
    }

    // ============================= ID Generators ==============================
    public static String genId(IdType idType) {
        return SNOWFLAKE_MAP.get(idType).nextIdStr();
    }

    public static String genUserId() {
        return genId(IdType.USER_ID);
    }

    public static String genUgcId() {
        return genId(IdType.UGC_ID);
    }

    public static String genUgcCategoryId() {
        return genId(IdType.UGC_CATEGORY_ID);
    }

    public static String genUgcTagId() {
        return genId(IdType.UGC_TAG_ID);
    }

    public static String genCommentaryId() {
        return genId(IdType.COMMENTARY_ID);
    }

    public static String genSysTaskId() {
        return genId(IdType.SYS_TASK_ID);
    }

    public static String genNotificationId() {
        return genId(IdType.NOTIFICATION_ID);
    }

    public static String genMediaResourceKey() {
        return genId(IdType.MEDIA_RESOURCE_KEY);
    }

    public static String genMediaResourceName() {
        return genId(IdType.MEDIA_RESOURCE_NAME);
    }

    public static String genImageCaptchaId() {
        return genId(IdType.IMAGE_CAPTCHA_ID);
    }

    // ============================= Random String Generators ==============================
    public static String genEmailCaptcha() {
        return RandomStringUtils.secure().nextNumeric(EMAIL_CAPTCHA_LENGTH);
    }

    public static String genUserNickname() {
        return USER_NICK_NAME_PREFIX + RandomStringUtils.secure().nextNumeric(USER_NICKNAME_SUFFIX_LENGTH);
    }

    public static String genUserVerifyCaptchaToken() {
        return RandomStringUtils.secure().nextAlphanumeric(USER_VERIFY_CAPTCHA_TOKEN_LENGTH);
    }

    public static String genPwdSalt() {
        return RandomStringUtils.secure().nextAlphanumeric(USER_PWD_SALT_LENGTH);
    }
}