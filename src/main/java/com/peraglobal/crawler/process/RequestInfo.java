package com.peraglobal.crawler.process;

import java.io.File;
import java.util.Map;

import org.springframework.http.HttpRequest;

import com.peraglobal.crawler.model.DbConst;

public class RequestInfo {

	public static final String LINE = "-";
	public static final String DIR = "C:/kmCrawler" + File.separator + "db-import-data";
	public static final String DBRULEDIR = "dbrule";
	public static final String TEMPDIR = "db-temp-file";
	public static final String LOGDIR = "db-breakpointlog";
	public static final String TASKMark = "task-mark";

	private final HttpRequest request;
	private final String taskId;// quartz 任务ID
	public String crawlerRule;// db采集规则
	
	private final String metaDateFilePath;
	private int fetchSize;
	private final int threadNum;
	
	private final String dataTempPath;// 临时文件路径

	public String getDataTempPath() {
		return dataTempPath;
	}

	public String getCrawlerRule() {
		return crawlerRule;
	}

	public int getThreadNum() {
		return threadNum;
	}

	public String getMetaDateFilePath() {
		return metaDateFilePath;
	}

	public HttpRequest getRequest() {
		return request;
	}

	public String getTaskId() {
		return taskId;
	}

	public int getFetchSize() {
		return fetchSize;
	}

	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	/**
	 * 请求参数封装
	 * 
	 * @param request
	 * @param requestParams
	 */
	public RequestInfo(HttpRequest request, Map<String, Object> params) {
		this.request = request;
		this.threadNum = 1;
		this.fetchSize = 500;
		this.taskId = (String) params.get(DbConst.CRAWLER_ID);
		this.dataTempPath = DIR + File.separator + TEMPDIR;
		this.metaDateFilePath = (String) params.get("metaDateFilePath");
		this.crawlerRule = (String) params.get(DbConst.CRAWLER_RULE);
	}

}