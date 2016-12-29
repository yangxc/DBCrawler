package com.peraglobal.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.peraglobal.db.model.Crawler;

/**
 *  <code>CrawlerMapper.java</code>
 *  <p>功能:DB采集存储
 *  
 *  <p>Copyright 安世亚太 2016 All right reserved.
 *  @author yongqian.liu	
 *  @version 1.0
 *  @see 2016-12-9
 *  </br>最后修改人 无
 */
@Mapper
public interface CrawlerMapper {
	
	/**
	 * 根据组 ID 获得数据库采集列表
	 * @param groupId 组 ID
	 * @return List<Crawler> 任务列表
	 */
	@Select("select * from crawler where groupId = #{groupId}")
    public List<Crawler> getCrawlerList(String groupId);
   
	/**
	 * 根据 ID 数据库采集
	 * @param crawlerId 数据库采集 ID
	 * @return Crawler 任务
	 */
    @Select("select * from crawler where crawlerId = #{crawlerId}")
    public Crawler getCrawler(String crawlerId);
   
	/**
	 * 根据数据库采集名称和组 ID 数据库采集
	 * @param crawlerName 数据库采集名称
	 * @param groupId 组 ID
	 * @return Crawler 数据库采集
	 */
    @Select("select * from crawler where crawlerName = #{crawlerName} and groupId = #{groupId}")
    public Crawler getCrawlerByCrawlerName(Crawler crawler);
    
	/**
	 * 创建数据库采集
	 * @param Crawler 数据库采集对象
	 */
    @Insert("insert into crawler (crawlerId, crawlerName, groupId, groupName, express, state, createTime, updateTime) values (#{crawlerId}, #{crawlerName}, #{groupId}, #{groupName}, #{express,javaType=string,jdbcType=BLOB}, #{state}, #{createTime}, #{updateTime})")  
    public void createCrawler(Crawler crawler);

    /**
	 * 移除数据库采集
	 * @param crawlerId 任务 ID
	 */
    @Delete("delete from crawler where crawlerId = #{crawlerId}")
	public void removeCrawler(String crawlerId);

	/**
	 * 编辑数据库采集
	 * @param Crawler 数据库采集对象
	 */
    @Update("update crawler set crawlerName = #{crawlerName}, groupName = #{groupName}, express = #{express,javaType=string,jdbcType=BLOB}, updateTime = #{updateTime} where crawlerId = #{crawlerId}")
	public void editCrawler(Crawler crawler);

	/**
	 * 根据数据库采集 ID 修改状态
	 * @param Crawler 数据库采集对象
	 */
    @Update("update crawler set state = #{state}, updateTime = #{updateTime} where crawlerId = #{crawlerId}")
	public int updateStateByCrawler(Crawler crawler);

}
