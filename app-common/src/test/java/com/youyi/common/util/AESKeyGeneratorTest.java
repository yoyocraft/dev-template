package com.youyi.common.util;

import com.youyi.common.util.crypto.AESKeyGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/04
 */
class AESKeyGeneratorTest {

    @Test
    void testGenerateAESKey() {
        String result = Assertions.assertDoesNotThrow(AESKeyGenerator::generateAESKey);
        Assertions.assertEquals(24, result.length());
    }
}

// Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme