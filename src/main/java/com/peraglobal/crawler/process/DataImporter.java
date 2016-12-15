package com.peraglobal.crawler.process;

import com.peraglobal.crawler.model.SpiderConfiguration;

public class DataImporter {

	private SpiderConfiguration config;
	public MetaDataBuilder metaDataBuilder;

	public SpiderConfiguration getConfig() {
		return config;
	}

	public DataImporter(SpiderConfiguration conf) {
		this.config = conf;
	}

	/**
	 * 执行导入
	 * @param requestParams
	 */
	public void doImport() {
		this.doImport(config.getRequestInfo());
	}

	/**
	 * 执行导入
	 * @param requestParams
	 */
	private void doImport(RequestInfo params) {
		try {
			new MetaDataBuilder(params, this);
		} catch (Exception e) {
			// Exception  Import
		}
	}

}
