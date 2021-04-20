package com.me.service;

import com.me.model.UserModel;

import java.util.Map;

public interface IUserSvice {

	
	public Boolean registry(UserModel userModel);
	
	public UserModel loginIn(UserModel userModel);

	public Map<String, String> getAess();
}
