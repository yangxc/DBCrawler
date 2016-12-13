package com.peraglobal.db.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peraglobal.db.mapper.CrawlerMapper;
import com.peraglobal.db.model.Crawler;
import com.peraglobal.db.model.CrawlerConst;

/**
 *  <code>TaskService.java</code>
 *  <p>功能:数据库采集功能 Service
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
	public String createCrawler(Crawler crawler) throws Exception {
		// 根据当前数据库采集名称和组 ID 查询是否存在，则不创建
		Crawler c = crawlerMapper.getCrawlerByCrawlerName(crawler);
		if(c == null) {
			// uuid 任务 ID
			crawler.setCrawlerId(java.util.UUID.randomUUID().toString());
			// 默认状态为：就绪
			crawler.setState(CrawlerConst.STATE_READY);
			crawler.setCreateTime(new Date());
			crawler.setUpdateTime(new Date());
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
	public void editCrawler(Crawler crawler) throws Exception {
		// 查询数据库采集对象是否存在
		Crawler c = crawlerMapper.getCrawler(crawler.getCrawlerId());
		if(c != null) {
			// 判断数据库采集对象是否在运行，如果状态为：非就绪，则存在任务调度器中
			if(c.getState().equals(CrawlerConst.STATE_READY)) {
				// 后续完善，停止任务
			}
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
		Crawler c = crawlerMapper.getCrawler(crawlerId);
		// 数据库采集状态为：非开始，则开始任务
		if(c != null && !c.getState().equals(CrawlerConst.STATE_STRAT)) {
			// 更新任务状态
			c.setState(CrawlerConst.STATE_STRAT);
			c.setUpdateTime(new Date());
			crawlerMapper.updateStateByCrawler(c);
		}
	}

	/**
	 * 停止数据库采集
	 * @param crawlerId 数据库采集 ID
	 * @throws Exception
	 */
	public void stop(String crawlerId) throws Exception {
		Crawler t = crawlerMapper.getCrawler(crawlerId);
		// 数据库采集状态为：开始，则停止
		if(t != null && t.getState().equals(CrawlerConst.STATE_STRAT)) {
			// 更新数据库采集状态为停止
			t.setState(CrawlerConst.STATE_STOP);
			t.setUpdateTime(new Date());
			crawlerMapper.updateStateByCrawler(t);
		}
	}
}
