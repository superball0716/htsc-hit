package com.htsc.service;

public interface FundManagerService {
	/*
	 * ���»�����
	 * */
	public boolean updateFundManager(int mId,int pId);
	
	/*
	 * Ϊ������Ӿ�����Ϣ
	 * */
	public boolean addFundManager(int mId,int pId);
	
	
	/*
	 * ��ȡ����ľ�����Id
	 * */
	public int getManagerOfFund(int pId);
	
	
	public boolean delFundManager(int mId);
}
