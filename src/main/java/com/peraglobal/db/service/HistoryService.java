package com.peraglobal.db.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peraglobal.common.IDGenerate;
import com.peraglobal.db.mapper.HistoryMapper;
import com.peraglobal.db.model.History;

/**
 *  <code>HistoryService.java</code>
 *  <p>功能：历史记录功能 Service
 *  
 *  <p>Copyright 安世亚太 2016 All right reserved.
 *  @author yongqian.liu	
 *  @version 1.0
 *  2016-12-19
 *  </br>最后修改人 无
 */
@Service
public class HistoryService {
	
	@Autowired
    private HistoryMapper historyMapper;

	
	/**
	 * 根据组爬虫 ID 获得历史记录列表
	 * @param crawlerId 爬虫 ID
	 * @return List<History> 历史记录列表
	 */
	public List<History> getHistorysByCrawlerId(String crawlerId) throws Exception {
		return historyMapper.getHistorysByCrawlerId(crawlerId);
	}
	
	/**
	 * 根据 ID 查询历史记录
	 * @param metadataId 历史记录 ID
	 * @return History 历史记录
	 */
	public History getHistory(String id) throws Exception {
		return historyMapper.getHistory(id);
	}

	/**
	 * 创建历史记录
	 * @param history 历史记录对象
	 * @throws Exception
	 */
	public String createHistory(History history) throws Exception {
		// 设置历史记录的版本号
		List<History> listorys = historyMapper.getHistorysByCrawlerId(history.getCrawlerId());
		int version = 0;
		if (listorys != null && listorys.size() > 0) {
			version = historyMapper.queryVersion(history.getCrawlerId());
		}
		
		history.setId(IDGenerate.uuid());
		history.setVersion(version + 1); // 最大版本 + 1
		history.setPageCrawledCount(0);
		history.setHasException(0);
		history.setStartDate(new Date());
		history.setCreateTime(new Date());
		history.setUpdateTime(new Date());
		historyMapper.createHistory(history);
		return history.getId();
	}

	/**
	 * 通过移除历史记录 ID 删除对象
	 * @param id 历史记录 ID
	 * @throws Exception
	 */
	public void removeHistory(String id) throws Exception {
		historyMapper.removeHistory(id);
	}
	
	/**
	 * 通过爬虫 ID 删除对象
	 * @param crawlerId 爬虫 ID
	 * @throws Exception
	 */
	public void removeHistoryByCrawlerId(String crawlerId) throws Exception {
		historyMapper.removeHistoryByCrawlerId(crawlerId);
	}
	
	/**
	 * 编辑历史记录对象
	 * @param history 历史记录对象
	 * @throws Exception
	 */
	public void editHistory(History history) throws Exception {
		historyMapper.editHistory(history);
	}

	/**
	 * 更新爬虫数量
	 * @param crawlerId 爬虫 ID
	 * @throws Exception
	 */
	public void updatePageCount(String crawlerId)  throws Exception {
		int version = historyMapper.queryVersion(crawlerId);
		History history = new History();
		history.setCrawlerId(crawlerId);
		history.setVersion(version);
		history = historyMapper.getLastHistory(history);
		history.setPageCrawledCount(history.getPageCrawledCount() + 1); // 采集数据 + 1
		history.setUpdateTime(new Date());
		historyMapper.updatePageCount(history);
	}

	/**
	 * 更新异常信息
	 * @param crawlerId 爬虫 ID
	 * @param message
	 */
	public void updateExcetion(String crawlerId, String message) {
		// 异常信息保存字符长度控制在 1000 字以内
		if (message != null && message.length() > 1000) {
			message = message.substring(0, 1000);
		}
		int version = historyMapper.queryVersion(crawlerId);
		History history = new History();
		history.setCrawlerId(crawlerId);
		history.setVersion(version);
		history = historyMapper.getLastHistory(history);
		history.setHasException(1);
		history.setExceptionMessage(message);
		history.setUpdateTime(new Date());
		historyMapper.updateExcetion(history);
	}

	/**
	 * 更新停止时间
	 * @param crawlerId
	 */
	public void stopHistory(String crawlerId) {
		int version = historyMapper.queryVersion(crawlerId);
		History history = new History();
		history.setCrawlerId(crawlerId);
		history.setVersion(version);
		history = historyMapper.getLastHistory(history);
		history.setStopDate(new Date());
		history.setUpdateTime(new Date());
		historyMapper.updateExcetion(history);
	}

	public int getCountByCrawlerId(String crawlerId) {
		List<History> historys = historyMapper.getHistorysByCrawlerId(crawlerId);
		int count = 0;
		if (historys != null) {
			for (History history : historys) {
				count += history.getPageCrawledCount();
			}
		}
		return count;
	}
	
}
