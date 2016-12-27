package com.peraglobal.db.model;

import java.io.Serializable;

import com.peraglobal.spider.model.DbConnection;

/**
 *  <code>CrawlerJdbc.java</code>
 *  <p>功能：DB采集参数转换类
 *  
 *  <p>Copyright 安世亚太 2016 All right reserved.
 *  @author yongqian.liu	
 *  @version 1.0
 *  @see 2016-12-16
 *  </br>最后修改人 无
 */
public class CrawlerJdbc implements Serializable {

	private static final long serialVersionUID = 2540369499127783004L;

	/**
	 * @category 采集 ID
	 */
	private String crawlerId;
	
	/**
	 * @category 采集名称
	 */
	private String crawlerName;
	
	/**
	 * @category 组 ID
	 */
	private String groupId;
	
	/**
	 * @category 组名称
	 */
	private String groupName;
	
	/**
	 * @category jdbc对象
	 */
	private DbConnection dbConnection;
	

	public String getCrawlerId() {
		return crawlerId;
	}

	public void setCrawlerId(String crawlerId) {
		this.crawlerId = crawlerId;
	}

	public String getCrawlerName() {
		return crawlerName;
	}

	public void setCrawlerName(String crawlerName) {
		this.crawlerName = crawlerName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public DbConnection getDbConnection() {
		return dbConnection;
	}

	public void setDbConnection(DbConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

}
