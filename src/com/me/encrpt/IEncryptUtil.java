package com.me.encrpt;

import java.security.NoSuchAlgorithmException;

/**
 * @author quan
 * @create 2021-04-20 14:32
 */
public interface IEncryptUtil {

    public String symmetricEncrypt(String message, String key) throws Exception;
    public String symmetricDecrypt(String message, String key) throws Exception;

    public String puEncrypt(String message, String key) throws Exception;
    public String prEncrypt(String message, String key) throws Exception;


    public String puDecrypt(String message, String key) throws Exception;
    public String prDecrypt(String message, String key) throws Exception;

    public String getPrivateKey();

    public String getPublicKey();

    public String getSymmetricKey();

}
