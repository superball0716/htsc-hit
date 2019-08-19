package com.htsc.consumer.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONObject;
import com.htsc.bean.FundDetail;
import com.htsc.bean.FundEditForm;
import com.htsc.bean.FundSearchForm;
import com.htsc.bean.ManagerDetail;
import com.htsc.bean.ManagerSearch;
import com.htsc.bean.ManagerSearchForm;
import com.htsc.bean.ProductInfo;
import com.htsc.bean.ProductNormal;
import com.htsc.consumer.config.FundManagerConsumer;
import com.htsc.consumer.config.ManagerServiceConsumer;
import com.htsc.consumer.config.ProductInfoConsumer;
import com.htsc.consumer.config.SecKillConsumer;
import com.htsc.consumer.config.UserServiceConsumer;
import com.htsc.consumer.redis.CacheUtils;

@Controller
public class FundInfoController {
	
	@Autowired
	ProductInfoConsumer pic;
	
	@Autowired
	ManagerServiceConsumer msc;
	
	@Autowired
	FundManagerConsumer fmc;
	
	@Autowired
	SecKillConsumer skc;
	
	private static final Logger log = LoggerFactory.getLogger(FundInfoController.class);

	
	@GetMapping("/vagueSearchByFund")
	public void vagueSearchByFund(String keyword, HttpServletResponse resp) {
    	JSONObject jsonObject = new JSONObject();
    	
		List<FundSearchForm> result = pic.vagueSearchByFund(keyword);
		
		jsonObject.put("fundSearch", result);
		
        try {
        	resp.setContentType("text/html;charset=UTF-8");
			resp.getWriter().println(jsonObject.toJSONString());
	        resp.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error");
		}
	}
	
	
	@GetMapping("/getAllFund")
	public void getAllFund(int pageNum,int pageSize, HttpServletResponse resp) {
		
    	JSONObject jsonObject = new JSONObject();

		log.info("getAll fund start ,pageSize:{},pageNum:{}",pageSize,pageNum);
		
		String allFund = pic.getAllFund(pageNum, pageSize);
		
		jsonObject.put("allFund", allFund);
		
        try {
        	resp.setContentType("text/html;charset=UTF-8");
			resp.getWriter().println(jsonObject.toJSONString());
	        resp.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error");
		}
	}
	
	@GetMapping("/getFundBymId")
	public void getFundByUId(String mId, int pageNum, int pageSize, HttpServletResponse resp) {
    	JSONObject jsonObject = new JSONObject();
		
		String fund = pic.getFundByManager(mId, pageNum, pageSize);
		
		jsonObject.put("allFund", fund);
		
        try {
        	resp.setContentType("text/html;charset=UTF-8");
			resp.getWriter().println(jsonObject.toJSONString());
	        resp.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error");
		}
	}
	
	@GetMapping("/getFundByPId")
	public void getFundByPId(String pId, int pageNum, int pageSize, HttpServletResponse resp) {
    	JSONObject jsonObject = new JSONObject();
		
		ProductInfo fund = pic.getFundByPId(pId);
		
		String [] secTime = skc.getSecTime(Integer.parseInt(pId));
		
		jsonObject.put("allFund", fund);
		jsonObject.put("secTime", secTime);
		
        try {
        	resp.setContentType("text/html;charset=UTF-8");
			resp.getWriter().println(jsonObject.toJSONString());
	        resp.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error");
		}
	}
	
	@GetMapping("/getFundDetailByPId")
	public void getFundDetailByPId(String pId, HttpServletResponse resp) {
    	JSONObject jsonObject = new JSONObject();
    	
    	FundDetail cachedfd = CacheUtils.getBean("FundDetail?pId="+pId, FundDetail.class);
		if(cachedfd == null) {
		
			ProductInfo fund = pic.getFundByPId(pId);
			
			String [] secTime = skc.getSecTime(Integer.parseInt(pId));
			
			int mId = fmc.getManagerBypId(Integer.parseInt(pId));
			
			ManagerDetail md = msc.getMangerDetail(mId);
			
			FundDetail fd = new FundDetail(fund,secTime,md);
			
			CacheUtils.saveBean("FundDetail?pId="+pId, fd);
    		CacheUtils.expire("FundDetail?pId="+pId, 5);

	
			jsonObject.put("fundDetail", fd);
		}
		else {
			log.info("get fund detail by cache pId:{}", pId);
			jsonObject.put("fundDetail", cachedfd);
		}
        try {
        	resp.setContentType("text/html;charset=UTF-8");
			resp.getWriter().println(jsonObject.toJSONString());
	        resp.getWriter().close();
		} catch (IOException e) {
			System.out.println("error");
		}
	}
	
	@GetMapping("/getFundByKeyword")
	public void getFundBykeyword(String fundKey, String userKey, int pageNum, int pageSize, HttpServletResponse resp) {
		log.info("FundInfoController getFundBykeyword fundkey:{},userkey:{}",fundKey,userKey);
    	JSONObject jsonObject = new JSONObject();
    	
    	if(fundKey == null) {
    		fundKey = "";
    	}
    	if(userKey == null) {
    		userKey = "";
    	}
		
		String fund = pic.getFundByKeyword(fundKey, userKey, pageNum, pageSize);
		
		jsonObject.put("allFund", fund);
		
        try {
        	resp.setContentType("text/html;charset=UTF-8");
			resp.getWriter().println(jsonObject.toJSONString());
	        resp.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error");
		}
	}
	
	
	
	
	@PostMapping("/editFund")
	public void editFund(@RequestBody FundEditForm edit, HttpServletResponse resp) {
		
		ProductNormal pn = new ProductNormal();
		pn.setpId(edit.getpId());
		pn.setpName(edit.getpName());
		pn.setDescription(edit.getDescription());
		pn.setWorth(edit.getWorth());
		pn.setStartPoint(edit.getStartPoint());
		pn.setProfitRate(edit.getProfitRate());
		pn.setNum(edit.getNum());
		
		if(edit.getpId() > 0) {
			boolean updateResult = pic.updateFundNormal(pn);
			if(updateResult) {
				fmc.updateFundManager(edit.getmId(), edit.getpId());
				if(edit.getStartTime() != "" && edit.getEndTime() != "") {
					boolean addSecResult = skc.addSecTime(edit.getpId(), edit.getStartTime(), edit.getEndTime());
					if(addSecResult) {
				    	CacheUtils.saveSeq("secKillProduct max?pId="+edit.getpId(),pn.getNum()*10);
					}
				}
			}
		}
		else {
			while(true) {
				String newIden = pic.getNewestIdentifier();
				pn.setIdentifier(newIden);
				boolean addResult = pic.addFundNormal(pn, newIden);
				if(addResult) {
					int newpId = pic.getpIdByIdentifier(newIden);
					fmc.addFundManager(edit.getmId(), newpId);
					if(edit.getStartTime() != "" && edit.getEndTime() != "") {
						boolean addSecResult = skc.addSecTime(newpId, edit.getStartTime(), edit.getEndTime());
						if(addSecResult) {
					    	CacheUtils.saveSeq("secKillProduct max?pId="+newpId,pn.getNum()*10);
						}
					}
					break;
				}
			}
		}
	}
	
	
}
