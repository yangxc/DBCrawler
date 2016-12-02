package com.peraglobal.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import com.peraglobal.model.Crawler;

@Mapper
public interface CrawlerMapper {
	
	@Select("select * from Crawler where crawlerId = #{crawlerId}")
	public Crawler findCrawlerByCrawlerId(@Param("crawlerId")String crawlerId);
	
	@Select("select * from Crawler where crawlerName = #{crawlerName}")
	public Crawler findCrawlerByCrawlerName(@Param("crawlerName")String crawlerName);

}
