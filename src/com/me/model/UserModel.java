package com.me.model;

import java.io.Serializable;

public class UserModel implements Serializable, Cloneable {
	private String id;
	
	private String name;
	private int sex;
	private int age;
	private String tel;

	private String serverPuk;
	private String aesKey;
	
	public UserModel() {
	}
	
	public UserModel(String id) {
		super();
		this.id = id;
	}

	public UserModel(String name, int sex, int age, String tel) {
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.tel = tel;
	}

	public UserModel(String id, String name, int sex, int age, String tel) {
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.tel = tel;
	}

	public UserModel(String id, String name, String aesKey) {
		super();
		this.aesKey = aesKey;
		this.name = name;
		this.id = id;
	}
	
	public UserModel(String id, String serverPuk, String name, String publicKey, String privateKey, String aesKey) {
		super();
		this.id = id;
		this.serverPuk = serverPuk;
		this.name = name;
		this.aesKey = aesKey;
	}

	public UserModel(String aesKey, String name, String publicKey, String privateKey) {
		this.name = name;
		this.aesKey = aesKey;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAesKey() {
		return aesKey;
	}

	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	

	public String getServerPuk() {
		return serverPuk;
	}

	public void setServerPuk(String serverPuk) {
		this.serverPuk = serverPuk;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aesKey == null) ? 0 : aesKey.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((serverPuk == null) ? 0 : serverPuk.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserModel other = (UserModel) obj;
		if (aesKey == null) {
			if (other.aesKey != null)
				return false;
		} else if (!aesKey.equals(other.aesKey))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (serverPuk == null) {
			if (other.serverPuk != null)
				return false;
		} else if (!serverPuk.equals(other.serverPuk))
			return false;
		return true;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return "账号 : " + id  + "   " + "昵称 : " + name+ "   " + "性别 : " + sex + "   " + "年龄 : " + age+ "   " + "电话 : " + tel
				 + "   " + "aeskey : " + aesKey;
	}

}
