package com.htsc.entity;

import java.sql.Timestamp;

public class secKillResult {
	private int uId;
	
	private int pId;
	
	private Timestamp createTime;

	public int getuId() {
		return uId;
	}

	public int getpId() {
		return pId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setuId(int uId) {
		this.uId = uId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public secKillResult(int uId, int pId, Timestamp createTime) {
		super();
		this.uId = uId;
		this.pId = pId;
		this.createTime = createTime;
	}
}
