package com.htsc.service;

import java.util.List;

import com.htsc.bean.ProductInfo;
import com.htsc.bean.ProductNormal;
import com.htsc.bean.ProductSearch;

public interface ProductInfoService {
	/*
	 * �������в�Ʒid����������ǰ��ģ��ƥ��
	 * */
	public List<ProductSearch> vagueSearchByProduct(String keyword);
	
	/*
	 * ����pId��ѯ��Ʒ��Ϣ
	 * */
	public ProductInfo getProductInfoByPid(String pId);
	
	/*
	 * ���ݾ����˲�ѯ���в�Ʒ
	 * */
	public String getProductInfoByManger(String mId,int pageNum,int pageSize);
	
	/*
	 * ���������ؼ��ʲ�ѯ���в�Ʒ
	 * */
	public String getProductInfoByKeyword(String productKey,String managerKey,int pageNum,int pageSize);
	
	/*
	 * ��ȡ���л�����Ϣ�����ڳ�ʼչʾҳ�棬���ط�ҳ���ProductInfo
	 * */
	public String getAllProductInfo(int pageNum,int pageSize);
	
	/*
	 * ��ȡ�»����Ψһ���
	 * */
	public String newIndentifier();
	
	/*
	 * ���»��������Ϣ
	 * */
	public boolean updateFundNormal(ProductNormal p);
	
	/*
	 * �������������Ϣ
	 * */
	public boolean addNewFundNormal(ProductNormal p,String identifier);
	
	
	/*
	 * ����Ψһ��ʶ��ȡpId
	 * */
	public int getpIdByIdentifier(String identifier);

	
	
	
}
