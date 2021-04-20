package com.me.CSFramework.core;

import com.me.model.UserModel;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @author quan
 * @create 2021-04-15 16:17
 */
public class TestClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("169.254.100.38", 54188);
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Object o = objectInputStream.readObject();
            UserModel cast = UserModel.class.cast(o);
            System.out.println(cast.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
