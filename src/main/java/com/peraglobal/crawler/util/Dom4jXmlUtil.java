package com.peraglobal.crawler.util;


import java.util.Map;

import com.mongodb.DBObject;

/**
 * 2015-7-2
 * @author yongqian.liu
 * 使用 Dom4j 解析 xml 文件
 */
public class Dom4jXmlUtil {
	
	/**
	 * 检查数据完整性
	 * @param kvs
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String checkIsFull(DBObject kvs){
		Map knowledgeKvsMap = kvs.toMap();
		for(Object key : knowledgeKvsMap.keySet()){
			if(knowledgeKvsMap.get(key)==null || knowledgeKvsMap.get(key).toString().equals("")){
				return "1";
			}
		}
		return "0";
	}
}
