package com.htsc.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.htsc.bean.UserCache;
import com.htsc.bean.ManagerSearch;
import com.htsc.entity.FundManage;
import com.htsc.entity.FundUser;
import com.htsc.entity.Manager;
import com.htsc.mapper.FundManageMapper;
import com.htsc.mapper.FundUserMapper;
import com.htsc.mapper.ManagerMapper;
import com.htsc.service.UserService;
import com.htsc.utils.CacheUtils;
import com.htsc.utils.TokenUtil;

@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private static final String loginKey = "login";
	
	private static final int EXPIRE = 12*3600;

	
	@Autowired
	FundUserMapper fum;

	@Override
	public UserCache LoginValidate(String userName, String passwd) {
		log.info("UserServiceImpl loginValidate start");
		FundUser fundUser = fum.getUserByNameAndPassword(userName, passwd);
		
		if (fundUser != null) {
			UserCache result = new UserCache();

			//生成token并存入redis
			try {
				String token = TokenUtil.createToken(fundUser.getuId(), fundUser.getRole());
				result.setToken(token);
				result.setRole(fundUser.getRole());
				result.setuName(fundUser.getuName());
				result.setLoginId(fundUser.getLoginId());
			} catch (Exception e) {
				log.error("创建token失败，uName:{}",userName);
			}
			return result;
		}
		return null;
		
	}

	@Override
	public boolean userExist(String userName) {
		if(fum.userExist(userName) == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean addUser(String userName, String passwd) {
		String loginId = "";
		boolean addResult = false;
		while(true) {
			loginId = fum.getNewestLoginId();
			loginId = String.valueOf(Integer.parseInt(loginId) + 1);
			if(loginId.length()<6) {
				for(int i=0;i<6-loginId.length();i++) {
					loginId = "0" + loginId; 
				}
			}
			addResult = fum.addNormalUser(loginId, userName,passwd);
			if(addResult) {
				break;
			}		
		}
		return addResult;
		
	}

	@Override
	public int getuIdByloginId(String loginId) {
		return fum.getuIdByLoginId(loginId);
	}
	
}
