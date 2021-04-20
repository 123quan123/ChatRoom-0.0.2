package com.me.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.me.model.UserModel;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ArgumentMaker {
	//gson使用时可保证多线程安全
	public static final Gson gson = new GsonBuilder().create();
	private static final Type type = new TypeToken<Map<String, String>>() {}.getType();
	private Map<String, String> paraMap;
	
	public ArgumentMaker() {
		paraMap = new HashMap<String, String>();
	}
	
	public ArgumentMaker(String str) {
		paraMap = gson.fromJson(str, type);
	}
	
	public ArgumentMaker addArg(String name, Object value) {
		paraMap.put(name, gson.toJson(value));
		
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getValue(String name, Type type) {
		String valueString = paraMap.get(name);
		if (valueString == null) {
			return null;
		}
		
		return (T) gson.fromJson(valueString, type);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getValue(String name, Class<?> type) {
		String valueString = paraMap.get(name);
		if (valueString == null) {
			return null;
		}
		
		return (T) gson.fromJson(valueString, type);
	}
	
	public Map<String, String> getMap() {
		return paraMap;
	}
	
	public void setMap(Map<String, String> map) {
		this.paraMap = map;
	}
	
	public String toJson() {
		return gson.toJson(paraMap);
	}

	public static void main(String[] args) {
		ArgumentMaker argumentMaker = new ArgumentMaker();
		String s = argumentMaker.addArg("com/me/model", new UserModel("10001", 15, 1, "sxasx")).toJson();
		ArgumentMaker argumentMaker1 = new ArgumentMaker(s);
		UserModel value = argumentMaker1.getValue("com/me/model", UserModel.class);
		System.out.println(value);
		String client = "nPxc9FVfGrPosACjlj81knKKHETycHVzVVLIfn9s723FLzU/uXtz0uylJwzhVocy9lIMORYkx/lMG1rUmcOFfx3G3iz7azj5jGv8406TcUl0GmoypiY5t+iBS9+55BcxYOJ0pBwbsdVjDuQBXfr9FcrViaUr96K7og5E9Fv5nS3dLrd7gsdj4kIHPm8B96buaeRAQBt+//8II2FHvjcBBwKzbTMfiG5sfiZeaQxrAR8=";
	}
}
