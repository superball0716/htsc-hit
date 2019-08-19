package com.htsc.service;

import java.util.List;

import com.htsc.bean.ManagerDetail;
import com.htsc.bean.ManagerSearch;

public interface ManagerService {
	
	/*
	 * ���ݹؼ��ʷ���ģ����ѯ��Ӧ�����˵��û����͹���
	 * */
	public List<ManagerSearch> vagueSearchByManager(String keyword);
	
	/*
	 * ��ȡ��������
	 * */
	public ManagerDetail getManagerDetail(int mId);
	
	/*
	 * 
	 * */
	public String getManagerDetailByKeyword(String keyword,int pageNum,int pageSize);
	
	
	/*
	 * �����¹���
	 * */
	public String newJobId();
	
	/*
	 * ����������
	 * */
	public boolean addManager(String mName,String jobId,String description, String img_url);
	
	/*
	 * �༭������Ϣ
	 * */
	public boolean editManager(int mId,String mName,String description, String img_url);
	
	public boolean delManager(int mId);
	
}
