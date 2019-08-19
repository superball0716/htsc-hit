package com.htsc.service;

import com.htsc.bean.OrderDetail;

public interface secKillService {
	public boolean secKillRequest(String uId,String pId);
	
	public OrderDetail getSecKillResult(String uId,String pId);
	
	/*
	 * 添加或修改一次秒杀
	 * */
	public boolean addSecTime(int pId,String startTime,String endTime);
	
	
	/*
	 * 获取一个基金的秒杀时间
	 * */
	public String[] getsecTime(int pId);
	
	
}
