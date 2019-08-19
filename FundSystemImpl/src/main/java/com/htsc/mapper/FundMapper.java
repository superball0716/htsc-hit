package com.htsc.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.htsc.entity.Fund;

public interface FundMapper {
	@Select("SELECT * FROM fund WHERE pId = #{pId}")
	Fund getProductBypId(int pId);
	
	@Update("update fund set productnum = productnum - 1,version_id = #{version_id}+1 where productnum > 0 and pId = #{pId} and version_id = #{version_id}")
	boolean decreseProductByLock(@Param("pId") int pId,@Param("version_id") int version_id);
	
	@Select("select version_id from fund WHERE pId=#{pId}")
	int getVersionId(int pId);
	
	@Select("select * from fund where CONCAT(pName,'(',identifier,')') like CONCAT('%',#{keyword},'%')")
	List<Fund> vagueSearchByFund(String keyword);
	
	@Select("select * from fund order by identifier desc limit 1")
	Fund getNewestFund();
	
	
	@Update("update fund set pName=#{pName},description=#{description},worth=#{worth},startPoint=#{startPoint},profitRate=#{profitRate},"
			+ "productNum=#{productNum} where pId=#{pId}")
	int updateFundNormal(@Param("pId") int pId,@Param("pName") String pName,@Param("description") String description,
			@Param("worth") BigDecimal worth, @Param("startPoint") BigDecimal startPoint, @Param("profitRate") BigDecimal profitRate,
			@Param("productNum") int productNum);
	
	
	@Insert("Insert into fund(identifier,pName,description,worth,startPoint,profitRate,productnum,version_id) values "
			+ "(#{identifier},#{pName},#{description},#{worth},#{startPoint},#{profitRate},#{productNum},1)")
	int addFundNormal(@Param("identifier") String identifier,@Param("pName") String pName,@Param("description") String description,
			@Param("worth") BigDecimal worth, @Param("startPoint") BigDecimal startPoint, @Param("profitRate") BigDecimal profitRate,
			@Param("productNum") int productNum);
	
	@Select("select pId from fund where identifier = #{identifier}")
	int getPIdByIdentifier(String identifier);
	


}
