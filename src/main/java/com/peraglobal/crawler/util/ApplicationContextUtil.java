package com.peraglobal.crawler.util;

import org.springframework.context.ApplicationContext;

public class ApplicationContextUtil {
		private static ApplicationContext applicationContext;

	  public static void setApplicationContext(ApplicationContext context) {
		  applicationContext = context;
	  }
	  
	   public static Object getBean(String beanName) {
		   return applicationContext.getBean(beanName);
	  } 
}
