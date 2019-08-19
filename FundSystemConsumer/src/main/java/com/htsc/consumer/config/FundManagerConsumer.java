package com.htsc.consumer.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.htsc.service.FundManagerService;

public class FundManagerConsumer {
	
	@Reference(url = "dubbo://localhost:12345")
	private FundManagerService fundManagerService;

	public boolean updateFundManager(int mId,int pId) {
		return fundManagerService.updateFundManager(mId, pId);
	}
	
	public boolean addFundManager(int mId,int pId) {
		return fundManagerService.addFundManager(mId, pId);
	}	
	
	public int getManagerBypId(int pId) {
		return fundManagerService.getManagerOfFund(pId);
	}
	
	public boolean delFundManager(int mId) {
		return fundManagerService.delFundManager(mId);
	}
}
