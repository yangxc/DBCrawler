package com.peraglobal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;

import com.peraglobal.common.CurrentApplicationContext;

/**
 * <code>Application.java</code>
 * <p>
 * 功能:启动类
 * 
 * <p>
 * Copyright 安世亚太 2016 All right reserved.
 * 
 * @author yongqian.liu
 * @version 1.0 2016-12-8 </br>
 * 			最后修改人 无
 */
@EnableEurekaClient
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// 设置启动类
		ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		// 设置 spring 上下文到自定义类中
		CurrentApplicationContext.setApplicationContext(applicationContext);
	}

}
