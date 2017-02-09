package com.peraglobal.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.peraglobal.mongodb.model.Attachment;

/**
 *  <code>AttachmentRepository.java</code>
 *  <p>功能：附件存储
 *  
 *  <p>Copyright 安世亚太 2016 All right reserved.
 *  @author yongqian.liu
 *  @version 1.0
 *  @see 2017-2-9
 */
public interface AttachmentRepository extends MongoRepository<Attachment, String> {

	/**
	 * 根据附件 Id 查询附件
	 * @param attachment
	 * @return
	 */
	public Attachment findByAttachmentId(String attachmentId);
	
	/**
	 * 根据爬虫 Id 查询附件集合
	 * @param crawlerId
	 * @return
	 */
	public List<Attachment> findByCrawlerId(String crawlerId);
	
	/**
	 * 根据元数据 ID 查询附件
	 * @param metadataId
	 * @return
	 */
	public Attachment findByMetadataId(String metadataId);
	
}
