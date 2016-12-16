package com.peraglobal.db.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.peraglobal.db.model.Crawler;
import com.peraglobal.spider.model.JdbcConnection;
import com.peraglobal.spider.model.JdbcField;
import com.peraglobal.spider.model.JdbcTable;
import com.peraglobal.spider.process.DbSpider;
import com.peraglobal.spider.process.MetaDataBuilder;
import com.peraglobal.spider.process.SpiderManager;

/**
 *  <code>SpiderService.java</code>
 *  <p>功能:数据库爬虫功能入口 Service
 *  
 *  <p>Copyright 安世亚太 2016 All right reserved.
 *  @author yongqian.liu	
 *  @version 1.0
 *  2016-12-15
 *  </br>最后修改人 无
 */
@Service("spiderService")
public class SpiderService {

	/**
	 * 开始爬虫
	 * @param crawler
	 */
	public void start(Crawler crawler) {
		
		// 创建数据库导入对象
		DbSpider.create().setCrawler(crawler).register();
		// 开始爬虫操作
		SpiderManager.start(crawler.getCrawlerId());
	}

	/**
	 * 停止爬虫
	 * @param crawler
	 */
	public void stop(Crawler crawler) {
		// 停止爬虫
		SpiderManager.stop(crawler.getCrawlerId());
		
		// 销毁爬虫
		SpiderManager.remove(crawler.getCrawlerId());
	}

	/**
	 * 获得所有表
	 * @param jdbc
	 * @return
	 */
	public List<JdbcTable> getTables(JdbcConnection jdbc) {
		List<JdbcTable> jdbcTable = MetaDataBuilder.getTables(jdbc, null);
		if(jdbcTable != null) {
			for (JdbcTable table : jdbcTable) {
				List<JdbcField> fields = MetaDataBuilder.getFields(jdbc, table);
				table.setFields(fields);
			}
			return jdbcTable;
		}
		return null;
	}
	
}