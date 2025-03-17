package com.youyi.common.util;

import com.youyi.common.util.crypto.AESUtil;
import com.youyi.common.util.crypto.IvGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/04
 */
class AESUtilTest {

    @Test
    void testEncryptAndDecrypt() throws Exception {
        String email = "codejuzi@test.com";
        String iv = IvGenerator.generateIv(email);

        String cipherEmail = AESUtil.encrypt(email, iv);
        String decryptedEmail = AESUtil.decrypt(cipherEmail, iv);
        Assertions.assertEquals(email, decryptedEmail);
    }
}

// Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme