package com.peraglobal.spider.process;

import com.peraglobal.spider.model.DbConst;

/**
 * 爬虫任务基类
 * @author hadoop
 */
public abstract class SdcSpider implements Runnable {
	
	private String spiderState;
	private String spiderName;

	public String getSpiderState() {
		return spiderState;
	}

	public void setSpiderState(String spiderState) {
		this.spiderState = spiderState;
	}

	public String getSpiderName() {
		return spiderName;
	}

	public void setSpiderName(String spiderName) {
		this.spiderName = spiderName;
	}

	/**
	 * 线程业务处理
	 */
	public abstract void execute();
	
	@Override
	public void run() {
		this.execute();
	}

	/**
	 * 爬虫监视器
	 */
	public boolean spiderMonitor() {
		if (DbConst.PUASE.equals(spiderState)) {
			// condition.await();//等待
			return true;
		}
		if (DbConst.STOP.equals(spiderState)) {
			return true;// 退出
		}
		return false;
	}

}