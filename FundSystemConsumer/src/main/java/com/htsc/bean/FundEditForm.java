package com.htsc.bean;

import java.math.BigDecimal;

public class FundEditForm {
	
	int pId;
	
	String pName;
	
	String description;
	
	int num;
	
	BigDecimal worth;
	
	BigDecimal startPoint;
	
	BigDecimal profitRate;
	
	int mId;
	
	String managerName;
	
	String startTime;
	
	String endTime;

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

	public int getmId() {
		return mId;
	}

	public String getManagerName() {
		return managerName;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
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

	public void setmId(int mId) {
		this.mId = mId;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "FundEditForm [pId=" + pId + ", pName=" + pName + ", description=" + description + ", num=" + num
				+ ", worth=" + worth + ", startPoint=" + startPoint + ", profitRate=" + profitRate + ", mId=" + mId
				+ ", managerName=" + managerName + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
	
	
}
