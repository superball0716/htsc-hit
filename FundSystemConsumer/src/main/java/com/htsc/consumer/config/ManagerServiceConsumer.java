package com.htsc.consumer.config;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Reference;
import com.htsc.bean.ManagerDetail;
import com.htsc.bean.ManagerSearch;
import com.htsc.bean.ManagerSearchForm;
import com.htsc.service.ManagerService;

public class ManagerServiceConsumer {
	@Reference(url = "dubbo://localhost:12345")
	private ManagerService managerService;
	
	public List<ManagerSearchForm> vagueSearchByManager(String keyword){
		List<ManagerSearch> userSearchList = managerService.vagueSearchByManager(keyword);
		
		List<ManagerSearchForm> result = new ArrayList<ManagerSearchForm>();
		for(ManagerSearch us:userSearchList) {
			ManagerSearchForm usf = new ManagerSearchForm();
			usf.setValue(us.getmName()+"("+us.getJobId()+")");
			usf.setmId(us.getmId());
			result.add(usf);
		}
		return result;
	}

	
	public ManagerDetail getMangerDetail(int mId) {
		return managerService.getManagerDetail(mId);
	}
	
	
	public String getManagerDetailByKeyword(String keyword,int pageNum,int pageSize) {
		return managerService.getManagerDetailByKeyword(keyword, pageNum, pageSize);
	}
	
	public boolean addManager(String mName, String jobId, String description, String img_url) {
		return managerService.addManager(mName, jobId, description, img_url);
	}
	
	public boolean editManager(int mId,String mName,String description,String img_url) {
		return managerService.editManager(mId, mName, description, img_url);
	}
	
	public boolean delManager(int mId) {
		return managerService.delManager(mId);
	}
	
	public String getNewjobId() {
		return managerService.newJobId();
	}
}
