package com.me.dao;

import com.me.model.UserModel;
import com.me.util.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserDao implements IUserDao {
	
	private Database database;
	
	public UserDao() {
		database = new Database();
	}

	@Override
	public Boolean registry(UserModel userModel) {
		ResultSet rs = database.executeQuery("SELECT user_id FROM user "
				+ "where user_id = '" + userModel.getId() + "'");
		
		try {
			if (rs.next()) {
				return false;
			} else {
				int i = database.executeUpdate("INSERT INTO user(user_id, name, sex, age, tel) "
						+ "VALUES ('" + userModel.getId()+ "','" + userModel.getName()
						+ "','" + userModel.getSex() + "','" + userModel.getAge()+ "','" + userModel.getTel() + "')");
				if (i != 1) {
					return false;
				}
				i = database.executeUpdate("INSERT INTO chatroom_keys(id, aesKey) "
						+ "VALUES ('" + userModel.getId()+ "','" + userModel.getAesKey() + "')");

				System.out.println("insert return " + i);
				
				return i == 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public UserModel loginIn(String id, String aesKey) {
		ResultSet rs = database.executeQuery("SELECT * FROM chatroom_keys "
				+ "where id = '" + id + "' and aesKey = '" + aesKey + "'");
		try {
			if (rs.next()) {
				rs = database.executeQuery("SELECT user_id, name, sex, age, tel FROM user "
						+ "where user_id = '" + id + "'");
				rs.next();
				String name = rs.getString("name");
				int sex = rs.getInt("sex");
				int age = rs.getInt("age");
				String tel = rs.getString("tel");

				UserModel userModel = new UserModel(id, name, sex, age, tel);
				userModel.setAesKey(aesKey);
				return userModel;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public Map<String, String> getAess() {
		System.out.println("SELECT id, aesKey FROM chatroom ");
		ResultSet rs = database.executeQuery("SELECT id, aesKey FROM "
				+ "chatroom ");
		try {
			Map<String, String> aesMap = new HashMap<String, String>();
			while (rs.next()) {
				String id = rs.getString("id");
				
				String aesKey = rs.getString("aesKey");
				System.out.println(id + " : " + aesKey);
				aesMap.put(id, aesKey);
			} 
			return aesMap;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
