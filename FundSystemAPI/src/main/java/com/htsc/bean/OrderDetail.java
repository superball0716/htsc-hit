package com.htsc.bean;

import java.io.Serializable;

public class OrderDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 12222222L;
	
	private String uId;
	
	private String pId;
	
	private int num;
	
	private String description;
	
	public String getuId() {
		return uId;
	}

	public String getpId() {
		return pId;
	}

	public int getNum() {
		return num;
	}

	public String getDescription() {
		return description;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
