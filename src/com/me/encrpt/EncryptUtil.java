package com.me.encrpt;

import org.bouncycastle.jcajce.provider.asymmetric.RSA;

import java.security.NoSuchAlgorithmException;

/**
 * @author quan
 * @create 2021-04-20 14:43
 */
public class EncryptUtil implements IEncryptUtil {
    private String privateKey;
    private String publicKey;
    private String symmetricKey;


    public EncryptUtil() {
        try {
            RSAUtil.getKeyPair();
            this.privateKey = RSAUtil.getprivateKey();
            this.publicKey = RSAUtil.getPublicKey();
            this.symmetricKey = AESUtil.getKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPrivateKey() {
        return privateKey;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }


    @Override
    public String getSymmetricKey() {
        return symmetricKey;
    }

    @Override
    public String symmetricEncrypt(String message, String key) throws Exception {
        String encrypt = AESUtil.encrypt(message, key);
        return encrypt;
    }

    @Override
    public String symmetricDecrypt(String message, String key) throws Exception {
        String decrypt = AESUtil.decrypt(message, key);
        return decrypt;
    }

    @Override
    public String puEncrypt(String message, String key) throws Exception {
        String encrypt = RSAUtil.puEncrypt(message, key);
        return encrypt;
    }

    @Override
    public String prEncrypt(String message, String key) throws Exception {
        String encrypt = RSAUtil.prEncrypt(message, key);
        return encrypt;
    }

    @Override
    public String puDecrypt(String message, String key) throws Exception {
        String decrypt = RSAUtil.puDecrypt(message, key);
        return decrypt;
    }

    @Override
    public String prDecrypt(String message, String key) throws Exception {
        String decrypt = RSAUtil.prDecrypt(message, key);
        return decrypt;
    }

    public static void main(String[] args) {
        try {
            RSAUtil.getKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        IEncryptUtil encryptUtil = new EncryptUtil();

        try {
            String encrypt = encryptUtil.puEncrypt("你好 玛卡巴卡 @159666.com", encryptUtil.getPublicKey());
            String decrypt = encryptUtil.prDecrypt(encrypt, encryptUtil.getPrivateKey());
            System.out.println(decrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
