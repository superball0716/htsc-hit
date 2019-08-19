package com.htsc.bean;

import java.io.Serializable;

public class ProductSearch implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 11133442L;

	private int pId;
	
	private String pName;
	
	private String identifier;	
	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public int getpId() {
		return pId;
	}

	public String getpName() {
		return pName;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}
	
	
}
