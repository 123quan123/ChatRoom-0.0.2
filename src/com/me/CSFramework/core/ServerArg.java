package com.me.CSFramework.core;

import com.me.encrpt.EncryptUtil;
import com.me.encrpt.IEncryptUtil;
import com.me.util.PropertiesParse;

import java.io.InputStream;

/**
 * @author quan
 * @create 2021-04-15 18:56
 */
public class ServerArg {

    private static final int DEFAULT_PORT = 54188;
    private static final int MAX_CLIENT_COUNT = 50;

    private final int serverId = 10000;
    private final String serverName = "SERVER";

    private int port = DEFAULT_PORT;
    private int maxClientCount = MAX_CLIENT_COUNT;
    private int startRegistryId;

    private IEncryptUtil encryptUtil;

    public ServerArg() {
        encryptUtil = new EncryptUtil();
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

    public IEncryptUtil getEncryptUtil() {
        return encryptUtil;
    }

    public void setEncryptUtil(IEncryptUtil encryptUtil) {
        this.encryptUtil = encryptUtil;
    }

    @Override
    public String toString() {
        return "ServerArg{" +
                "serverId=" + serverId +
                ", serverName='" + serverName + '\'' +
                ", port=" + port +
                ", maxClientCount=" + maxClientCount +
                ", startRegistryId=" + startRegistryId +
                '}';
    }
}
