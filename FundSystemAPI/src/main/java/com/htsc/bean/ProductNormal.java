package com.htsc.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductNormal implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 12298317L;

	int pId;
	
	String pName;
	
	String description;
	
	String identifier;
	
	int num;
	
	BigDecimal worth;
	
	BigDecimal startPoint;
	
	BigDecimal profitRate;

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

	public String getDescription() {
		return description;
	}

	public int getNum() {
		return num;
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

	public void setpId(int pId) {
		this.pId = pId;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setNum(int num) {
		this.num = num;
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
	
	
}
