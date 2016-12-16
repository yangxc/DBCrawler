package com.peraglobal.common;

import org.springframework.context.ApplicationContext;

public class CurrentApplicationContext {
	
	private static ApplicationContext applicationContext;

	public static void setApplicationContext(ApplicationContext context) {
		applicationContext = context;
	}

	public static Object getBean(String beanId) {
		return applicationContext.getBean(beanId);
	}
}
