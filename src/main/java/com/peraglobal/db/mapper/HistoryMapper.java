package com.peraglobal.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.peraglobal.db.model.History;

/**
 * <code>HistoryMapper.java</code>
 * <p>
 * 功能：历史记录
 * 
 * <p>
 * Copyright 安世亚太 2016 All right reserved.
 * 
 * @author yongqian.liu
 * @version 1.0
 * @see 2016-12-19 </br>
 * 		最后修改人 无
 */
@Mapper
public interface HistoryMapper {

	/**
	 * 根据组爬虫 ID 获得历史记录列表
	 * 
	 * @param crawlerId
	 *            爬虫 ID
	 * @return List<History> 历史记录列表
	 */
	@Select("select * from history where crawlerId = #{crawlerId} order by version ")
	public List<History> getHistorysByCrawlerId(String crawlerId);

	/**
	 * 根据 ID 查询历史记录
	 * 
	 * @param id
	 *            历史记录 ID
	 * @return History 历史记录
	 */
	@Select("select * from history where id = #{id}")
	public History getHistory(String id);

	/**
	 * 根据 ID 查询历史记录
	 * 
	 * @param id
	 *            历史记录 ID
	 * @return History 历史记录
	 */
	@Select("select * from history where version = #{version} and crawlerId = #{crawlerId}")
	public History getLastHistory(History history);

	/**
	 * 创建历史记录
	 * 
	 * @param metadata
	 *            历史记录对象
	 */
	@Insert("insert into history (id, crawlerId, version, pageCrawledCount, hasException, exceptionMessage, startDate, StopDate, createTime, updateTime) values (#{id}, #{crawlerId}, #{version}, #{pageCrawledCount}, #{hasException}, #{exceptionMessage}, #{startDate}, #{startDate}, #{createTime}, #{updateTime})")
	public void createHistory(History history);

	/**
	 * 移除历史记录
	 * 
	 * @param id
	 *            历史记录 ID
	 */
	@Delete("delete from history where id = #{id}")
	public void removeHistory(String id);

	/**
	 * 移除历史记录
	 * 
	 * @param crawlerId
	 *            爬虫 ID
	 */
	@Delete("delete from history where crawlerId = #{crawlerId}")
	public void removeHistoryByCrawlerId(String crawlerId);

	/**
	 * 编辑历史记录采集数量
	 * 
	 * @param history
	 *            历史记录对象
	 */
	@Update("update history set pageCrawledCount = #{pageCrawledCount}, hasException = #{hasException}, exceptionMessage = #{exceptionMessage}, StopDate = #{StopDate}, updateTime = #{updateTime} where id = #{id}")
	public void editHistory(History history);

	/**
	 * 根据爬虫 ID 查询最大版本号
	 * 
	 * @param crawlerId
	 * @return
	 */
	@Select("select max(version) from history where crawlerId = #{crawlerId}")
	public int queryVersion(String crawlerId);

	/**
	 * 更新爬虫数量
	 * 
	 * @param history
	 */
	@Update("update history set pageCrawledCount = #{pageCrawledCount}, updateTime = #{updateTime} where id = #{id}")
	public void updatePageCount(History history);

	/**
	 * 更新异常信息
	 * 
	 * @param history
	 */
	@Update("update history set hasException = #{hasException}, exceptionMessage = #{exceptionMessage}, updateTime = #{updateTime} where id = #{id}")
	public void updateExcetion(History history);

}
