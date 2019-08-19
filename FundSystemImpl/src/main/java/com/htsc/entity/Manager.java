package com.htsc.entity;

public class Manager {
	int managerId;
	
    String mName;
    
    String jobId;
    
    String description;
    
    String img_url;
    
    int version_id;

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

	public int getVersion_id() {
		return version_id;
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

	public void setVersion_id(int version_id) {
		this.version_id = version_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
    
    
}
