package com.htsc.bean;

import java.io.Serializable;

public class ManagerDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1278467121L;
	
	private int managerId;
	
    private String mName;
    
    private String jobId;
    
    private String description;
    
    private String img_url;

	public int getManagerId() {
		return managerId;
	}

	public String getmName() {
		return mName;
	}

	public String getJobId() {
		return jobId;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}


	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
}
