package com.me.service;

import com.me.dao.IUserDao;
import com.me.dao.UserDao;
import com.me.model.UserModel;

import java.util.Map;

public class UserService implements IUserSvice {

	private IUserDao userDao;
	
	public UserService() {
		userDao = new UserDao();
	}
	
	@Override
	public Boolean registry(UserModel userModel) {
		return userDao.registry(userModel);
	}

	@Override
	public UserModel loginIn(UserModel userModel) {
		return userDao.loginIn(userModel.getId(), userModel.getAesKey());
	}

	@Override
	public Map<String, String> getAess() {
		return userDao.getAess();
	}

}
