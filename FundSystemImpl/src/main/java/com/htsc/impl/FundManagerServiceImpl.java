package com.htsc.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.htsc.mapper.FundManageMapper;
import com.htsc.service.FundManagerService;

@Service
public class FundManagerServiceImpl implements FundManagerService{
	
	
	@Autowired
	FundManageMapper fmm;
	
	@Override
	public boolean updateFundManager(int mId, int pId) {
		int result = fmm.updateFundManager(mId, pId);
		return result == 1?true:false;
	}

	@Override
	public boolean addFundManager(int mId, int pId) {
		int result = fmm.addFundManager(mId, pId);
		return result == 1?true:false;
	}
	

	@Override
	public int getManagerOfFund(int pId) {
		return fmm.getmIdbypId(pId);
	}

	@Override
	public boolean delFundManager(int mId) {
		return fmm.delFundManager(mId);
	}
}
