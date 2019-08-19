package com.htsc.service;

public interface FundManagerService {
	/*
	 * 更新基金经理
	 * */
	public boolean updateFundManager(int mId,int pId);
	
	/*
	 * 为基金添加经理信息
	 * */
	public boolean addFundManager(int mId,int pId);
	
	
	/*
	 * 获取基金的经理人Id
	 * */
	public int getManagerOfFund(int pId);
	
	
	public boolean delFundManager(int mId);
}
