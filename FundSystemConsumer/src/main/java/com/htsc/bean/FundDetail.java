package com.htsc.bean;

public class FundDetail {
	ProductInfo fund;
	
	String [] secTime;

	ManagerDetail md;


	public String[] getSecTime() {
		return secTime;
	}

	public ManagerDetail getMd() {
		return md;
	}


	public void setSecTime(String[] secTime) {
		this.secTime = secTime;
	}

	public void setMd(ManagerDetail md) {
		this.md = md;
	}

	public ProductInfo getFund() {
		return fund;
	}

	public void setFund(ProductInfo fund) {
		this.fund = fund;
	}

	public FundDetail(ProductInfo fund, String[] secTime, ManagerDetail md) {
		super();
		this.fund = fund;
		this.secTime = secTime;
		this.md = md;
	}

	
	
}
