package com.peraglobal.common;

import java.util.UUID;
import java.security.MessageDigest;

public class IDGenerate {
	/**
	 * 主键生成策略uuid
	 * @return
	 */
	public static String uuid(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 利用MD5将输入转换成32位字符串
	 * @param str 转换前的字符串
	 * @return newStr 转换为32位字符串
	 */
	public static String EncoderByMd5(String str){
		String newStr = "";
		try { 
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");  
            messageDigest.update(str.getBytes());  
            newStr = toHex(messageDigest.digest());  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
		return newStr;
	}

	/** 
     * 将16位byte[] 转换为32位String 
     *  
     * @param buffer 
     * @return 
     */
	private static String toHex(byte buffer[]) {  
        StringBuffer sb = new StringBuffer(buffer.length * 2);  
        for (int i = 0; i < buffer.length; i++) {  
            sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));  
            sb.append(Character.forDigit(buffer[i] & 15, 16));  
        }
        return sb.toString();  
    }
}
