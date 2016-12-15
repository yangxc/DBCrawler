package com.peraglobal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.peraglobal.common.ApplicationContextUtil;

/**
 *  <code>Application.java</code>
 *  <p>功能:启动类
 *  
 *  <p>Copyright 安世亚太 2016 All right reserved.
 *  @author yongqian.liu	
 *  @version 1.0
 *  2016-12-8
 *  </br>最后修改人 无
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args){
		ApplicationContext applicationContext = SpringApplication.run(Application.class,args);
		ApplicationContextUtil.setApplicationContext(applicationContext);
    }

}

