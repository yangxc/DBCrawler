package com.peraglobal.crawler.process;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JdbcDataSource extends DataSource<Iterator<Map<String, Object>>> {

	public static Map<String, Integer> fieldNameVsType = new HashMap<String, Integer>();
	private static final long CONN_TIME_OUT = TimeUnit.NANOSECONDS.convert(10, TimeUnit.SECONDS);
	
	protected Callable<Connection> factory;
	private long connLastUsed = 0;
	private boolean isClosed = false;
	private Connection conn;
	private ResultSetIterator r;
	
	@Override
	public void init(Properties initProps) {
		factory = createConnectionFactory(initProps);
	}

	/**
	 * 创建数据库链接
	 * 
	 * @param initProps
	 * @return
	 */
	protected Callable<Connection> createConnectionFactory(final Properties initProps) {
		final String jndiName = initProps.getProperty("jndiName");
		final String url = initProps.getProperty("url");
		final String driver = initProps.getProperty("driver");

		if (url == null && jndiName == null) {
			// JDBC URL or JNDI name has to be specified
		}
		return factory = new Callable<Connection>() {
			@Override
			public Connection call() throws Exception {
				Connection c = null;
				if (jndiName != null) {
					c = getFromJndi(initProps, jndiName);
				} else if (url != null) {
					try {
						Class.forName(driver);
						c = DriverManager.getConnection(url, initProps);
					} catch (SQLException e) {
						// Exception initializing  connection
					}
				}
				if (c != null) {
					try {
						initializeConnection(c, initProps);
					} catch (SQLException e) {
						try {
							c.close();
						} catch (SQLException e2) {
							// Exception closing connection during cleanup
						}
						// Exception initializing SQL connection
					}
				}
				return c;
			}

			private void initializeConnection(Connection c, final Properties initProps) throws SQLException {
				if (Boolean.parseBoolean(initProps.getProperty("readOnly"))) {
					c.setReadOnly(true);
					c.setAutoCommit(true);
					c.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
					c.setHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT);
				}
				if (!Boolean.parseBoolean(initProps.getProperty("autoCommit"))) {
					c.setAutoCommit(false);
				}
				String transactionIsolation = initProps.getProperty("transactionIsolation");
				if ("TRANSACTION_READ_UNCOMMITTED".equals(transactionIsolation)) {
					c.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
				} else if ("TRANSACTION_READ_COMMITTED".equals(transactionIsolation)) {
					c.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
				} else if ("TRANSACTION_REPEATABLE_READ".equals(transactionIsolation)) {
					c.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
				} else if ("TRANSACTION_SERIALIZABLE".equals(transactionIsolation)) {
					c.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				} else if ("TRANSACTION_NONE".equals(transactionIsolation)) {
					c.setTransactionIsolation(Connection.TRANSACTION_NONE);
				}
				String holdability = initProps.getProperty("holdability");
				if ("CLOSE_CURSORS_AT_COMMIT".equals(holdability)) {
					c.setHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT);
				} else if ("HOLD_CURSORS_OVER_COMMIT".equals(holdability)) {
					c.setHoldability(ResultSet.HOLD_CURSORS_OVER_COMMIT);
				}
			}

			/**
			 * JNDI方式
			 * @param initProps
			 * @param jndiName
			 */
			private Connection getFromJndi(final Properties initProps, final String jndiName)
					throws NamingException, SQLException {
				Connection c = null;
				InitialContext ctx = new InitialContext();
				Object jndival = ctx.lookup(jndiName);
				if (jndival instanceof javax.sql.DataSource) {
					javax.sql.DataSource dataSource = (javax.sql.DataSource) jndival;
					String user = (String) initProps.get("user");
					String pass = (String) initProps.get("password");
					if (user == null || user.trim().equals("")) {
						c = dataSource.getConnection();
					} else {
						c = dataSource.getConnection(user, pass);
					}
				} else {
					// the jndi name : '" + jndiName + "' is not a valid javax.sql.DataSource
				}
				return c;
			}
		};
	}

	@Override
	public Iterator<Map<String, Object>> getData(String query, int markSize, int fetchSize) {
		r = new ResultSetIterator(query, markSize, fetchSize);
		return r.getIterator();
	}

	public void closeAll() {
		if (null != r) {
			r.closeAll();
		}
	}

	private List<String> readFieldNames(ResultSetMetaData metaData) throws SQLException {
		List<String> colNames = new ArrayList<String>();
		int count = metaData.getColumnCount();
		for (int i = 0; i < count; i++) {
			colNames.add(metaData.getColumnLabel(i + 1));
		}
		return colNames;
	}

	private Map<String, Integer> readFieldNamesVsType(ResultSetMetaData metaData) throws SQLException {
		Map<String, Integer> colNamesVsType = new HashMap<String, Integer>();
		int count = metaData.getColumnCount();
		for (int i = 0; i < count; i++) {
			colNamesVsType.put(metaData.getColumnLabel(i + 1), metaData.getColumnType(i + 1));
		}
		return colNamesVsType;
	}

	private class ResultSetIterator {
		ResultSet resultSet;
		Statement stmt = null;
		List<String> colNames;
		Iterator<Map<String, Object>> rSetIterator;

		public void closeAll() {
			this.close();
			JdbcDataSource.this.close();
		}

		public ResultSetIterator(String query, int markSize, int fetchSize) {
			try {
				Connection c = getConnection();
				stmt = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(fetchSize);
				if (stmt.execute(query)) {
					resultSet = stmt.getResultSet();
					if (markSize > 0) {
						resultSet.absolute(markSize);
					} else {
						resultSet.beforeFirst();
					}
				}
				colNames = readFieldNames(resultSet.getMetaData());
				fieldNameVsType = readFieldNamesVsType(resultSet.getMetaData());
			} catch (Exception e) {
				// Unable to execute query
			}
			if (resultSet == null) {
				rSetIterator = new ArrayList<Map<String, Object>>().iterator();
				return;
			}
			// 自定义Iterator实现
			rSetIterator = new Iterator<Map<String, Object>>() {
				@Override
				public boolean hasNext() {
					return hasnext();
				}

				@Override
				public Map<String, Object> next() {
					return getARow();
				}

				@Override
				public void remove() {
				}
			};
		}

		private Iterator<Map<String, Object>> getIterator() {
			return rSetIterator;
		}

		/**
		 * 获取一行数据
		 * @return
		 */
		private Map<String, Object> getARow() {
			if (resultSet == null)
				return null;
			Map<String, Object> result = new HashMap<String, Object>();
			for (String colName : colNames) {
				try {
					Integer type = fieldNameVsType.get(colName);
					if (type == null)
						type = Types.VARCHAR;
					switch (type) {
					case Types.INTEGER:
						result.put(colName, resultSet.getInt(colName));
						break;
					case Types.FLOAT:
						result.put(colName, resultSet.getFloat(colName));
						break;
					case Types.BIGINT:
						result.put(colName, resultSet.getLong(colName));
						break;
					case Types.DOUBLE:
						result.put(colName, resultSet.getDouble(colName));
						break;
					case Types.DATE:
						result.put(colName, resultSet.getTimestamp(colName));
						break;
					case Types.BOOLEAN:
						result.put(colName, resultSet.getBoolean(colName));
						break;
					case Types.BLOB:
						result.put(colName, null != resultSet.getBlob(colName)
								? resultSet.getBlob(colName).getBinaryStream() : null);
						break;
					case Types.CLOB:
						result.put(colName, null != resultSet.getClob(colName)
								? resultSet.getClob(colName).getAsciiStream() : null);
						break;
					default:
						result.put(colName, resultSet.getString(colName));
						break;
					}
				} catch (SQLException e) {
					// Error reading data from database
				}
			}
			return result;
		}

		private boolean hasnext() {
			if (resultSet == null)
				return false;
			try {
				if (resultSet.next()) {
					return true;
				} else {
					close();
					return false;
				}
			} catch (SQLException e) {
				close();
				return false;
			}
		}

		private void close() {
			try {
				if (resultSet != null)
					resultSet.close();
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				resultSet = null;
				stmt = null;
			}
		}
	}

	/**
	 * 获取数据库connection 超时重新链接 10s
	 */
	private Connection getConnection() throws Exception {
		long currTime = System.nanoTime();
		if (currTime - connLastUsed > CONN_TIME_OUT) {
			synchronized (this) {
				Connection tmpConn = factory.call();
				closeConnection();
				connLastUsed = System.nanoTime();
				return conn = tmpConn;
			}
		} else {
			connLastUsed = currTime;
			return conn;
		}
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			if (!isClosed) {
				close();
			}
		} finally {
			super.finalize();
		}
	}

	@Override
	public void close() {
		try {
			closeConnection();
		} finally {
			isClosed = true;
		}
	}

	private void closeConnection() {
		try {
			if (conn != null) {
				try {
					conn.commit();
				} catch (Exception ex) {
				}
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
