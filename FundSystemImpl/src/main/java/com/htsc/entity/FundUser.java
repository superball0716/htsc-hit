package com.htsc.entity;

public class FundUser {
	private int uId;
	
	private String uName;
	
	private String loginId;
	
	private String passwd;
	
	/*
	 * 1为管理员，2为普通用户
	 * */
	private int role;
	
	private int version_id;
	
	

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public int getuId() {
		return uId;
	}

	public String getuName() {
		return uName;
	}

	public String getPasswd() {
		return passwd;
	}

	public int getRole() {
		return role;
	}

	public int getVersion_id() {
		return version_id;
	}

	public void setuId(int uId) {
		this.uId = uId;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public void setVersion_id(int version_id) {
		this.version_id = version_id;
	}
	
}
