package com.peraglobal.spider.process;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.DatabaseMetaData;
import com.peraglobal.spider.model.DbConnection;
import com.peraglobal.spider.model.DbField;
import com.peraglobal.spider.model.DbTable;

/**
 * 2016-12-16
 * @author yongqian.liu
 * @see 数据库连接工具类
 */
public class MetaDataBuilder {
	
	/**
	 * 功能:验证数据库链接
	 * <p>作者 yongqian.liu 2016-12-15
	 */	
	public static boolean isConn(DbConnection dbConnection){
		try {
			Class.forName(dbConnection.getDriver());
			Connection conn = (Connection) DriverManager.getConnection(dbConnection.getUrl(),
					dbConnection.getUser(), dbConnection.getPassword());
			if (conn != null) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * 获得数据库所有表
	 * @param jdbc
	 * @param DBType 数据库类型
	 * @return
	 */
	public static List getTables(DbConnection dbConnection) {
		try {
			String DBType = "mysql";
			Class.forName(dbConnection.getDriver());
			Connection con = (Connection) DriverManager.getConnection(dbConnection.getUrl(),
					dbConnection.getUser(), dbConnection.getPassword());
			Statement statement = con.createStatement();
			ResultSet result=null;
			if ("oracle".equals(DBType)) {
				result = statement.executeQuery("select table_name from user_tables");
			}else if ("mysql".equals(DBType)) {
				DatabaseMetaData dmd=(DatabaseMetaData)con.getMetaData();
				result=dmd.getTables(null, null, "%", null);
			}else if("sqlserver".equals(DBType)){
				result = statement.executeQuery("Select Name FROM SysObjects Where XType='U' ORDER BY Name ");
			}
			List tables = new ArrayList();
			if("sqlserver".equals(DBType)){
				while (result.next()) {
					tables.add(result.getString("name"));
				} 
			}else {
				while (result.next()) {
					tables.add(result.getString("table_name"));
				} 
			}
			result.close();
			statement.close();
			con.close();
			return tables;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获得数据库表结构
	 * @param jdbc
	 * @return
	*/
	public static List getFields(DbConnection dbConnection) {
		try {
			Class.forName(dbConnection.getDriver());
			Connection con = (Connection) DriverManager.getConnection(dbConnection.getUrl(),
					dbConnection.getUser(), dbConnection.getPassword());
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery("select * from " + dbConnection.getTables().getName());
			ResultSetMetaData metaData = result.getMetaData();
			int count = metaData.getColumnCount();
			List fields = new ArrayList();
			for (int i = 0; i < count; i++) {
				fields.add(metaData.getColumnLabel(i + 1));
		    }
			result.close();
			statement.close();
			con.close();
			return fields;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	} 
	
	/**
	 * 获得数据库表结构
	 * @param jdbc
	 * @return
	*/
	public static List getRowDatas(DbConnection dbConnection) {
		try {
			Class.forName(dbConnection.getDriver());
			Connection con = (Connection) DriverManager.getConnection(dbConnection.getUrl(),
					dbConnection.getUser(), dbConnection.getPassword());
			Statement statement = con.createStatement();
			
			// 设置查询条件
			DbTable table = dbConnection.getTables();
			ResultSet result = statement.executeQuery("select * from " + table.getName());
			
			// 获得所有列
			ResultSetMetaData metaData = result.getMetaData();
			int count = metaData.getColumnCount();
			List fields = new ArrayList();
			for (int i = 0; i < count; i++) {
				fields.add(metaData.getColumnLabel(i + 1));
		    }
			
			// 获得所有列的值
			List obj = new ArrayList();
			while (result.next()) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				for (int i = 0; i < count; i++) {
					dataMap.put(fields.get(i).toString(), result.getObject(fields.get(i).toString()));
			    }
				obj.add(dataMap);
			}   
			result.close();
			statement.close();
			con.close();
			return obj;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	} 
	
	
	/**
	 * 获得数据库所有视图
	 * @param jdbc
	 * @return
	 */
	public static List<DbTable> getViews(DbConnection dbConnection) {
		try {
			Class.forName(dbConnection.getDriver());
			Connection con = (Connection) DriverManager.getConnection(dbConnection.getUrl(),
					dbConnection.getUser(), dbConnection.getPassword());
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery("select view_name from user_views");
			List<DbTable> tables = new ArrayList<DbTable>();
			DbTable jdbcTable = null;
			while (result.next()) {
				jdbcTable = new DbTable();
				jdbcTable.setName(result.getString("view_name"));
				tables.add(jdbcTable);
			}
			result.close();
			statement.close();
			con.close();
			return tables;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通过 JDBC 封装的类型 KEY 装换为类型值
	 * @param type key
	 * @return 类型值
	 */
	public static String getType(int type){
		String str = "";
		switch (type) {
		case Types.CHAR:
			str = "CHAR";
			break;

		case Types.VARCHAR:
			str = "VARCHAR";
			break;

		case Types.BIT:
			str = "BIT";
			break;

		case Types.TINYINT:
			str = "TINYINT";
			break;

		case Types.SMALLINT:
			str = "SMALLINT";
			break;

		case Types.INTEGER:
			str = "INTEGER";
			break;

		case Types.BIGINT:
			str = "BIGINT";
			break;

		case Types.FLOAT:
			str = "FLOAT";
			break;

		case Types.DOUBLE:
			str = "DOUBLE";
			break;

		case Types.NUMERIC:
			str = "NUMERIC";
			break;

		case Types.DECIMAL:
			str = "DECIMAL";
			break;

		case Types.LONGVARCHAR:
			str = "LONGVARCHAR";
			break;

		case Types.DATE:
			str = "DATE";
			break;

		case Types.TIME:
			str = "TIME";
			break;

		case Types.TIMESTAMP:
			str = "TIMESTAMP";
			break;

		case Types.BINARY:
			str = "BINARY";
			break;

		case Types.VARBINARY:
			str = "VARBINARY";
			break;

		case Types.LONGVARBINARY:
			str = "LONGVARBINARY";
			break;

		case Types.BLOB:
			str = "BLOB";
			break;

		case Types.CLOB:
			str = "CLOB";
			break;

		case Types.NCHAR:
			str = "NCHAR";
			break;

		case Types.NVARCHAR:
			str = "NVARCHAR";
			break;

		case Types.LONGNVARCHAR:
			str = "LONGNVARCHAR";
			break;

		case Types.NCLOB:
			str = "NCLOB";
			break;

		default:
			str = "12";
			break;
		}
		return str;
	}
	
	/**
	 * 通过 JDBC 封装的类型类型值装换为  KEY
	 * @param type 类型值
	 * @return 类型Key
	 */
	public static int getType(String type){
		
		if(type.equals("CHAR")){ 
			return Types.CHAR;
		}else if(type.equals("VARCHAR")){ 
			return Types.VARCHAR;
		}else if(type.equals("BIT")){ 
			return Types.BIT;
		}else if(type.equals("TINYINT")){ 
			return Types.TINYINT;
		}else if(type.equals("SMALLINT")){ 
			return Types.SMALLINT;
		}else if(type.equals("INTEGER")){ 
			return Types.INTEGER;
		}else if(type.equals("BIGINT")){ 
			return Types.BIGINT;
		}else if(type.equals("FLOAT")){ 
			return Types.FLOAT;
		}else if(type.equals("DOUBLE")){ 
			return Types.DOUBLE;
		}else if(type.equals("DOUBLE")){ 
			return Types.DOUBLE;
		}else if(type.equals("NUMERIC")){ 
			return Types.NUMERIC;
		}else if(type.equals("DECIMAL")){ 
			return Types.DECIMAL;
		}else if(type.equals("LONGVARCHAR")){ 
			return Types.LONGVARCHAR;
		}else if(type.equals("LONGVARCHAR")){ 
			return Types.LONGVARCHAR;
		}else if(type.equals("DATE")){ 
			return Types.DATE;
		}else if(type.equals("TIME")){ 
			return Types.TIME;
		}else if(type.equals("TIMESTAMP")){ 
			return Types.TIMESTAMP;
		}else if(type.equals("BINARY")){ 
			return Types.BINARY;
		}else if(type.equals("VARBINARY")){ 
			return Types.VARBINARY;
		}else if(type.equals("LONGVARBINARY")){ 
			return Types.LONGVARBINARY;
		}else if(type.equals("BLOB")){ 
			return Types.BLOB;
		}else if(type.equals("CLOB")){ 
			return Types.CLOB;
		}else if(type.equals("NCHAR")){ 
			return Types.NCHAR;
		}else if(type.equals("NVARCHAR")){ 
			return Types.NVARCHAR;
		}else if(type.equals("LONGNVARCHAR")){ 
			return Types.LONGNVARCHAR;
		}else if(type.equals("NCLOB")){ 
			return Types.NCLOB;
		}else if(type.equals("PATH")){
			return 3008;
		}else{ 
			return 12;
		}
	}
	
}
