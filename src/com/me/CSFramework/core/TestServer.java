package com.me.CSFramework.core;

import com.me.model.UserModel;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author quan
 * @create 2021-04-15 16:15
 */
public class TestServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(54188);
            Socket accept = serverSocket.accept();

            OutputStream outputStream = accept.getOutputStream();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(new UserModel("10001"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
