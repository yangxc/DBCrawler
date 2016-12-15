package com.peraglobal.crawler.process;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.peraglobal.crawler.model.DbConst;
import com.peraglobal.crawler.model.SpiderConfiguration;
import com.peraglobal.crawler.model.Table;

/**
 * 实体处理器
 * 
 * @author hadoop
 */
public class SqlEntityProcessor extends EntityProcessor {

	protected boolean isFirstInit = true;
	
	private String taskId;
	private int markSize;// 断点标记
	private int fetchSize;// 默认查询记录数
	protected String entityName;
	protected Context context;
	protected DataSource dataSource;
	private Map<String, Map<String, String>> dataSourceRule;
	protected CacheSupport cacheSupport = null;
	private String dataSourceName;
	protected Iterator<Map<String, Object>> rowIterator;
	protected String query;
	protected Table table;
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public Table getTable() {
		return table;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getFetchSize() {
		return fetchSize;
	}

	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	public int getMarkSize() {
		return markSize;
	}

	public void setMarkSize(int markSize) {
		this.markSize = markSize;
	}
	
	public SqlEntityProcessor(Table table, SpiderConfiguration config) {
		// 实体属性
		this.table = table;
		this.query = table.getQuery();
		this.entityName = table.getName();
		
		// 配置信息
		this.taskId = config.getRequestInfo().getTaskId();
		this.dataSourceName = config.getDataSourceName();
		this.dataSourceRule = config.getDataSourcesRule();
		this.fetchSize = config.getRequestInfo().getFetchSize();
		
		// 数据库配置
		this.dataSource = getDataSourceInstance(dataSourceName);
	}

	/**
	 * 实例化dataSource
	 * @return
	 */
	public DataSource getDataSourceInstance(String dataSourceName) {
		DataSource dataSrc = new JdbcDataSource();
		try {
			Properties initProps = new Properties();
			// 从规则中获取datasource配置
			Map<String, String> dsMap = dataSourceRule.get(dataSourceName);
			for (String key : dsMap.keySet()) {
				initProps.put(key, dsMap.get(key));
			}
			// 初始化dataSource实例
			dataSrc.init(initProps);
		} catch (Exception e) {
			// Failed to initialize DataSource
		}
		return dataSrc;
	}

	@Override
	public void init(Context context) {
		this.context = context;

		if (isFirstInit) {
			firstInit(context);
		}
		if (cacheSupport != null) {
			cacheSupport.initNew();
		}
	}

	/**
	 * first time init call. do one-time operations here it's necessary to call
	 * it from the overridden method,
	 */
	protected void firstInit(Context context) {
		initCache(context);
		isFirstInit = false;
	}

	protected void initCache(Context context) {
		String cacheImplName = CacheSupport.CACHE_IMPL;
		if (cacheImplName != null) {
			cacheSupport = new CacheSupport(cacheImplName);
		}
	}

	protected void initQuery(String q) {
		try {
			JdbcDataSource jdbcDataSource = null;
			if (dataSource instanceof JdbcDataSource) {
				jdbcDataSource = (JdbcDataSource) dataSource;
				this.query = q;
				rowIterator = jdbcDataSource.getData(query, markSize, fetchSize);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> nextRow() {
		if (rowIterator == null) {
			String q = getQuery();
			initQuery(q);
		}
		return getNext();
	}

	public String getQuery() {
		String queryString = context.getEntityAttribute(DbConst.QUERY);
		if (Context.FULL_DUMP.equals(context.currentProcess())) {
			return queryString + " order by " + table.getPk() + " asc";
		}
		return null;
	}

	protected Map<String, Object> getNext() {
		if (rowIterator == null)
			return null;
		if (rowIterator.hasNext())
			return rowIterator.next();
		// 以下是启用缓存机制，由于缓存会导致数据采集不完整，暂时不用缓存机制
		if (cacheSupport == null) {
			try {
				if (rowIterator == null)
					return null;
				if (rowIterator.hasNext())
					return rowIterator.next();
				query = null;
				rowIterator = null;
				return null;
			} catch (Exception e) {
				query = null;
				rowIterator = null;
				return null;
			}
		} else {
			return cacheSupport.getCacheData(context, query, rowIterator);
		}
	}

	@Override
	public void destroy() {
		query = null;
	}

}
