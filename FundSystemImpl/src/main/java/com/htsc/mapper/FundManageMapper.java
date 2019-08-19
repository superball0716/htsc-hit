package com.htsc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.htsc.entity.FundManage;

public interface FundManageMapper {
		
	/*@Select("select fId,manager.managerId,jobId,mName,fund.pId,pName,description,worth,startPoint,profitRate,productnum "
			+ "from fund, FundManage,manager " 
			+ "where fund.pId = FundManage.pId and manager.managerId = FundManage.managerId and fund.pId in " 
			+ "(select pId from FundManage where managerId in "
			+ "(select managerId from manager where manager.jobId like '%${keyword}%' or uName like '%${keyword}%'))")
	List<FundManage> vagueSearchByUser(String keyword);*/
	
	@Select("select identifier,fId,manager.managerId,jobId,mName,fund.pId,pName,fund.description,worth,startPoint,profitRate,productnum " + 
			"from fund, FundManage,manager " + 
			"where fund.pId = FundManage.pId and manager.managerId = FundManage.managerId order by identifier desc")
	List<FundManage> getAllProductInfo();
	
	@Select("select identifier,fId,manager.managerId,jobId,mName,fund.pId,pName,fund.description,worth,startPoint,profitRate,productnum " + 
			"from fund, FundManage,manager " + 
			"where fund.pId = FundManage.pId and manager.managerId = FundManage.managerId and manager.managerId = #{managerId} order by identifier desc")
	List<FundManage> getProductInfoByManagerId(int managerId);
	
	
	@Select("select identifier,fId,manager.managerId,jobId,mName,fund.pId,pName,fund.description,worth,startPoint,profitRate,productnum " + 
			"from fund, FundManage,manager " + 
			"where fund.pId = FundManage.pId and manager.managerId = FundManage.managerId and fund.pId = #{pId} order by identifier desc")
	FundManage getProductInfoByPId(int pId);
	
	
	@Select("select identifier,fId,manager.managerId,jobId,mName,fund.pId,pName,fund.description,worth,startPoint,profitRate,productnum "
			+ "from fund, FundManage,manager " 
			+ "where fund.pId = FundManage.pId and manager.managerId = FundManage.managerId and "
			+ "CONCAT(fund.pName,'(',fund.identifier,')') like '%${fundkey}%' and fund.pId in " 
			+ "(select pId from FundManage where managerId in "
			+ "(select managerId from manager where "
			+ "CONCAT(mName,'(',manager.jobId,')') like '%${managerkey}%')) order by identifier desc")
	List<FundManage> getProductInfoByKeyword(String fundkey,String managerkey);
	
	@Update("update FundManage set managerId=#{mId} where pId=#{pId}")
	int updateFundManager(int mId,int pId);
	
	@Insert("insert into FundManage(managerId,pId) values (#{mId},#{pId})")
	int addFundManager(int mId,int pId);
	
	
	@Select("select managerId from FundManage where pId = #{pId}")
	int getmIdbypId(int pId);
	
	@Delete("delete from FundManage where managerId = #{mId}")
	boolean delFundManager(int mId);
	
}
