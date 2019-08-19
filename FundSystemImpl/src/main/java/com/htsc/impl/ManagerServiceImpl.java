package com.htsc.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.htsc.bean.ManagerDetail;
import com.htsc.bean.ManagerSearch;
import com.htsc.entity.Fund;
import com.htsc.entity.FundManage;
import com.htsc.entity.Manager;
import com.htsc.mapper.FundManageMapper;
import com.htsc.mapper.ManagerMapper;
import com.htsc.service.ManagerService;


@Service
public class ManagerServiceImpl implements ManagerService{
	
	private static final Logger log = LoggerFactory.getLogger(ManagerServiceImpl.class);

	
	@Autowired
	ManagerMapper mm;
			
	@Override
	public List<ManagerSearch> vagueSearchByManager(String keyword) {
		log.info("userServiceImpl vagueSearchByUser start ,keyword:{}",keyword);
		
		List<Manager> fundUserList = mm.vagueSearchByManager(keyword);
		List<ManagerSearch>  result = new ArrayList<ManagerSearch>();
		
		for (Manager fu:fundUserList) {
			ManagerSearch us = new ManagerSearch();
			us.setmId(fu.getManagerId());
			us.setmName(fu.getmName());
			us.setJobId(fu.getJobId());
			result.add(us);
		}
		return result;
	}


	@Override
	public ManagerDetail getManagerDetail(int mId) {
		Manager m = mm.getManagerDetail(mId);
		ManagerDetail result = new ManagerDetail();
		result.setJobId(m.getJobId());
		result.setManagerId(m.getManagerId());
		result.setmName(m.getmName());
		result.setDescription(m.getDescription());
		log.info(m.getDescription());
		result.setImg_url(m.getImg_url());
		return result;
	}


	@Override
	public String getManagerDetailByKeyword(String keyword, int pageNum, int pageSize) {
	    PageHelper.startPage(pageNum, pageSize);
		List<Manager> manager = mm.getManagerDetailByKeyword(keyword);
	    PageInfo<Manager> managerList = new PageInfo<>(manager);
		return JSON.toJSONString(managerList);
	}


	@Override
	public String newJobId() {
		String jobId = mm.getNewestJobId();
		int newest = Integer.parseInt(jobId) + 1;
		String result="";
		for(int i = 0; i < 4 - String.valueOf(newest).length(); i++) {
			result = result + "0";
		}
		result = result + String.valueOf(newest);
		
		return result;
	}


	@Override
	public boolean addManager(String mName, String jobId, String description, String img_url) {
		return mm.addManger(mName, jobId, description, img_url);
	}


	@Override
	public boolean editManager(int mId, String mName, String description, String img_url) {
		return mm.editManager(mId, mName, description, img_url);
	}


	@Override
	public boolean delManager(int mId) {
		return mm.delManager(mId);
	}
}
