package com.htsc.impl;

import com.htsc.bean.ProductInfo;
import com.htsc.bean.ProductNormal;
import com.htsc.bean.ProductSearch;
import com.htsc.entity.Fund;
import com.htsc.entity.FundManage;
import com.htsc.mapper.FundManageMapper;
import com.htsc.mapper.FundMapper;
import com.htsc.service.ProductInfoService;
import com.htsc.utils.ObjectConvertionUtil;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ProductInfoImpl implements ProductInfoService{

	@Autowired
	FundMapper pm;
	
	@Autowired
	FundManageMapper fmm;
	
	private static final Logger log = LoggerFactory.getLogger(ProductInfoImpl.class);

	@Override
	public ProductInfo getProductInfoByPid(String pId) {
	    ObjectConvertionUtil util = ObjectConvertionUtil.getInstance();

		FundManage fund = fmm.getProductInfoByPId(Integer.parseInt(pId));

	    return util.convertFromFundManage2ProductInfo(fund);
	}

	@Override
	public String getProductInfoByManger(String uId,int pageNum,int pageSize) {		
	    ObjectConvertionUtil util = ObjectConvertionUtil.getInstance();
	    
	    PageHelper.startPage(pageNum, pageSize);
		List<FundManage> fund = fmm.getProductInfoByManagerId(Integer.parseInt(uId));
	    PageInfo<FundManage> FundPage = new PageInfo<>(fund); 

	    return util.convertFromFundPage2ProductPage(fund, FundPage);
	}


	@Override
	public List<ProductSearch> vagueSearchByProduct(String keyword) {
		List<Fund> fundList = pm.vagueSearchByFund(keyword);
		
		List<ProductSearch> result = new ArrayList<ProductSearch>();
		for (Fund f : fundList) {
			ProductSearch ps = new ProductSearch();
			ps.setpId(f.getpId());
			ps.setpName(f.getpName());
			ps.setIdentifier(f.getIdentifier());
			result.add(ps);
		}
		return result;
	}

	@Override
	public String getProductInfoByKeyword(String productKey, String managerKey, int pageNum, int pageSize) {
	    log.info("ProductInfoImpl getProductInfoByKeyword start,productKey:{},managerKey:{}",productKey,managerKey);
	    ObjectConvertionUtil util = ObjectConvertionUtil.getInstance();
		
	    PageHelper.startPage(pageNum, pageSize);
	    log.info("------------------------,{},{}",productKey,managerKey);
	    List<FundManage> allProduct = fmm.getProductInfoByKeyword(productKey, managerKey);  
	    PageInfo<FundManage> FundPage = new PageInfo<>(allProduct);   
	    log.info("ProductInfoImpl getProductInfoByKeyword end");

	    return util.convertFromFundPage2ProductPage(allProduct, FundPage);
	}

	@Override
	public String getAllProductInfo(int pageNum, int pageSize) {
		
	    log.info("getAllProductInfo start");
	    
	    ObjectConvertionUtil util = ObjectConvertionUtil.getInstance();
		
	    PageHelper.startPage(pageNum, pageSize);
	    List<FundManage> allProduct = fmm.getAllProductInfo();    
	    PageInfo<FundManage> FundPage = new PageInfo<>(allProduct);

	    return util.convertFromFundPage2ProductPage(allProduct, FundPage);
	}

	@Override
	public String newIndentifier() {
		Fund f = pm.getNewestFund();
		int newest = Integer.parseInt(f.getIdentifier().substring(2)) + 1;
		String result="JJ";
		for(int i = 0; i < 6 - String.valueOf(newest).length(); i++) {
			result = result + "0";
		}
		result = result + String.valueOf(newest);
		
		return result;
	}

	@Override
	public boolean updateFundNormal(ProductNormal p) {
		int result = pm.updateFundNormal(p.getpId(), p.getpName(), p.getDescription(), p.getWorth(), p.getStartPoint(),
				p.getProfitRate(), p.getNum());
		if(result == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean addNewFundNormal(ProductNormal p, String identifier) {
		int result = pm.addFundNormal(identifier, p.getpName(), p.getDescription(), p.getWorth(), p.getStartPoint(), 
				p.getProfitRate(), p.getNum());
		if(result == 1) {
			return true;
		}
		return false;
	}

	@Override
	public int getpIdByIdentifier(String identifier) {
		return pm.getPIdByIdentifier(identifier);
	}



	
//	@Override
//	public ProductInfo getProductInfo(String key) {
//		// TODO 先查redis，判断是否有和超时情况
//		String redisKey = "product:" + key;
//		
//		ProductInfo cached = CacheUtils.getBean(redisKey, ProductInfo.class);
//		if(cached != null) {
//			return cached;
//		}
//		else {
//			Fund p = pm.getProductBypId(Integer.parseInt(key));
//			if(p != null) {
//				ProductInfo pd = ObjectConvertionUtil.getDetailFromProduct(p);
//				CacheUtils.saveBean(redisKey, pd, 60);
//				return pd;
//			}
//			//TODO 错误信息封装
//			return null;
//		}
//	}

}
