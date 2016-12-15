package com.peraglobal.common;

import java.util.UUID;

public class IDGenerate {
	/**
	 * 主键生成策略uuid
	 * @return
	 */
	public static String uuid(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
