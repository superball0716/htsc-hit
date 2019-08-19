package com.htsc.consumer.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.htsc.service.secKillService;

public class SecKillConsumer {
	@Reference(url = "dubbo://localhost:12345")
	private secKillService sec;
	
	public boolean addSecTime(int pId,String startTime,String endTime) {
		return sec.addSecTime(pId, startTime, endTime);	
	}
	
	public String[] getSecTime(int pId) {
		return sec.getsecTime(pId);
	}
}
