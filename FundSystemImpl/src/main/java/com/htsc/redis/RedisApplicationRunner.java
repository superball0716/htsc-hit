package com.htsc.redis;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.htsc.entity.secKill;
import com.htsc.mapper.FundMapper;
import com.htsc.mapper.secKillMapper;
import com.htsc.utils.CacheUtils;


@Component
public class RedisApplicationRunner implements ApplicationRunner {
	
    @Autowired
    secKillMapper skm;
    
	@Autowired
	FundMapper pm;
	
	private static final Logger log = LoggerFactory.getLogger(RedisApplicationRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
		List<secKill> activeSec = skm.getActiveSecKill();
		log.info("initialSecNum:{}",activeSec.size());
		for(secKill sk: activeSec) {
			int productNum =  pm.getProductBypId(sk.getpId()).getProductnum();
			if(productNum > 0) {
				CacheUtils.saveSeq("secKillProduct max?pId="+sk.getpId(),productNum*10);
			}
		}
    }

}