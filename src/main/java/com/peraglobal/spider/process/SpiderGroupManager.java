package com.peraglobal.spider.process;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * 爬虫线程管理
 * 
 * @author hadoop
 */
public class SpiderGroupManager extends SpiderManager {

	private static ConcurrentMap<String, ConcurrentMap<String, SdcSpider>> groupSpiderMap = new ConcurrentHashMap<String, ConcurrentMap<String, SdcSpider>>();

	/**
	 * 组爬虫注册
	 * 
	 * @param key
	 * @param spiderMap
	 */
	public static void registerGroup(String key, ConcurrentMap<String, SdcSpider> spiderMap) {
		groupSpiderMap.put(key, spiderMap);
		// 注册爬虫
		Set<String> keys = spiderMap.keySet();
		for (String k : keys) {
			register(k, spiderMap.get(k));
		}
	}

	/**
	 * 销毁
	 */
	public static void shutdownGroup() {
		shutdown();
	}

	/**
	 * 启动一组爬虫
	 * 
	 * @param key
	 */
	public static void startGroup(String key) {

		ConcurrentMap<String, SdcSpider> spiderMap = groupSpiderMap.get(key);
		Set<String> keys = spiderMap.keySet();
		for (String k : keys) {
			start(k);
		}

	}

	/**
	 * 暂停一组爬虫
	 * 
	 * @param key
	 */
	public static void puaseGroup(String key) {
		ConcurrentMap<String, SdcSpider> spiderMap = groupSpiderMap.get(key);
		Set<String> keys = spiderMap.keySet();
		for (String k : keys) {
			puase(k);
		}
	}

	/**
	 * 恢复一组爬虫
	 * 
	 * @param key
	 */
	public static void recoverGroup(String key) {
		ConcurrentMap<String, SdcSpider> spiderMap = groupSpiderMap.get(key);
		Set<String> keys = spiderMap.keySet();
		for (String k : keys) {
			recover(k);
		}
	}

	/**
	 * 停止一组爬虫
	 * 
	 * @param key
	 */
	public static void stopGroup(String key) {
		ConcurrentMap<String, SdcSpider> spiderMap = groupSpiderMap.get(key);
		Set<String> keys = spiderMap.keySet();
		for (String k : keys) {
			stop(k);
		}
	}

}