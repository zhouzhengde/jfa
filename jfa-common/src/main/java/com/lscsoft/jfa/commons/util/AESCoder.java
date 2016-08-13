package com.lscsoft.jfa.commons.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;

/**
 * AES��ȫ����ͨ���������
 *
 * @author ������
 * @version 1.0
 */
public final class AESCoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(AESCoder.class);

    private AESCoder() {

    }

    static {
        try {
            Security.addProvider(new BouncyCastleProvider());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ��Կ�㷨��
     */
    public static final String KEY_ALGORITHM = "AES";

    /**
     * ����/�����㷨 / ����ģʽ / ��䷽ʽ Java 7֧��PKCS5PADDEING
     */
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";

    /**
     * ת����Կ
     *
     * @param key ��������Կ
     * @return Key ���ܵ���Կ
     * @throws Exception
     */
    private static Key toKey(byte[] key) {
        SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
        return secretKey;
    }

    /**
     * ����
     *
     * @param data �Ƚ�������
     * @param key  ��Կ
     * @return byte[] ���ܵ�����
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        try {
            Key k = toKey(key);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
            cipher.init(Cipher.DECRYPT_MODE, k);
            return cipher.doFinal(data);
        } catch (Exception e) {
            LOGGER.error("[����/����]", e);
            return null;
        }
    }

    /**
     * ����
     *
     * @param data �ȼ�������
     * @param key  ��Կ
     * @return byte[] ���ܺ������
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        try {
            Key k = toKey(key);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, k);
            return cipher.doFinal(data);
        } catch (Exception e) {
            LOGGER.error("[����/����]", e);
            return null;
        }
    }

    /**
     * ������Կ
     *
     * @return byte[] ��������Կ
     * @throws Exception
     */
    public static byte[] initKey() {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            kg.init(128);
            SecretKey secretKey = kg.generateKey();
            return secretKey.getEncoded();

        } catch (Exception e) {
            LOGGER.error("[������Կ]", e);
            return null;
        }
    }

    /**
     * ��ʼ����Կ
     *
     * @return
     * @throws Exception
     */
    public static String initKeyString() {
        return Base64.encodeBase64String(initKey());
    }

    /**
     * ��ȡ��Կ
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] getKey(String key) {
        return Base64.decodeBase64(key);
    }

    /**
     * ����
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, String key) {
        return decrypt(data, getKey(key));
    }

    /**
     * ����
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, String key) {
        return encrypt(data, getKey(key));
    }

    /**
     * ժҪ����
     *
     * @param data
     * @return
     */
    public static String shaHex(byte[] data) {
        return DigestUtils.md5Hex(data);
    }

    /**
     * ��֤
     *
     * @param data
     * @param messageDigest
     * @return
     */
    public static boolean validate(byte[] data, String messageDigest) {
        return messageDigest.equals(shaHex(data));
    }
}
