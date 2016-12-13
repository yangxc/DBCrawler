package com.peraglobal.db.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.peraglobal.db.model.Attachment;

/**
 *  <code>AttachmentMapper.java</code>
 *  <p>功能:附件存储
 *  
 *  <p>Copyright 安世亚太 2016 All right reserved.
 *  @author yongqian.liu	
 *  @version 1.0
 *  @see 2016-12-13
 *  </br>最后修改人 无
 */
@Mapper
public interface AttachmentMapper {

	/**
	 * 功能：根据任务ID和mongo objectId获取附加
	 * 作者：井晓丹 2016-3-22
	 * @param map
	 * @return
	 */
	public List<Attachment> getAttachmentByFilePath(Map map);
	/**
	 * 功能：根据元数据id获取附件
	 * 作者：井晓丹 2016-3-22
	 * @param map
	 * @return
	 */
	public List<Attachment> findAllByDatumId(Map map);
	
}
