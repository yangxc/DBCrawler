package com.peraglobal.spider.process;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

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
	
	public DbSpider(){
		this.historyService = (HistoryService)CurrentApplicationContext.getBean("historyService");
		this.metadataService = (MetadataService)CurrentApplicationContext.getBean("metadataService");
	}
	
	/**
	 * 构建实例方法
	 * @return
	 */
	public static DbSpider create(){
		return new DbSpider();
	}
	
	/**
	 * 构建 DB 采集实例方法
	 * @return
	 */
	public DbSpider setCrawler(Crawler crawler) {
		this.crawler = crawler;
		return this;
	}
	
	public DbSpider setDbConnection() {
		if (this.crawler != null) {
			JSONObject jsonObj = new JSONObject(crawler.getExpress());  
			this.dbConnection = (DbConnection)JSONObject.wrap(jsonObj);
		}
		return this;
	}
	
	/**
	 * 注册到线程池中
	 */
	public void register(){
		SpiderManager.register(this.crawler.getCrawlerId(), this);
	}
	
	/**
	 * 主方法，业务实现
	 */
	@Override
	public void execute() {
		
		while (true) {
			if (spiderMonitor()) {
				// 监控日志，后续完善
				break;
			}
			try {
				Map<String, Object> data = MetaDataBuilder.getRowDatas(this.dbConnection);
				if (data != null && data.size() > 0) {
					for (int i = 0; i < data.size(); i++) {
						
						// 采集到数据转换为 Json 格式
						JSONObject jsonObj = new JSONObject(data.get(i));  
						String jsonData = jsonObj.toString();
						
						// 生成 MD5 码
						String md5 = IDGenerate.EncoderByMd5(jsonData);
						
						// 判断数据是否存在
						Metadata metadata = metadataService.getMetadataByMd(md5);
						if (metadata == null) {
							
							// 持久化元数据
							metadata = new Metadata();
							metadata.setCrawlerId(crawler.getCrawlerId());
							metadata.setMd(md5);
							metadata.setMetadta(jsonData);
							metadataService.createMetadata(metadata);
							
							// 监控日志，后续完善
							historyService.updatePageCount(crawler.getCrawlerId());
						}
					}
				}
				Thread.sleep(500); // 休眠 0.5 秒
			} catch (Exception e) {
				e.printStackTrace();
				historyService.updateExcetion(crawler.getCrawlerId(), e.getMessage());
			}
		}
	}
}
