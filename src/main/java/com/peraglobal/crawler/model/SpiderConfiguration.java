package com.peraglobal.crawler.model;

import static com.peraglobal.crawler.process.DataImportException.SEVERE;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.peraglobal.crawler.process.ConfigParseUtil;
import com.peraglobal.crawler.process.DataImportException;
import com.peraglobal.crawler.process.RequestInfo;

public class SpiderConfiguration extends Configuration {

	private static final Logger log = LoggerFactory.getLogger(SpiderConfiguration.class);

	private ReentrantLock importLock = new ReentrantLock();

	private String dataSourceName; // 数据源名称

	private List<Entity> entities; // 所有entities
	
	private RequestInfo requestInfo;

	private Map<String, Map<String, String>> dataSourcesRule;

	public String getDataSourceName() {
		return dataSourceName;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public RequestInfo getRequestInfo() {
		return requestInfo;
	}
	
	public Map<String, Map<String, String>> getDataSourcesRule() {
		return dataSourcesRule;
	}

	
	public SpiderConfiguration(Map<String, Object> params) {
		initConfiguration(params);
	}

	/**
	 * 加载配置项
	 * 
	 * @param params
	 * @return
	 */
	public boolean initConfiguration(Map<String, Object> params) {
		requestInfo = new RequestInfo(null, params);
		return this.initConfiguration(requestInfo);
	}



	/**
	 * 加载配置项
	 * @param params
	 * @param defaultParams
	 * @return
	 * @throws IOException
	 */
	public boolean initConfiguration(RequestInfo params) {
		if (importLock.tryLock()) {
			boolean success = false;
			try {
				// load rule xml
				String configStr = null;
				if (null == params.getDbRuleXml()) {
					// test
					String dataconfigFile = params.getConfigFile();
					ClassLoader classLoader = getClass().getClassLoader();
					InputStream in = classLoader.getResourceAsStream(dataconfigFile);
					configStr = IOUtils.toString(in);
				} else {
					configStr = params.getDbRuleXml();
				}
				InputSource is = null;
				if (null != configStr) {
					is = new InputSource(new StringReader(configStr));
				}
				if (is != null) {
					loadDataConfig(is);
					success = true;
				}
			} catch (IOException e) {
				throw new DataImportException(SEVERE, "load rule xml file problem: " + e.getMessage(), e);
			} finally {
				importLock.unlock();
			}
			return success;
		} else {
			return false;
		}
	}

	/**
	 * 加载规则xml
	 * @param configFile
	 * @return
	 */
	public void loadDataConfig(InputSource configFile) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			try {
				dbf.setNamespaceAware(true);
			} catch (UnsupportedOperationException e) {
				log.warn("XML parser doesn't support XInclude option");
			}
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document document;
			try {
				document = builder.parse(configFile);
			} finally {
				IOUtils.closeQuietly(configFile.getByteStream());
			}
			readFromXml(document);
			log.info("Data Configuration loaded successfully");
		} catch (Exception e) {
			throw new DataImportException(SEVERE, "Data Config problem: " + e.getMessage(), e);
		}
	}

	public void readFromXml(Document xmlDocument) {
		Map<String, Map<String, String>> dataSources = new HashMap<String, Map<String, String>>();
		NodeList dataConfigTags = xmlDocument.getElementsByTagName("dataConfig");
		if (dataConfigTags == null || dataConfigTags.getLength() == 0) {
			throw new DataImportException(SEVERE, "the root node '<dataConfig>' is missing");
		}

		Element e = (Element) dataConfigTags.item(0);
		// 初始化实体配置
		List<Entity> modEntities = new ArrayList<Entity>();
		List<Element> l = ConfigParseUtil.getChildNodes(e, "entity");
		for (Element et : l) {
			Entity entity = new Entity(et);
			modEntities.add(entity);
		}
		this.entities = Collections.unmodifiableList(modEntities);
		List<Element> dataSourceTags = ConfigParseUtil.getChildNodes(e, ConfigNameConstants.DATA_SRC);
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
		}
		this.dataSourcesRule = Collections.unmodifiableMap(dataSources);
	}

}