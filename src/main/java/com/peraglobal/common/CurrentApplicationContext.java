package com.peraglobal.common;

import org.springframework.context.ApplicationContext;

/**
 * <code>CurrentApplicationContext.java</code>
 * <p>
 * 功能:自动获取 spring bean，用于静态类或多线程中从spring容器获得 bean 实例
 * <p>
 * Copyright 安世亚太 2016 All right reserved.
 * 
 * @author yongqian.liu
 * @version 1.0
 * @see 2016-12-6 </br>
 * 		最后修改人 无
 */
public class CurrentApplicationContext {

	private static ApplicationContext applicationContext;

	public static void setApplicationContext(ApplicationContext context) {
		applicationContext = context;
	}

	/**
	 * 重写 getBean 方法
	 * 
	 * @param beanId
	 * @return
	 */
	public static Object getBean(String beanId) {
		return applicationContext.getBean(beanId);
	}
}
