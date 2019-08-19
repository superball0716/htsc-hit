package com.htsc.consumer.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.htsc.bean.UserCache;
import com.htsc.service.UserService;

public class UserServiceConsumer {
	@Reference(url = "dubbo://localhost:12345")
	private UserService userService;
	
	public UserCache LoginValidate(String userName,String passwd) {
		return userService.LoginValidate(userName, passwd);
	}
	
	public boolean userExist(String userName) {
		return userService.userExist(userName);
	}
	
	public boolean addUser(String userName,String passwd) {
		return userService.addUser(userName, passwd);
	}
	
	public int getuIdByLoginId(String loginId) {
		return userService.getuIdByloginId(loginId);
	}
}
