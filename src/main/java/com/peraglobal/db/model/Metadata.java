package com.peraglobal.db.model;

import java.io.Serializable;
import java.util.Date;

/**
 *  <code>Metadata.java</code>
 *  <p>功能：元数据存储类
 *  
 *  <p>Copyright 安世亚太 2016 All right reserved.
 *  @author yongqian.liu	
 *  @version 1.0
 *  @see 2016-12-19
 *  </br>最后修改人 无
 */
public class Metadata implements Serializable {

	private static final long serialVersionUID = -5577310925375978878L;

	/**
	 * @category ID
	 */
	private String metadataId;

	/**
	 * @category 爬虫 ID
	 */
	private String crawlerId;
	
	/**
	 * @category 元数据 json 格式
	 */
	private String metadta;
	
	/**
	 * @category md5 码
	 */
	private String md;
	
	/**
	 * @category 创建时间
	 */
	private Date createTime;
	
	/**
	 * @category 更新时间
	 */
	private Date updateTime;

	public String getMetadataId() {
		return metadataId;
	}

	public void setMetadataId(String metadataId) {
		this.metadataId = metadataId;
	}

	public String getCrawlerId() {
		return crawlerId;
	}

	public void setCrawlerId(String crawlerId) {
		this.crawlerId = crawlerId;
	}

	public String getMetadta() {
		return metadta;
	}

	public void setMetadta(String metadta) {
		this.metadta = metadta;
	}

	public String getMd() {
		return md;
	}

	public void setMd(String md) {
		this.md = md;
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
