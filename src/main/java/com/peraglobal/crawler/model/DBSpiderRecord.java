package com.peraglobal.crawler.model;

import static com.peraglobal.crawler.process.DataImportException.wrapAndThrow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.peraglobal.crawler.process.DataImportException;

public class DBSpiderRecord {
	
	private static final Logger log = LoggerFactory.getLogger(DBSpiderRecord.class);

	public static final String LOGFILETYPE = ".log";
	
	/**
	 * 爬虫log是否存在
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean existsRecord(String filePath) {
		File fie = new File(filePath);
		if (!fie.exists()) {
			return false;
		}
		return true;
	}

	public static boolean removeRecord(String filePath, String taskId) {
		File fie = new File(filePath);
		if (fie.exists()) {
			fie.delete();
			return true;
		}
		return false;
	}

	/**
	 * 读取log或者txt信息
	 * 
	 * @param filePath
	 * @return
	 */
	public static List<String> readRecord(String filePath) {
		List<String> list = null;
		try {
			list = new ArrayList<String>();
			FileInputStream is = new FileInputStream(filePath);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = "";
			try {
				while ((line = br.readLine()) != null) {
					if (line.equals(""))
						continue;
					else
						list.add(line);
				}
			} catch (IOException e) {
				wrapAndThrow(DataImportException.SEVERE, e, "读取一行数据时出错");
			}
		} catch (FileNotFoundException e) {
			wrapAndThrow(DataImportException.SEVERE, e, "文件读取路径错误");
		}
		return list;
	}

	/**
	 * 文件并写入内容
	 * 
	 * @param filePath
	 * @param fileName
	 * @param msg
	 */
	public static void writeRecord(String filePath, String msg) {
		try {
			File file = new File(filePath.substring(0, filePath.lastIndexOf(File.separator) + 1));
			if (file.mkdirs()) {
			}
			// 创建文件输出流
			FileOutputStream output = new FileOutputStream(new File(filePath));
			String outPutStr = new String(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + msg);
			output.write(outPutStr.getBytes());
			output.close();
			log.info(outPutStr);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}