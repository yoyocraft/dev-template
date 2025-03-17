package com.youyi.common.type;

import org.apache.commons.lang3.EnumUtils;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/05
 */
public enum OperationType {

    UNKNOWN,

    INSERT_CONFIG,
    UPDATE_CONFIG,
    DELETE_CONFIG,

    USER_LOGIN,
    USER_LOGOUT,
    USER_SET_PASSWORD,
    USER_EDIT_INFO,
    USER_FOLLOW_USER,

    NOTIFY_CAPTCHA,
    VERIFY_CAPTCHA,

    ADD_PERMISSION,
    AUTHORIZE_PERMISSION,
    REVOKE_PERMISSION,

    UPLOAD_IMAGE,

    UGC_PUBLISH,
    UGC_SET_STATUS,
    UGC_DELETE,
    UGC_INTERACT,
    UGC_ADOPT,
    UGC_AI_GENERATE_SUMMARY,

    COMMENTARY_PUBLISH,
    COMMENTARY_DELETE,

    PUBLISH_NOTIFICATION,
    ;

    public static OperationType of(String type) {
        return EnumUtils.getEnum(OperationType.class, type, UNKNOWN);
    }
}
