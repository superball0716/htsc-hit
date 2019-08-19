package com.htsc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import com.htsc.entity.Manager;

public interface ManagerMapper {
	
	@Select("select * from manager where CONCAT(mName,'(',jobId,')') like CONCAT('%',#{keyword},'%')")
	List<Manager> vagueSearchByManager(String keyword);
	
	@Select("select * from manager where managerId=#{mId}")
	Manager getManagerDetail(int mId);
	
	@Select("select * from manager where mName like CONCAT('%',#{keyword},'%') or jobId like CONCAT('%',#{keyword},'%')")
	List<Manager> getManagerDetailByKeyword(String keyword);
	
	@Select("select jobId from manager order by jobId desc limit 1")
	String getNewestJobId();
	
	@Insert("insert into manager(mName,jobId,description,img_url,version_id) "
			+ "select #{mName},#{jobId},#{description},#{img_url},1 from DUAL " + 
			"where not exists(select jobId from manager where jobId=#{jobId})")
	boolean addManger(@Param("mName") String mName, @Param("jobId") String jobId, 
			@Param("description") String description, @Param("img_url") String img_url);
	
	@Update("update manager set mName = #{mName},description=#{description},img_url=#{img_url} where managerId=#{mId}")
	boolean editManager(@Param("mId") int mId, @Param("mName") String mName, 
			@Param("description") String description, @Param("img_url") String img_url);
	
	@Delete("delete from manager where managerId = #{mId}")
	boolean delManager(@Param("mId") int mId);
}
