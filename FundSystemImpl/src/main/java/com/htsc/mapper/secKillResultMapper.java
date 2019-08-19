package com.htsc.mapper;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Insert;

public interface secKillResultMapper {
	@Insert("insert into secKillResult(pId,uId,createTime) values (#{pId},#{uId},#{createTime})")
	boolean insertSecKillResult(int pId,int uId,Timestamp createTime);
}
