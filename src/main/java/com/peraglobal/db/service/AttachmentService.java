package com.peraglobal.db.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peraglobal.db.mapper.AttachmentMapper;
import com.peraglobal.db.model.Attachment;

/**
 * 2016-1-8
 * @author xiaodan.jing
 * 附件逻辑处理类
 */
@Service
public class AttachmentService {
	
	@Autowired
    private AttachmentMapper attachmentMapper;
	
	
	/**
	 * 功能：根据任务ID和mongo objectId获取附加
	 * @param map
	 * @return
	 */
	public Attachment getAttachmentByFilePath(String taskId,String filePath){
		Map<String,String> map = new HashMap<String,String>();
		map.put("taskId", taskId);
		map.put("path", filePath);
		List<Attachment> list = attachmentMapper.getAttachmentByFilePath(map);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据元数据获取附件列表
	 * @param datumId
	 * @return
	 */
	public List<Attachment> findAllByDatumId(String datumId){
		Map<String,String> map = new HashMap<String,String>();
		map.put("datumId", datumId);
		List<Attachment> list = attachmentMapper.findAllByDatumId(map);
		return list;
	}

	public Attachment findById(String attachmentId) {
		// 后续完善
		return null;
	}

	public void save(Attachment a) {
		// 后续完善
	}

	public void updateIncrement(Attachment a) {
		// 后续完善
	}
	
}
