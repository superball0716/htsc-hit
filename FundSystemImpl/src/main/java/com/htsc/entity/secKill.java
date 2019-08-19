package com.htsc.entity;

import java.sql.Timestamp;

public class secKill {
	private int sId;
	
	private int pId;
	
	private Timestamp createTime;
	
	private Timestamp startTime;
	
	private Timestamp endTime;

	public int getsId() {
		return sId;
	}

	public int getpId() {
		return pId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setsId(int sId) {
		this.sId = sId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	
}
