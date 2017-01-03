package com.peraglobal.db.mapper;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.peraglobal.db.model.Rule;

@Mapper
public interface RuleMapper {
	
    @Select("select * from rule where crawlerId = #{crawlerId}")
    public Rule getRule(String crawlerId);
   
    @Insert("insert into rule (ruleId, crawlerId, express) values (#{ruleId}, #{crawlerId}, #{express,javaType=string,jdbcType=BLOB})")  
    public void createRule(Rule rule);

    @Delete("delete from rule where crawlerId = #{crawlerId}")
	public void removeRule(String crawlerId);

    @Update("update rule set express = #{express,javaType=string,jdbcType=BLOB} where crawlerId = #{crawlerId}")
	public void editRule(Rule rule);

}
