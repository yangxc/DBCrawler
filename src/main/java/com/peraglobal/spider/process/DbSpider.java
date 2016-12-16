package com.peraglobal.spider.process;

import com.peraglobal.db.model.Crawler;

public class DbSpider extends SdcSpider {
	
    protected Crawler crawler;
	
	public DbSpider(){
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
			if(spiderMonitor()) break;
			try {
				System.out.println("=================");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
