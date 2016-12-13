package com.peraglobal.db.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.peraglobal.db.model.Crawler;
import com.peraglobal.db.service.CrawlerService;


/**
 *  <code>CrawlerController.java</code>
 *  <p>功能:数据库采集 Controller
 *  
 *  <p>Copyright 安世亚太 2016 All right reserved.
 *  @author yongqian.liu	
 *  @version 1.0 
 *  </br>最后修改人 无
 */
@RestController
@RequestMapping("db")
public class CrawlerController {
	
	@Autowired
	private CrawlerService crawlerService;
	
	
	/**
	 * 获得数据库采集列表
	 * @param groupId 组Id （多用户区分不同用户）
	 * @return List<Crawler> 数据库采集列表
	 * @since 1.0
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/getCrawlerList/{groupId}", method = RequestMethod.GET)
	public ResponseEntity<List<Crawler>> getCrawlerList(@PathVariable("groupId") String groupId) {
		try {
			List<Crawler> crawlers = crawlerService.getCrawlerList(groupId);
			return new ResponseEntity<>(HttpStatus.OK).accepted().body(crawlers);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	/**
	 * 获得数据库采集
	 * @param crawlerId 数据库采集 ID
	 * @return crawler 数据库采集
	 * @since 1.0
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/getCrawler/{crawlerId}", method = RequestMethod.GET)
	public ResponseEntity<Crawler> getCrawler(@PathVariable("crawlerId") String crawlerId) {
		try {
			Crawler crawler = crawlerService.getCrawler(crawlerId);
			return new ResponseEntity<>(HttpStatus.OK).accepted().body(crawler);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	/**
	 * 创建数据库采集
	 * @param crawler 数据库采集对象
	 * @return crawlerId 创建成功返回数据库采集 ID
	 * @since 1.0
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/createCrawler", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createCrawler(@RequestBody Crawler crawler) {
		try {
			String crawlerId = crawlerService.createCrawler(crawler);
			if(crawlerId != null) {
				return new ResponseEntity<>(HttpStatus.CREATED).accepted().body(crawlerId);
			}
		} catch (Exception e) {}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}
	
	/**
	 * 移除数据库采集
	 * @param crawlerId 数据库采集 ID
	 * @return 状态码
	 * @since 1.0
	 */
	@RequestMapping(value = "/removeCrawler/{crawlerId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeCrawler(@PathVariable("crawlerId") String crawlerId) {
		try {
			crawlerService.removeCrawler(crawlerId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}
	
	/**
	 * 编辑数据库采集
	 * @param crawler 数据库采集对象
	 * @return 状态码
	 * @since 1.0
	 */
	@RequestMapping(value = "/editCrawler", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> editCrawler(@RequestBody Crawler crawler) {
		try {
			crawlerService.editCrawler(crawler);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}
	
	/**
	 * 开始数据库采集
	 * @param crawler 数据库采集对象
	 * @return 状态码
	 * @since 1.0
	 */
	@RequestMapping(value = "/start", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> start(@RequestBody Crawler crawler) {
		try {
			crawlerService.start(crawler.getCrawlerId());
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}
	
	/**
	 * 停止数据库采集
	 * @see 2016-12-7	 
	 * @param crawler 数据库采集
	 * @return 状态码
	 * @since 1.0
	 */
	@RequestMapping(value = "/stop", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> stop(@RequestBody Crawler crawler) {
		try {
			crawlerService.stop(crawler.getCrawlerId());
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
	}
}