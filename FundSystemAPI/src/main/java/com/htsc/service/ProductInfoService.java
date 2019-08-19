package com.htsc.service;

import java.util.List;

import com.htsc.bean.ProductInfo;
import com.htsc.bean.ProductNormal;
import com.htsc.bean.ProductSearch;

public interface ProductInfoService {
	/*
	 * 返回所有产品id和名称用于前端模糊匹配
	 * */
	public List<ProductSearch> vagueSearchByProduct(String keyword);
	
	/*
	 * 根据pId查询产品信息
	 * */
	public ProductInfo getProductInfoByPid(String pId);
	
	/*
	 * 根据经理人查询所有产品
	 * */
	public String getProductInfoByManger(String mId,int pageNum,int pageSize);
	
	/*
	 * 根据搜索关键词查询所有产品
	 * */
	public String getProductInfoByKeyword(String productKey,String managerKey,int pageNum,int pageSize);
	
	/*
	 * 获取所有基金信息，用于初始展示页面，返回分页后的ProductInfo
	 * */
	public String getAllProductInfo(int pageNum,int pageSize);
	
	/*
	 * 获取新基金的唯一编号
	 * */
	public String newIndentifier();
	
	/*
	 * 更新基金基础信息
	 * */
	public boolean updateFundNormal(ProductNormal p);
	
	/*
	 * 新增基金基础信息
	 * */
	public boolean addNewFundNormal(ProductNormal p,String identifier);
	
	
	/*
	 * 根据唯一标识获取pId
	 * */
	public int getpIdByIdentifier(String identifier);

	
	
	
}
