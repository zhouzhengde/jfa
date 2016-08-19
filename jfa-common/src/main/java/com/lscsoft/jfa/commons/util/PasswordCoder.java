package com.lscsoft.jfa.commons.util;

import org.apache.commons.codec.binary.Base64;


/**
 * @author Created by Bond(China) on 2016/7/6.
 */
public final class PasswordCoder {

    /**
     * 默认密钥
     */
    private static final String DEFAULT_KEY = "Cxo3IhxN0+IP3sX9qULbcQ==";

    private PasswordCoder() {
    }

    /**
     * 加密
     *
     * @param password Input value
     * @return The result already to encrypt.
     */
    public static String encrypt(String password) {

        byte[] bts = AESCoder.encrypt(Base64.decodeBase64(password), DEFAULT_KEY);
        // 考虑到密码安全性，做完对称加密，需要进一步摘要处理，将其变为不可逆
        return AESCoder.shaHex(Base64.encodeBase64String(bts).getBytes());
    }

    /**
     * 加密
     *
     * @param password Input value
     * @param key      KEY
     * @return The result already to encrypt.
     */
    public static String encrypt(String key, String password) {

        byte[] bts = AESCoder.encrypt(Base64.decodeBase64(password), key);
        // 考虑到密码安全性，做完对称加密，需要进一步摘要处理，将其变为不可逆
        return AESCoder.shaHex(Base64.encodeBase64String(bts).getBytes());
    }

}
