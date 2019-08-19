package com.htsc.impl;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.context.annotation.Bean;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.htsc.bean.OrderDetail;
import com.htsc.entity.Fund;
import com.htsc.entity.secKill;
import com.htsc.entity.secKillResult;
import com.htsc.mapper.FundMapper;
import com.htsc.mapper.secKillMapper;
import com.htsc.mapper.secKillResultMapper;
import com.htsc.service.secKillService;
import com.htsc.utils.CacheUtils;
import com.htsc.utils.ObjectConvertionUtil;


@Service
public class secKillImpl implements secKillService{
	
	private static final Logger log = LoggerFactory.getLogger(secKillImpl.class);
	
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
    
    @Autowired
    secKillMapper skm;
    
	@Autowired
	FundMapper pm;
	
	@Autowired
	secKillResultMapper skrm;

	@Override
	public boolean secKillRequest(String uId, String pId) {
		
		Fund p = pm.getProductBypId(Integer.parseInt(pId));
        if (p.getProductnum() > 0) {
        	log.info("secKillRequest productNum :{}",p.getProductnum());
        	int version_id = pm.getVersionId(Integer.parseInt(pId));
        	boolean updated = pm.decreseProductByLock(Integer.parseInt(pId), version_id);
        	
        	log.info("decrease productNum {}:",updated);
        	if(updated) {
        		Timestamp createTime = new Timestamp(System.currentTimeMillis()); 
        		boolean resultAdd = skrm.insertSecKillResult(Integer.parseInt(pId), Integer.parseInt(uId), createTime);
            	log.info("add secKillResult {}:",resultAdd);

        		return resultAdd;
        	}
        	else {
		    	CacheUtils.incr("secKillProduct max?pId="+pId);
        	}
        }
		return false;
	}

	@Override
	public OrderDetail getSecKillResult(String uId, String pId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addSecTime(int pId, String startTime, String endTime) {		
		Timestamp createTime = new Timestamp(System.currentTimeMillis()); 

		int result = skm.addSecKill(pId, Timestamp.valueOf(startTime), Timestamp.valueOf(endTime), createTime);
		if (result == 1) {
			return true;
		}
		return false;
	}

	@Override
	public String[] getsecTime(int pId) {
		ObjectConvertionUtil util = ObjectConvertionUtil.getInstance();
		String [] result = new String[2];

		secKill sk = skm.getSecKill(pId);
		if(sk != null) {
			result[0] = util.getStrByTimestamp(sk.getStartTime());
			result[1] = util.getStrByTimestamp(sk.getEndTime());
		}
		
		return result;
	}
	
}
