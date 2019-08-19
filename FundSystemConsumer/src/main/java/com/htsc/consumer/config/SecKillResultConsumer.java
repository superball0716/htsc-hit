package com.htsc.consumer.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.htsc.service.secKillService;

public class SecKillResultConsumer {
	@Reference(url = "dubbo://localhost:12345")
	private secKillService sks;
	
	public boolean secRequest(String uId,String pId) {
		return sks.secKillRequest(uId, pId);
	}
	
}
