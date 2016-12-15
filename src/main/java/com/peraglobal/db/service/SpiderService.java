package com.peraglobal.db.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.peraglobal.crawler.model.DbConst;
import com.peraglobal.crawler.model.SpiderConfiguration;
import com.peraglobal.crawler.process.DataImporter;
import com.peraglobal.crawler.process.SpiderManager;
import com.peraglobal.db.model.Crawler;

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
		// 创建配置参数对象
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(DbConst.CRAWLER_ID, crawler.getCrawlerId());
		params.put("metaDateFilePath", "C:/sdc/fileDownload");
		params.put(DbConst.CRAWLER_RULE, crawler.getExpress());
		
		// 创建数据库导入对象
		new DataImporter(new SpiderConfiguration(params)).doImport();
		
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

}