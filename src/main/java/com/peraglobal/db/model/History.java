package com.peraglobal.db.model;

import java.io.Serializable;
import java.util.Date;

/**
 *  <code>History.java</code>
 *  <p>功能：监控信息，历史记录
 *  
 *  <p>Copyright 安世亚太 2016 All right reserved.
 *  @author yongqian.liu
 *  @version 1.0
 *  @see 2016-12-19
 */
public class History implements Serializable {
	
	private static final long serialVersionUID = 4066968352822240331L;

	/**
	 * @category ID
	 */
	private String id;

	/**
	 * @category 爬虫 ID
	 */
	private String crawlerId;
	
	/**
	 * @category 版本号
	 */
	private int version;
	
	/**
	 * @category 抓取数据的数量
	 */
	private int pageCrawledCount;
	
	/**
	 * @category 指示是否存在异常
	 */
	private int hasException;
	
	/**
	 * @category 异常对应的信息
	 */
	private String exceptionMessage;

	/**
	 * @category 爬虫的启动时间
	 */
	private Date startDate;
	
	/**
	 * @category 爬虫的停止时间
	 */
	private Date StopDate;
	
	/**
	 * @category 创建时间
	 */
	private Date createTime;
	
	/**
	 * @category 更新时间
	 */
	private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCrawlerId() {
		return crawlerId;
	}

	public void setCrawlerId(String crawlerId) {
		this.crawlerId = crawlerId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getPageCrawledCount() {
		return pageCrawledCount;
	}

	public void setPageCrawledCount(int pageCrawledCount) {
		this.pageCrawledCount = pageCrawledCount;
	}

	public int getHasException() {
		return hasException;
	}

	public void setHasException(int hasException) {
		this.hasException = hasException;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStopDate() {
		return StopDate;
	}

	public void setStopDate(Date stopDate) {
		StopDate = stopDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	
	
}
