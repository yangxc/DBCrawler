package com.peraglobal.db.model;


import java.io.Serializable;
import java.util.Date;

public class Attachment implements Serializable {

	private static final long serialVersionUID = -7563905349055068738L;
	
	/**
	 * @category 主键
	 */
	private String attachmentId;
	
	/**
	 * @category 爬虫 ID
	 */
	private String crawlerId;
	
	/**
	 * @category 附件 Id
	 */
	private String datumId;
	
	/**
	 * @category 附件名称
	 */
	private String name;
	
	/**
	 * @category 是否失败
	 */
	private String isFail;
	
	/**
	 * @category 路径
	 */
	private String path;
	
	/**
	 * @category 类型
	 */
	private String type;
	
	/**
	 * @category txt
	 */
	private String txt;
	
	/**
	 * @category 文件大小
	 */
	private String fileSize;
	
	/**
	 * @category 创建时间
	 */
	private Date createTime;
	
	/**
	 * @category 更新时间
	 */
	private Date updateTime;
	

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getCrawlerId() {
		return crawlerId;
	}

	public void setCrawlerId(String crawlerId) {
		this.crawlerId = crawlerId;
	}

	public String getDatumId() {
		return datumId;
	}

	public void setDatumId(String datumId) {
		this.datumId = datumId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsFail() {
		return isFail;
	}

	public void setIsFail(String isFail) {
		this.isFail = isFail;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
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
