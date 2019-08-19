package com.htsc.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.htsc.entity.FundUser;

public interface FundUserMapper {
	@Select("SELECT * FROM FundUser WHERE uName = #{userName} and passwd = #{passwd}")
	FundUser getUserByNameAndPassword(String userName,String passwd);
	
	@Select("SELECT * from FundUser WHERE uName = #{userName}")
	FundUser userExist(String userName);
	
	@Insert("INSERT INTO FundUser(loginId,passwd,role,version_id,uName) select #{loginId},#{password},2,1,#{userName} from DUAL " + 
			"where not exists(select loginId from FundUser where loginId=#{loginId})")
	boolean addNormalUser(String loginId,String userName, String password);
	
	@Select("select uId from FundUser where loginId = #{loginId}")
	int getuIdByLoginId(String loginId);
	
	@Select("select loginId from FundUser order by loginId desc limit 1")
	String getNewestLoginId();
}
