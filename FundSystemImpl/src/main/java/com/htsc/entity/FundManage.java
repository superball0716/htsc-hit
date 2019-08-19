package com.htsc.entity;

import java.math.BigDecimal;

public class FundManage {
	private int fId;
	
	private int managerId;
	
	private String jobId;
	
	private String mName;
	
	private int pId;
	
	private String identifier;
	
	private String pName;
	
	private String description;

	private BigDecimal worth;
	
	private BigDecimal startPoint;
	
	private BigDecimal profitRate;
	
	private int productnum;

	
	public int getManagerId() {
		return managerId;
	}


	public String getmName() {
		return mName;
	}


	public String getIdentifier() {
		return identifier;
	}


	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}


	public void setmName(String mName) {
		this.mName = mName;
	}


	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}


	public int getfId() {
		return fId;
	}


	public String getJobId() {
		return jobId;
	}

	public int getpId() {
		return pId;
	}

	public String getpName() {
		return pName;
	}

	public String getDescription() {
		return description;
	}


	public BigDecimal getWorth() {
		return worth;
	}

	public BigDecimal getStartPoint() {
		return startPoint;
	}

	public BigDecimal getProfitRate() {
		return profitRate;
	}

	public void setfId(int fId) {
		this.fId = fId;
	}


	public void setJobId(String jobId) {
		this.jobId = jobId;
	}


	public void setpId(int pId) {
		this.pId = pId;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setWorth(BigDecimal worth) {
		this.worth = worth;
	}

	public void setStartPoint(BigDecimal startPoint) {
		this.startPoint = startPoint;
	}

	public void setProfitRate(BigDecimal profitRate) {
		this.profitRate = profitRate;
	}


	public int getProductnum() {
		return productnum;
	}


	public void setProductnum(int productnum) {
		this.productnum = productnum;
	}

	
	
}
