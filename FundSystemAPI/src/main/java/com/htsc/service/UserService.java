package com.htsc.service;

import com.htsc.bean.UserCache;

public interface UserService {
	/*
	 * ��½У�飬�ɹ������û���Ϣ��������token
	 * */
	public UserCache LoginValidate(String userName,String passwd);
	
	/*
	 * �ж��û��Ƿ����
	 * */
	public boolean userExist(String userName);
	
	
	public boolean addUser(String userName, String passwd);
	
	
	/*
	 * ����loginId��ȡ�û�Id
	 * */
	public int getuIdByloginId(String loginId);

}
