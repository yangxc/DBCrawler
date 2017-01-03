package com.peraglobal.db.model;

import java.io.Serializable;

public class Rule implements Serializable {
	
	private static final long serialVersionUID = -8742056211360872492L;

	/**
	 * @category ID
	 */
	private String ruleId;
	
	/**
	 * @category 爬虫 ID
	 */
	private String crawlerId;
	
	/**
	 * 表达式：对应 blob 字段
	 */
	private String express;

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getCrawlerId() {
		return crawlerId;
	}

	public void setCrawlerId(String crawlerId) {
		this.crawlerId = crawlerId;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}
}
