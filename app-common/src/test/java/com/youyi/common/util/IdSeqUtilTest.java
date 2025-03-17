package com.youyi.common.util;

import com.youyi.common.util.seq.IdSeqUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/09
 */
class IdSeqUtilTest {

    @Test
    void testGenEmailCaptcha() {
        String result = IdSeqUtil.genEmailCaptcha();
        Assertions.assertTrue(StringUtils.isNotBlank(result));
    }

    @Test
    void testGenUserVerifyCaptchaToken() {
        String result = IdSeqUtil.genUserVerifyCaptchaToken();
        Assertions.assertTrue(StringUtils.isNotBlank(result));
    }
}

// Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme