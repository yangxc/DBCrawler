package com.peraglobal.mongodb.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

/**
 *  <code>Attachment.java</code>
 *  <p>功能：附件
 *  
 *  <p>Copyright 安世亚太 2016 All right reserved.
 *  @author yongqian.liu
 *  @version 1.0
 *  @see 2017-2-9
 */
public class Attachment implements Serializable {

	private static final long serialVersionUID = -4376521100216388622L;
	
	/**
	 * @category 附件 ID
	 */
	@Id
	private String attachmentId;
	
	/**
	 * @category 爬虫 ID
	 */
	private String crawlerId;
	
	/**
	 * @category 元数据 ID
	 */
	private String metadataId;
	
	/**
	 * @category 文件名称
	 */
	private String fileName;
	
	/**
	 * @category 文件类型
	 */
	private String fileType;
	
	/**
	 * @category 文件路径
	 */
	private String filePath;
	
	/**
	 * @category 字符数据
	 */
	private byte[] context;

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

	public String getMetadataId() {
		return metadataId;
	}

	public void setMetadataId(String metadataId) {
		this.metadataId = metadataId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public byte[] getContext() {
		return context;
	}

	public void setContext(byte[] context) {
		this.context = context;
	}
	
	
}
