package com.peraglobal.spider.process;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.peraglobal.common.CurrentApplicationContext;
import com.peraglobal.common.IDGenerate;
import com.peraglobal.db.model.Crawler;
import com.peraglobal.db.model.Metadata;
import com.peraglobal.db.service.HistoryService;
import com.peraglobal.db.service.MetadataService;
import com.peraglobal.spider.model.DbConnection;

public class DbSpider extends SdcSpider {

	protected Crawler crawler;
	protected DbConnection dbConnection;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private MetadataService metadataService;

	public DbSpider() {
		this.historyService = (HistoryService) CurrentApplicationContext.getBean("historyService");
		this.metadataService = (MetadataService) CurrentApplicationContext.getBean("metadataService");
	}

	/**
	 * 构建实例方法
	 * 
	 * @return
	 */
	public static DbSpider create() {
		return new DbSpider();
	}

	/**
	 * 构建 DB 采集实例方法
	 * 
	 * @return
	 */
	public DbSpider setCrawler(Crawler crawler) {
		this.crawler = crawler;
		return this;
	}

	public DbSpider setDbConnection(DbConnection dbConnection) {
		if (this.crawler != null) {
			this.dbConnection = dbConnection;
		}
		return this;
	}

	/**
	 * 注册到线程池中
	 */
	public void register() {
		SpiderManager.register(this.crawler.getCrawlerId(), this);
	}

	/**
	 * 主方法，业务实现
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void execute() {

		while (true) {
			if (spiderMonitor()) {
				historyService.stopHistory(crawler.getCrawlerId());
				break;
			}
			try {
				List data = MetaDataBuilder.getRowDatas(this.dbConnection);
				if (data != null && data.size() > 0) {
					for (int i = 0; i < data.size(); i++) {

						// 采集到数据转换为 Json 格式
						String jsonData = JSONObject.toJSONString(data.get(i));

						// 生成 MD5 码
						String md5 = IDGenerate.EncoderByMd5(jsonData);

						// 持久化元数据
						Metadata metadata = new Metadata();
						metadata.setCrawlerId(crawler.getCrawlerId());
						metadata.setMd(md5);
						metadata.setMetadata(jsonData);
						metadataService.createMetadata(metadata);

						// 监控日志，后续完善
						historyService.updatePageCount(crawler.getCrawlerId());
						Thread.sleep(500); // 休眠 0.5 秒

						if (spiderMonitor() && data.size() == i) {
							historyService.stopHistory(crawler.getCrawlerId());
							break;
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				historyService.updateExcetion(crawler.getCrawlerId(), e.getMessage());
			}
		}
	}
}
