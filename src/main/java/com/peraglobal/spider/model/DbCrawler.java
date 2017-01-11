package com.peraglobal.spider.model;

import com.peraglobal.db.model.Crawler;

public class DbCrawler extends Crawler {

	private static final long serialVersionUID = -6746631829727013621L;

	private DbConnection dbConnection;

	public DbConnection getDbConnection() {
		return dbConnection;
	}

	public void setDbConnection(DbConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

}
