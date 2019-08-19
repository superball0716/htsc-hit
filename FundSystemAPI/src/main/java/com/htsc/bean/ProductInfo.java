package com.htsc.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 11111L;

	private int fId;
	
	private int mId;
	
	private String jobId;
	
	private String mName;
	
	private int pId;
	
	private String identifier;
	
	private String pName;
	
	private String description;

	private BigDecimal worth;
	
	private BigDecimal startPoint;
	
	private BigDecimal profitRate;
	
	private int productNum;

	
	
	public String getIdentifier() {
		return identifier;
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

	public int getProductNum() {
		return productNum;
	}

	public void setfId(int fId) {
		this.fId = fId;
	}


	public void setJobId(String jobId) {
		this.jobId = jobId;
	}


	public int getmId() {
		return mId;
	}

	public String getmName() {
		return mName;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public void setmName(String mName) {
		this.mName = mName;
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

	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}
	
	

}
