package com.peraglobal.model;

public class Crawler {

	private String crawlerId;
	
	private String crawlerName;
	
	private String groupId;
	
	public Crawler() {
		
	}
	
	public Crawler(String crawlerId, String crawlerName, String groupId) {
		this.crawlerId = crawlerId;
		this.crawlerName = crawlerName;
		this.groupId = groupId;
	}
	
	
	public String getCrawlerId() {
		return crawlerId;
	}
	public void setCrawlerId(String crawlerId) {
		this.crawlerId = crawlerId;
	}
	public String getCrawlerName() {
		return crawlerName;
	}
	public void setCrawlerName(String crawlerName) {
		this.crawlerName = crawlerName;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
}
