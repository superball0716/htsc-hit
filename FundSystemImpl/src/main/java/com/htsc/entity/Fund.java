package com.htsc.entity;

import java.math.BigDecimal;

public class Fund {
	private int pId;
	
	private String identifier;
	
	private String pName;
	
	private String description;
	
	private int productnum;

	private BigDecimal worth;
	
	private BigDecimal startPoint;
	
	private BigDecimal profitRate;
	
	private int version_id;
	
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
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

	public void setWorth(BigDecimal worth) {
		this.worth = worth;
	}

	public void setStartPoint(BigDecimal startPoint) {
		this.startPoint = startPoint;
	}

	public void setProfitRate(BigDecimal profitRate) {
		this.profitRate = profitRate;
	}

	public int getVersion_id() {
		return version_id;
	}

	public void setVersion_id(int version_id) {
		this.version_id = version_id;
	}

	public int getProductnum() {
		return productnum;
	}

	public void setProductnum(int productnum) {
		this.productnum = productnum;
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

	public void setpId(int pId) {
		this.pId = pId;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	
}
