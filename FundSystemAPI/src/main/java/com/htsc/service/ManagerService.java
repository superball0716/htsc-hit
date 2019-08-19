package com.htsc.service;

import java.util.List;

import com.htsc.bean.ManagerDetail;
import com.htsc.bean.ManagerSearch;

public interface ManagerService {
	
	/*
	 * 根据关键词返回模糊查询相应经理人的用户名和工号
	 * */
	public List<ManagerSearch> vagueSearchByManager(String keyword);
	
	/*
	 * 获取经理详情
	 * */
	public ManagerDetail getManagerDetail(int mId);
	
	/*
	 * 
	 * */
	public String getManagerDetailByKeyword(String keyword,int pageNum,int pageSize);
	
	
	/*
	 * 生成新工号
	 * */
	public String newJobId();
	
	/*
	 * 新增经理人
	 * */
	public boolean addManager(String mName,String jobId,String description, String img_url);
	
	/*
	 * 编辑经理信息
	 * */
	public boolean editManager(int mId,String mName,String description, String img_url);
	
	public boolean delManager(int mId);
	
}
