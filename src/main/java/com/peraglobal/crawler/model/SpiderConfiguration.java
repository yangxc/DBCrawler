package com.peraglobal.crawler.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

import com.peraglobal.crawler.process.RequestInfo;

public class SpiderConfiguration extends Configuration {

	private Table table;
	private String dataSourceName; // 数据源名称
	private Map<String, Map<String, String>> dataSourcesRule;
	private RequestInfo requestInfo;
	
	public Table getTable() {
		return table;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public Map<String, Map<String, String>> getDataSourcesRule() {
		return dataSourcesRule;
	}

	public RequestInfo getRequestInfo() {
		return requestInfo;
	}


	public SpiderConfiguration(Map<String, Object> params) {
		initConfiguration(params);
	}

	/**
	 * 加载配置项
	 * @param params
	 * @return
	 */
	public boolean initConfiguration(Map<String, Object> params) {
		requestInfo = new RequestInfo(null, params);
		
		try {
			String crawlerRule = requestInfo.getCrawlerRule();
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return true;
		
	}


	public void readFromXml(Document xmlDocument) {
		Map<String, Map<String, String>> dataSources = new HashMap<String, Map<String, String>>();

		// 初始化实体配置
		List<Table> modEntities = new ArrayList<Table>();
		/*List<Element> l = ConfigParseUtil.getChildNodes(e, "entity");
		for (Element et : l) {
			Entity entity = new Entity(et);
			modEntities.add(entity);
		}
		this.entities = Collections.unmodifiableList(modEntities);
		List<Element> dataSourceTags = ConfigParseUtil.getChildNodes(e, DbConst.DATA_SRC);
		if (!dataSourceTags.isEmpty()) {
			for (Element element : dataSourceTags) {
				Map<String, String> p = new HashMap<String, String>();
				HashMap<String, String> attrs = ConfigParseUtil.getAllAttributes(element);
				for (Map.Entry<String, String> entry : attrs.entrySet()) {
					p.put(entry.getKey(), entry.getValue());
					if ("name".equals(entry.getKey())) {
						this.dataSourceName = entry.getValue();
					}
				}
				dataSources.put(p.get("name"), p);
			}
		}*/
		this.dataSourcesRule = Collections.unmodifiableMap(dataSources);
	}
}