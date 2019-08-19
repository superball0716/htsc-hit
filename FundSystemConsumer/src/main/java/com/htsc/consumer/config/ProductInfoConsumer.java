package com.htsc.consumer.config;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.htsc.bean.FundSearchForm;
import com.htsc.bean.ProductInfo;
import com.htsc.bean.ProductNormal;
import com.htsc.bean.ProductSearch;
import com.htsc.service.ProductInfoService;

public class ProductInfoConsumer {
	@Reference(url = "dubbo://localhost:12345")
	private ProductInfoService productInfoService;

	public List<FundSearchForm> vagueSearchByFund(String keyword){
		List<ProductSearch> productSearchList = productInfoService.vagueSearchByProduct(keyword);		
		
		List<FundSearchForm> result = new ArrayList<FundSearchForm>();
		for (ProductSearch ps : productSearchList) {
			FundSearchForm fs = new FundSearchForm();
			fs.setValue(ps.getpName()+"("+String.valueOf(ps.getIdentifier())+")");
			fs.setpId(ps.getpId());
			
			result.add(fs);		
		}
		return result;
	}
	
	public String getAllFund(int pageNum,int pageSize) {
		String allFund= productInfoService.getAllProductInfo(pageNum, pageSize);
		
		return allFund;
	}
	
	public String getFundByManager(String mId,int pageNum,int pageSize) {
		String fund = productInfoService.getProductInfoByManger(mId, pageNum, pageSize);
		
		return fund;
	}
	
	public ProductInfo getFundByPId(String pId) {
		ProductInfo fund = productInfoService.getProductInfoByPid(pId);
		
		return fund;
	}
	
	public String getFundByKeyword(String fundKey,String userKey, int pageNum, int pageSize) {
		String fund = productInfoService.getProductInfoByKeyword(fundKey, userKey, pageNum, pageSize);
		
		return fund;
	}
	
	public String getNewestIdentifier() {
		return productInfoService.newIndentifier();
	}
	
	public boolean updateFundNormal(ProductNormal p) {
		boolean result = productInfoService.updateFundNormal(p);
		
		return result;
	}
	
	public boolean addFundNormal(ProductNormal p,String identifier) {
		boolean result = productInfoService.addNewFundNormal(p, identifier);
				
		return result;
	}
	
	public int getpIdByIdentifier(String identifier) {
		return productInfoService.getpIdByIdentifier(identifier);
	}
	
}
