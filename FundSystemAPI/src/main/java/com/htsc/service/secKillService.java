package com.htsc.service;

import com.htsc.bean.OrderDetail;

public interface secKillService {
	public boolean secKillRequest(String uId,String pId);
	
	public OrderDetail getSecKillResult(String uId,String pId);
	
	/*
	 * ��ӻ��޸�һ����ɱ
	 * */
	public boolean addSecTime(int pId,String startTime,String endTime);
	
	
	/*
	 * ��ȡһ���������ɱʱ��
	 * */
	public String[] getsecTime(int pId);
	
	
}
