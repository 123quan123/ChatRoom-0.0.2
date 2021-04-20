package com.me.dao;

import com.me.model.UserModel;

import java.util.Map;

public interface IUserDao {
	
	public Boolean registry(UserModel userModel);
	
	public UserModel loginIn(String id, String password);

	public Map<String, String> getAess();
}
