package com.htsc.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.htsc.bean.ProductInfo;
import com.htsc.entity.Fund;
import com.htsc.entity.FundManage;

public class ObjectConvertionUtil {
	private static ObjectConvertionUtil instance;
	
	private ObjectConvertionUtil() {
		
	}
	
	public static ObjectConvertionUtil getInstance() {
		if(instance == null) {
			instance = new ObjectConvertionUtil();
		}
		return instance;
	}
	
	public ProductInfo convertFromFundManage2ProductInfo(FundManage fundManage) {
    	ProductInfo p = new ProductInfo();
    	p.setDescription(fundManage.getDescription());
    	p.setfId(fundManage.getfId());
    	p.setJobId(fundManage.getJobId());
    	p.setpId(fundManage.getpId());
    	p.setIdentifier(fundManage.getIdentifier());
    	p.setpName(fundManage.getpName());
    	p.setProductNum(fundManage.getProductnum());
    	p.setProfitRate(fundManage.getProfitRate());
    	p.setStartPoint(fundManage.getStartPoint());
    	p.setmId(fundManage.getManagerId());
    	p.setmName(fundManage.getmName());
    	p.setWorth(fundManage.getWorth());
    	
    	return p;
	}
	
	public String convertFromFundPage2ProductPage(List<FundManage> allProduct,PageInfo<FundManage> FundPage) {
		List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
	    for(FundManage fundManage:allProduct) {
	    	ProductInfo p = convertFromFundManage2ProductInfo(fundManage);
	    	productInfoList.add(p);
	    }	    
	    PageInfo<ProductInfo> page = new PageInfo<ProductInfo>();
	    page.setTotal(FundPage.getTotal());
	    page.setPageNum(FundPage.getPageNum());
	    page.setPageSize(FundPage.getPageSize());
	    page.setList(productInfoList);
	    return JSON.toJSONString(page);
	}
	
	
	public String getStrByTimestamp(Timestamp t) {
        String result = "";  
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        try {   
            result = sdf.format(t);
        } catch (Exception e) {  
            e.printStackTrace();  
        }
        return result;
	}
}
