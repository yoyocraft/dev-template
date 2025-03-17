package com.youyi.infra.conf.core;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/05
 */
public enum ConfigKey {

    // ================ Email ================
    CAPTCHA_EMAIL_TEMPLATE,
    CAPTCHA_EMAIL_TITLE,
    CAPTCHA_PROCESS_CN_TITLE,
    CAPTCHA_EMAIL_SUBJECT,
    MAIL_FROM,
    PLATFORM_RESPONSIBLE_PERSON,

    // ================ ThreadPool ================
    RECORD_OP_LOG_THREAD_POOL_CONFIG,
    AUDIT_UGC_THREAD_POOL_CONFIG,
    UGC_STATISTICS_THREAD_POOL_CONFIG,
    UGC_SYS_TASK_THREAD_POOL_CONFIG,
    NOTIFICATION_THREAD_POOL_CONFIG,

    // ================ Feature AB Switch ================
    SEND_EMAIL_AB_SWITCH,
    QUERY_LOGIN_USER_INFO_FROM_DB_AB_SWITCH,
    AUDIT_IMAGE_AB_SWITCH,

    // ================ Crypto ================
    AES_KEY,
    AES_ALGORITHM,

    // ================ User ================
    DEFAULT_AVATAR,

    // ================ Lock ================
    DEFAULT_LOCAL_LOCK_TIMEOUT,

    // ================ Media ================
    MEDIA_STORAGE_BASE_PATH,
    MEDIA_ACCESS_URL_PREFIX,
    MAX_UPLOAD_FILE_SIZE,

    // ================ System ==================
    DEFAULT_PAGE_SIZE,

    // ================ Ugc ==================
    SYSTEM_PRESET_UGC_CATEGORY,
    SYSTEM_PRESET_UGC_TAG,
    UGC_MAX_TAG_COUNT,
    UGC_TAG_RELATIONSHIP,
    HOT_LIST_TOP_N,
    ATTACHMENT_MAX_COUNT,

    IMAGE_DETECT_API_URL,
    PROB_THRESHOLD,

    // ================ Notification ==================
    NOTIFICATION_SUMMARY_TEMPLATE,

    // ================ AI ==================
    AI_UGC_VIEW_SUMMARY_PROMPT,
    ;

}
