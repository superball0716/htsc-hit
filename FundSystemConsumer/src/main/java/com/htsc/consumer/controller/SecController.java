package com.htsc.consumer.controller;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.htsc.bean.FundDetail;
import com.htsc.bean.ManagerDetail;
import com.htsc.bean.ProductInfo;
import com.htsc.bean.SecForm;
import com.htsc.consumer.config.FundManagerConsumer;
import com.htsc.consumer.config.ManagerServiceConsumer;
import com.htsc.consumer.config.ProductInfoConsumer;
import com.htsc.consumer.config.SecKillConsumer;
import com.htsc.consumer.config.SecKillResultConsumer;
import com.htsc.consumer.config.UserServiceConsumer;
import com.htsc.consumer.redis.CacheUtils;

@Controller
public class SecController {
	@Autowired
	UserServiceConsumer usc;
	
	@Autowired
	SecKillResultConsumer skrc;
	
	@Autowired
	ProductInfoConsumer pic;
	
	
	@Autowired
	ManagerServiceConsumer msc;
	
	@Autowired
	FundManagerConsumer fmc;
	
	@Autowired
	SecKillConsumer skc;
	
	private static final Logger log = LoggerFactory.getLogger(SecController.class);
	
	@PostMapping("/secRequest")
	@ResponseBody
	public void secRequest(@RequestBody SecForm lm,HttpServletRequest request,HttpServletResponse resp) {
		
    	resp.setContentType("text/html;charset=UTF-8");
        try {
		    JSONObject jsonObject = new JSONObject();
		    long maxNum = CacheUtils.getSeq("secKillProduct max?pId="+lm.getpId());
		    log.info("maxNum :{},{}",lm.getpId(),maxNum);
		    if(maxNum > 0) {
		    	
		    	String []  secTimeList = skc.getSecTime(lm.getpId());
				Timestamp now = new Timestamp(System.currentTimeMillis()); 

		    	if(Timestamp.valueOf(secTimeList[0]).after(now) || Timestamp.valueOf(secTimeList[1]).before(now)) {
	    		    jsonObject.put("result", "0");

		    	}
		    	else {
			    	CacheUtils.decr("secKillProduct max?pId="+lm.getpId());
		        	int uId = usc.getuIdByLoginId(lm.getLoginId());
		        	
		        	boolean secResult = skrc.secRequest(String.valueOf(uId), String.valueOf(lm.getpId()));
		        	log.info("secRequest {}",secResult);
		        	
		        	if(secResult) {
		        		FundDetail cachedfd = CacheUtils.getBean("FundDetail?pId="+String.valueOf(lm.getpId()), FundDetail.class);
		        		
		        		if(cachedfd != null) {
		
			        		ProductInfo fund = pic.getFundByPId(String.valueOf(lm.getpId()));
			        		cachedfd.setFund(fund);
			        		log.info("updated cache productNum:{}",fund.getProductNum());
			        		CacheUtils.saveBean("FundDetail?pId="+String.valueOf(lm.getpId()), cachedfd);
			        		CacheUtils.expire("FundDetail?pId="+String.valueOf(lm.getpId()), 5);
		        		}
		        		else {
		        			ProductInfo fund = pic.getFundByPId(String.valueOf(lm.getpId()));    			
		        			String [] secTime = skc.getSecTime(lm.getpId());     			
		        			int mId = fmc.getManagerBypId(lm.getpId());     			
		        			ManagerDetail md = msc.getMangerDetail(mId);        			
		        			FundDetail fd = new FundDetail(fund,secTime,md);      			
		        			CacheUtils.saveBean("FundDetail?pId="+lm.getpId(), fd);
		            		CacheUtils.expire("FundDetail?pId="+lm.getpId(), 5);
		
		        		}
		    		    jsonObject.put("result", "1");
		        	}
		        	else {
		    		    jsonObject.put("result", "0");
		        	}
		    	}
		    }
		    else {
    		    jsonObject.put("result", "0");
		    }

			resp.getWriter().println(jsonObject.toJSONString());
	        resp.getWriter().close();

		} catch (IOException e) {
			log.error("user login error");
		}
	}
}
