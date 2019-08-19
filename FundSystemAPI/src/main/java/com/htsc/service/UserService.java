package com.htsc.service;

import com.htsc.bean.UserCache;

public interface UserService {
	/*
	 * 登陆校验，成功返回用户信息用于生成token
	 * */
	public UserCache LoginValidate(String userName,String passwd);
	
	/*
	 * 判断用户是否存在
	 * */
	public boolean userExist(String userName);
	
	
	public boolean addUser(String userName, String passwd);
	
	
	/*
	 * 根据loginId获取用户Id
	 * */
	public int getuIdByloginId(String loginId);

}
