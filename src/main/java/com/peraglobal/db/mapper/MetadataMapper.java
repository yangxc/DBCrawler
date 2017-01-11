package com.peraglobal.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.peraglobal.db.model.Metadata;

/**
 * <code>MetaDataMapper.java</code>
 * <p>
 * 功能：元数据存储
 * <p>
 * Copyright 安世亚太 2016 All right reserved.
 * 
 * @author yongqian.liu
 * @version 1.0
 * @see 2016-12-19 </br>
 * 		最后修改人 无
 */
@Mapper
public interface MetadataMapper {

	/**
	 * 根据组爬虫 ID 获得元数据列表
	 * 
	 * @param crawlerId
	 *            爬虫 ID
	 * @return List<Metadata> 元数据列表
	 */
	@Select("select * from metadata where crawlerId = #{crawlerId} limit 0, 100")
	public List<Metadata> getMetadatasByCrawlerId(String crawlerId);

	/**
	 * 根据 ID 查询元数据
	 * 
	 * @param metadataId
	 *            元数据 ID
	 * @return Metadata 元数据
	 */
	@Select("select * from metadata where metadataId = #{metadataId}")
	public Metadata getMetadata(String metadataId);

	/**
	 * 根据 MD5 码 查询元数据
	 * 
	 * @param md
	 *            MD5 码
	 * @return Metadata 元数据
	 */
	@Select("select * from metadata where md = #{md}")
	public Metadata getMetadataByMd(String md);

	/**
	 * 创建元数据
	 * 
	 * @param metadata
	 *            元数据对象
	 */
	@Insert("insert into metadata (metadataId, crawlerId, metadata, md, createTime, updateTime) values (#{metadataId}, #{crawlerId}, #{metadata}, #{md}, #{createTime}, #{updateTime})")
	public void createMetadata(Metadata metadata);

	/**
	 * 移除元数据
	 * 
	 * @param metadataId
	 *            元数据 ID
	 */
	@Delete("delete from metadata where metadataId = #{metadataId}")
	public void removeMetadata(String metadataId);

	/**
	 * 移除元数据
	 * 
	 * @param crawlerId
	 *            爬虫 ID
	 */
	@Delete("delete from metadata where crawlerId = #{crawlerId}")
	public void removeMetadataByCrawlerId(String crawlerId);

}
