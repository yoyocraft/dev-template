package com.youyi.common.util.crypto;

import java.util.Arrays;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkState;

/**
 * AES Initialization Vector Generator
 *
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/04
 */
public class IvGenerator {

    private static final int IV_LENGTH = 16;

    public static String generateIv(String baseStr) {
        checkState(StringUtils.isNotBlank(baseStr), "Input for IV generation cannot be null or empty");

        byte[] hash = DigestUtils.sha256(baseStr);
        byte[] iv = Arrays.copyOf(hash, IV_LENGTH);
        return Base64.encodeBase64String(iv);
    }
}
