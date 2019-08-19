package com.htsc.bean;

import java.io.Serializable;

public class ManagerSearch implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1634332L;

	private int mId;

	private String mName;
	
	private String jobId;
	

	public String getJobId() {
		return jobId;
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

}
