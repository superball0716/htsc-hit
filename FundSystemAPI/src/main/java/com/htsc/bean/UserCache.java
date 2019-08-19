package com.htsc.bean;

import java.io.Serializable;

public class UserCache implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 12334222L;

	private String token;
	
	/* 1代表管理员
	 * 2代表普通用户
	 * */
	private int role;
	
	private String loginId;
	
	private String uName;

	public String getToken() {
		return token;
	}

	public int getRole() {
		return role;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}
}
