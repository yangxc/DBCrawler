package com.peraglobal.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.peraglobal.dao.CrawlerMapper;
import com.peraglobal.model.Crawler;

/**
 *  <code>DBCrawlerController.java</code>
 *  <p>功能:数据库采集 Controller
 *  
 *  <p>Copyright 安世亚太 2016 All right reserved.
 *  @author yongqian.liu	
 *  @version 1.0 
 *  </br>最后修改人 无
 */
@RestController
@RequestMapping("crawler")
public class CrawlerController {

	
	@Autowired
	private CrawlerMapper crawlerMapper;
	
	
	@RequestMapping(value = "/getCrawler")
	@ResponseBody
	public String findCrawlerByCrawlerId(String crawlerId) {
		Crawler crawler = crawlerMapper.findCrawlerByCrawlerId(crawlerId);
		String crawlerName = crawler.getCrawlerName();
		return crawlerName;
	}
	
}
