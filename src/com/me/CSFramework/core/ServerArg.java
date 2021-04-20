package com.me.CSFramework.core;

import com.me.util.AESUtil;
import com.me.util.PropertiesParse;
import com.me.util.RSAUtil;

import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

/**
 * @author quan
 * @create 2021-04-15 18:56
 */
public class ServerArg {

    private static final int DEFAULT_PORT = 54188;
    private static final int MAX_CLIENT_COUNT = 50;

    private final int serverId = 10000;
    private final String serverName = "SERVER";

    private String privateKey;
    private String publicKey;
    private String aesKey;
    private int port = DEFAULT_PORT;
    private int maxClientCount = MAX_CLIENT_COUNT;
    private int startRegistryId;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    private void readCfg(String cfgPath) {
        InputStream is = Server.class.getResourceAsStream(cfgPath);
        if (is == null) {
            return;
        }
        PropertiesParse.loadProperties(is);
        String str = PropertiesParse.value("port");
        if (str.length() > 0) {
            this.port = Integer.valueOf(str);
        }

        str = PropertiesParse.value("max_client_count");
        if (str.length() > 0) {
            this.maxClientCount = Integer.valueOf(str);
        }

        str = PropertiesParse.value("startRegistryId");
        if (str.length() > 0) {
            this.startRegistryId = Integer.valueOf(str.toString());
        }
        try {
            RSAUtil.getKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        str = RSAUtil.getprivateKey();
        if (str.length() > 0) {
            this.privateKey = str;
        }

        str = RSAUtil.getPublicKey();
        if (str.length() > 0) {
            this.publicKey = str;
        }

        try {
            str = AESUtil.getKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (str.length() > 0) {
            this.aesKey = str;
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMaxClientCount() {
        return maxClientCount;
    }

    public void setMaxClientCount(int maxClientCount) {
        this.maxClientCount = maxClientCount;
    }

    public int getStartRegistryId() {
        return startRegistryId;
    }

    public void setStartRegistryId(int startRegistryId) {
        this.startRegistryId = startRegistryId;
    }

    public void initNetConfig(String configFilePath) {
        readCfg(configFilePath);
    }


    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    @Override
    public String toString() {
        return "ServerArg{" +
                "serverId=" + serverId +
                ", serverName='" + serverName + '\'' +
                ", privateKey='" + privateKey + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", aesKey='" + aesKey + '\'' +
                ", port=" + port +
                ", maxClientCount=" + maxClientCount +
                ", startRegistryId=" + startRegistryId +
                '}';
    }
}
