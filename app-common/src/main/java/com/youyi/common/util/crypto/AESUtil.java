package com.youyi.common.util.crypto;

import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2025/01/04
 */
public class AESUtil {
    private static final String AES = "AES";
    private static final String SYSTEM_AES_KEY = "epcLazOjpG46NK6P5WQx/Q==";
    private static final String SYSTEM_AES_ALGORITHM = "AES/CBC/PKCS5Padding";

    public static String encrypt(String plainText, String iv) throws Exception {
        return encrypt(SYSTEM_AES_KEY, SYSTEM_AES_ALGORITHM, plainText, iv);
    }

    public static String encrypt(String aesKey, String aesAlgo, String plainText, String iv) throws Exception {
        checkState(StringUtils.isNotBlank(aesKey), "AES key cannot be null or empty");
        checkState(StringUtils.isNotBlank(aesAlgo), "AES algorithm cannot be null or empty");
        checkState(StringUtils.isNotBlank(plainText), "Plain text cannot be null or empty");
        checkState(StringUtils.isNotBlank(iv), "IV cannot be null or empty");

        byte[] ivBytes = Base64.decodeBase64(iv);

        // init AES key spec and initialization vector
        SecretKeySpec secretKeySpec = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), AES);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

        // configure encryption mode
        Cipher cipher = Cipher.getInstance(aesAlgo);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        // do encryption
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        return Base64.encodeBase64String(encryptedBytes);
    }

    public static String decrypt(String cipherText, String iv) throws Exception {
        return decrypt(SYSTEM_AES_KEY, SYSTEM_AES_ALGORITHM, cipherText, iv);
    }

    public static String decrypt(String aesKey, String aesAlgo, String cipherText, String iv) throws Exception {
        checkState(StringUtils.isNotBlank(aesKey), "AES key cannot be null or empty");
        checkState(StringUtils.isNotBlank(aesAlgo), "AES algorithm cannot be null or empty");
        checkState(StringUtils.isNotBlank(cipherText), "Cipher text cannot be null or empty");
        checkState(StringUtils.isNotBlank(iv), "IV cannot be null or empty");

        byte[] ivBytes = Base64.decodeBase64(iv);
        byte[] cipherBytes = Base64.decodeBase64(cipherText);

        // init AES key spec and initialization vector
        SecretKeySpec secretKeySpec = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), AES);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

        // configure decryption mode
        Cipher cipher = Cipher.getInstance(aesAlgo);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        // do decryption
        byte[] decryptedBytes = cipher.doFinal(cipherBytes);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
