package com.htsc.mapper;

import java.sql.Timestamp;
import java.util.List;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.htsc.entity.secKill;

public interface secKillMapper {
	
	@Insert("Insert into secKill(pId,startTime,endTime,createTime) values (#{pId},#{startTime},#{endTime},#{createTime})")
	int addSecKill(@Param("pId")int pId, @Param("startTime")Timestamp startTime, 
			@Param("endTime")Timestamp endTime, @Param("createTime")Timestamp createTime);
	
	@Select("select * from secKill where pId=#{pId} order by createTime desc limit 1")
	secKill getSecKill(int pId);
	
	@Select("select * from (select * from secKill group by pId order by createTime desc) as t where now()<t.endTime")
	List<secKill> getActiveSecKill();
}
