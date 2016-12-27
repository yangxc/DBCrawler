package com.peraglobal.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peraglobal.db.model.Crawler;
import com.peraglobal.db.model.History;
import com.peraglobal.spider.model.DbConnection;
import com.peraglobal.spider.model.DbField;
import com.peraglobal.spider.model.DbTable;
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

	@Autowired
   	private HistoryService historyService;
	
	
	/**
	 * 开始爬虫
	 * @param crawler
	 * @throws Exception 
	 */
	public void start(Crawler crawler) throws Exception {
		
		// 创建数据库导入对象
		DbSpider.create().setCrawler(crawler).register();
		// 开始爬虫操作
		SpiderManager.start(crawler.getCrawlerId());
		
		// 添加监控信息，历史记录生成一条新记录
		History history = new History();
		history.setCrawlerId(crawler.getCrawlerId());
		historyService.createHistory(history);
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
		
		// 修改监控信息，历史记录生成停止时间
		historyService.stopHistory(crawler.getCrawlerId());
		
	}

	/**
	 * 获得所有表
	 * @param jdbc
	 * @return
	 */
	public List<DbTable> getTables(DbConnection dbConnection) {
		List<DbTable> jdbcTable = MetaDataBuilder.getTables(dbConnection, null);
		if(jdbcTable != null) {
			for (DbTable table : jdbcTable) {
				List<DbField> fields = MetaDataBuilder.getFields(dbConnection, table);
				table.setFields(fields);
			}
			return jdbcTable;
		}
		return null;
	}
	
}