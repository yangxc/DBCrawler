package com.peraglobal.db.service;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peraglobal.common.IDGenerate;
import com.peraglobal.db.mapper.CrawlerMapper;
import com.peraglobal.db.model.Crawler;
import com.peraglobal.db.model.CrawlerConst;
import com.peraglobal.spider.model.DbCrawler;

/**
 *  <code>TaskService.java</code>
 *  <p>功能:数据库采集业务逻辑功能 Service
 *  
 *  <p>Copyright 安世亚太 2016 All right reserved.
 *  @author yongqian.liu	
 *  @version 1.0
 *  2016-12-9
 *  </br>最后修改人 无
 */
@Service
public class CrawlerService {
	
	@Autowired
    private CrawlerMapper crawlerMapper;
	
	@Autowired
    private SpiderService spiderService;
	
	/**
	 * 根据组 ID 查询数据库采集列表
	 * @param groupId 组 ID
	 * @return List<Crawler> 数据库采集列表
	 * @throws Exception
	 */
	public List<Crawler> getCrawlerList(String groupId) throws Exception {
		return crawlerMapper.getCrawlerList(groupId);
	}
	
	/**
	 * 根据数据库采集 ID 查询数据库采集对象
	 * @param crawlerId 数据库采集 ID
	 * @return Crawler 数据库采集对象
	 * @throws Exception
	 */
	public Crawler getCrawler(String crawlerId) throws Exception {
		return crawlerMapper.getCrawler(crawlerId);
	}

	/**
	 * 创建数据库采集
	 * @param crawler 数据库采集对象
	 * @return crawlerId 数据库采集 ID
	 * @throws Exception
	 */
	public String createCrawler(DbCrawler dbCrawler) throws Exception {
		Crawler crawler = new Crawler();
		crawler.setCrawlerName(dbCrawler.getCrawlerName());
		crawler.setGroupId(dbCrawler.getGroupId());
		crawler.setGroupName(dbCrawler.getGroupName());
		// 根据当前数据库采集名称和组 ID 查询是否存在，则不创建
		Crawler c = crawlerMapper.getCrawlerByCrawlerName(crawler);
		if(c == null) {
			if (null == dbCrawler.getCrawlerId()) {
				crawler.setCrawlerId(IDGenerate.uuid());
			} else {
				crawler.setCrawlerId(dbCrawler.getCrawlerId());
			}
			// 默认状态为：就绪
			crawler.setState(CrawlerConst.STATE_READY);
			crawler.setCreateTime(new Date());
			crawler.setUpdateTime(new Date());
			JSONObject jsonObj = new JSONObject(dbCrawler.getDbConnection());  
			crawler.setExpress(jsonObj.toString());
			crawlerMapper.createCrawler(crawler);
			return crawler.getCrawlerId();
		}
		return null;
	}

	/**
	 * 通过数据库采集 ID 删除对象
	 * @param crawlerId 数据库采集 ID
	 * @throws Exception
	 */
	public void removeCrawler(String crawlerId) throws Exception {
		// 通过数据库采集 ID 查询对象是否存在
		Crawler c = crawlerMapper.getCrawler(crawlerId);
		if(c != null) {
			// 判断数据库采集对象是否在运行，如果状态为：非就绪，则存在任务调度器中
			if(c.getState().equals(CrawlerConst.STATE_READY)) {
				// 后续完善，停止任务
			}
			crawlerMapper.removeCrawler(crawlerId);
		}
	}

	/**
	 * 编辑数据库采集对象
	 * @param crawler 数据库采集对象
	 * @throws Exception
	 */
	public void editCrawler(DbCrawler dbCrawler) throws Exception {
		// 查询数据库采集对象是否存在
		Crawler crawler = new Crawler();
		crawler.setCrawlerName(dbCrawler.getCrawlerName());
		crawler.setGroupId(dbCrawler.getGroupId());
		crawler.setGroupName(dbCrawler.getGroupName());
		Crawler c = crawlerMapper.getCrawler(crawler.getCrawlerId());
		if(c != null) {
			JSONObject jsonObj = new JSONObject(dbCrawler.getDbConnection());  
			crawler.setExpress(jsonObj.toString());
			crawler.setUpdateTime(new Date());
			crawlerMapper.editCrawler(crawler);
		}
	}

	/**
	 * 开始数据库采集
	 * @param crawlerId 数据库采集 ID
	 * @throws Exception
	 */
	public void start(String crawlerId) throws Exception {
		Crawler crawler = crawlerMapper.getCrawler(crawlerId);
		
		// 数据库采集状态为：非开始，则开始任务
		if(crawler != null && !crawler.getState().equals(CrawlerConst.STATE_STRAT)) {
			
			// 更新任务状态
			crawler.setState(CrawlerConst.STATE_STRAT);
			crawler.setUpdateTime(new Date());
			crawlerMapper.updateStateByCrawler(crawler);
			
			// 执行爬虫采集开始功能
			spiderService.start(crawler);
		}
	}

	/**
	 * 停止数据库采集
	 * @param crawlerId 数据库采集 ID
	 * @throws Exception
	 */
	public void stop(String crawlerId) throws Exception {
		Crawler crawler = crawlerMapper.getCrawler(crawlerId);
		
		// 数据库采集状态为：开始，则停止
		if(crawler != null && crawler.getState().equals(CrawlerConst.STATE_STRAT)) {
			
			// 更新数据库采集状态为停止
			crawler.setState(CrawlerConst.STATE_STOP);
			crawler.setUpdateTime(new Date());
			crawlerMapper.updateStateByCrawler(crawler);
			
			// 执行爬虫采集停止功能
			spiderService.stop(crawler);
		}
	}
}
