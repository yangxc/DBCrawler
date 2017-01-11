package com.peraglobal.db.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.peraglobal.db.model.Rule;

/**
 * <code>RuleMapper.java</code>
 * <p>
 * 功能:规则表达式存储
 * <p>
 * Copyright 安世亚太 2016 All right reserved.
 * 
 * @author yongqian.liu
 * @version 1.0
 * @see 2016-12-6 </br>
 * 		最后修改人 无
 */
@Mapper
public interface RuleMapper {

	/**
	 * 根据采集 ID 查询规则
	 * @param crawlerId
	 * @return
	 */
	@Select("select * from rule where crawlerId = #{crawlerId}")
	public Rule getRule(String crawlerId);

	/**
	 * 插入规则文件
	 * @param rule
	 */
	@Insert("insert into rule (ruleId, crawlerId, express) values (#{ruleId}, #{crawlerId}, #{express,javaType=string,jdbcType=BLOB})")
	public void createRule(Rule rule);

	/**
	 * 根据采集 ID 删除规则文件
	 * @param crawlerId
	 */
	@Delete("delete from rule where crawlerId = #{crawlerId}")
	public void removeRule(String crawlerId);

	/**
	 * 更新规则文件
	 * @param rule
	 */
	@Update("update rule set express = #{express,javaType=string,jdbcType=BLOB} where crawlerId = #{crawlerId}")
	public void editRule(Rule rule);

}
