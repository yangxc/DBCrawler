package com.peraglobal.spider.model;

import java.util.List;

public class DbTable {

	private String name;
	private String query;
	private String pk;
	private List<DbField> fields;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public List<DbField> getFields() {
		return fields;
	}

	public void setFields(List<DbField> fields) {
		this.fields = fields;
	}

}
