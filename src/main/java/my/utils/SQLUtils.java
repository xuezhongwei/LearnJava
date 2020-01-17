package my.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.CustomerModel;
/**
 * 与数据库操作相关的工具类
 *
 * 数据库资源使用完了，一定要关闭
 * 关闭的顺序是:ResultSet,Statement,Connection
 * 如果关闭Statement之后，ResultSet会自动关闭；关闭Connection之后，Statement不会自动关闭，但是Connection关闭之后，Statement将无法使用
 */
public class SQLUtils {
	private static Logger logger = LoggerFactory.getLogger(SQLUtils.class);
	
	private static final String HOME_USER = "29507";
	
	public static void main(String[] args) throws SQLException {
		Connection conn = getConnection();
		Statement state = conn.createStatement();

		DatabaseMetaData dbmd = conn.getMetaData();
		String tableName = "customer";
		//printTableToModel(dbmd, tableName);
		//printMapperResultMap(dbmd);
		//printMapperInsertSql(dbmd);
		//printAllTableInfo(dbmd);
		
		  String sql = "select * from customer"; ResultSet rs =
		  state.executeQuery(sql); 
		  while(rs.next()) { 
			  CustomerModel customer =  betterConvertToBean(rs, CustomerModel.class); 
			  System.out.println(customer.toString());
		  }
		 
		//printResultSetColumnName(rs);
		state.close();
		conn.close();
	}
	/**
	 * 获得数据库连接 
	 */
	public static Connection getConnection() {
		Properties props = PropertiesUtils.loadProperties();
		String userName = SystemUtils.getUserName();
		
		String driver = "";
		String dataBase = "";
		String user = "";
		String password = "";
		String url = "";
		// 如果在家就取这个配置
		if (HOME_USER.equals(userName)) {
			driver = props.getProperty("home.jdbc.driver");
			dataBase = props.getProperty("home.jdbc.database");
			user = props.getProperty("home.jdbc.user");
			password = props.getProperty("home.jdbc.password");
			url = props.getProperty("home.jdbc.url");
		} else {
			driver = props.getProperty("office.jdbc.driver");
			dataBase = props.getProperty("office.jdbc.database");
			user = props.getProperty("office.jdbc.user");
			password = props.getProperty("office.jdbc.password");
			url = props.getProperty("office.jdbc.url");
		}
			
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url + dataBase, user, password);
			if (null != conn) {
				logger.info("数据库连接成功！");
			}
			return conn;
		} catch (Exception e) {
			logger.error("数据库连接失败！");
			throw new RuntimeException("数据库连接失败！", e);
		}
	}
	/**
	 * 生成与数据库表对应的model
	 * 
	 * 获得某个表的所有字段信息
	 * 例如：字段名，字段类型，注释
	 * @param databaseMetaData
	 */
	public static void printTableToModel(DatabaseMetaData databaseMetaData, String tableName) {
		ResultSet rs;
		try {
			// getColums最一般的用法
			// getColums(null, null, tableName, "%") 获得某个表的所有字段信息
			rs = databaseMetaData.getColumns(null, null, tableName, "%");
			while(rs.next()){
				// 更多信息请查阅文档
	            // 列名
	            String columnName = rs.getString("COLUMN_NAME");
	            // 类型名称
	            String typeName = rs.getString("TYPE_NAME");
	            // 注释
	            String remarks = rs.getString("REMARKS");
	            
	            //System.out.println(columnName + " " + typeName + " " + remarks);
	            
	            // 拼接model字符串
	            String comments = "/**\n" + " * " + remarks + "\n */\n"; 
	            String var = "private ";
	            // 这里只列了几个常用类型，可根据实际情况调整
	            if (typeName.equalsIgnoreCase("bigint")) {
	            	var += "Long ";
	            } else if (typeName.equalsIgnoreCase("DECIMAL")) {
	            	var += "BigDecimal ";
	            } else if (typeName.equalsIgnoreCase("int")) {
	            	var += "Integer";
	            } else {
	            	var += "String ";
	            }
	            
	            String[] words = columnName.split("_");
	            var += words[0];
	            for (int i = 1; i < words.length; i++) {
	            	String temp = words[i].substring(0, 1).toUpperCase() + words[i].substring(1);
	            	var += temp;
	            }
	            var += ";";
	            
	            System.out.println(comments + var);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 生成与数据库表对应的mybatis mapper <resultMap/>字符串
	 * @param dbmd
	 */
	public static void printMapperResultMap(DatabaseMetaData dbmd, String tableName) {
		ResultSet rs;
		try {
			rs = dbmd.getColumns(null, "%", tableName, "%");
			while(rs.next()){
	            //列名
	            String columnName = rs.getString("COLUMN_NAME");
	            
	            String var = "";
	            String[] words = columnName.split("_");
	            var += words[0];
	            for (int i = 1; i < words.length; i++) {
	            	String temp = words[i].substring(0, 1).toUpperCase() + words[i].substring(1);
	            	var += temp;
	            }
	            var += "";
	            
				String result = "<result column=\"" + columnName + "\" property=\"" + var + "\"/>";
	            
	            System.out.println(result);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 生成与数据库表对应的mybatis mapper中 insert字符串
	 * @param dbmd
	 */
	public static void printMapperInsertSql(DatabaseMetaData dbmd) {
		ResultSet rs;
		StringBuilder columns = new StringBuilder();
		StringBuilder values = new StringBuilder();
		try {
			String tableName = "eb_ent_price_template_temp";
			rs = dbmd.getColumns(null, "%", tableName, "%");
			while(rs.next()){
	            //列名
	            String columnName = rs.getString("COLUMN_NAME");
	            
	            String var = "";
	            String[] words = columnName.split("_");
	            var += words[0];
	            for (int i = 1; i < words.length; i++) {
	            	String temp = words[i].substring(0, 1).toUpperCase() + words[i].substring(1);
	            	var += temp;
	            }
	            columns.append(columnName).append(",");
	            values.append("#{").append(var).append("},");
	        }
			if (columns.length() != 0 ) {
				System.out.println(columns.substring(0, columns.lastIndexOf(",")));
			}
			if (values.length() != 0 ) {
				System.out.println(values.substring(0, values.lastIndexOf(",")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获得数据库的所有表信息
	 * @param dbmd
	 */
	public static void printAllTableInfo(DatabaseMetaData dbmd) {
		try {
			// getTables最一般的用法
			// getTables(null, null, "%", mull) 获得所有表的信息
			ResultSet rs = dbmd.getTables(null, null, "%", null);
			while(rs.next()) {
				// 表名
				String tableName = rs.getString("table_name");
				// 表注释
				String remarks = rs.getString("remarks");
				
				System.out.println(tableName + "  " + remarks);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获得ResultSet包含的所有列名
	 * 可用于获得相应的getter/setter方法
	 */
	public static void printResultSetColumnName(ResultSet rs) {
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			// 注意ResultSet中列的编号是从1开始的
			for (int i = 1; i <= columnCount; i++) {
				String columnName = rsmd.getColumnName(i);
				System.out.println(columnName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 将ResultSet转为bean，基础版
	 * 适用于无继承的bean，如果有继承，则父类中的属性是不会被设值的
	 * 
	 * 思路：根据model里包含的属性名，去ResultSet中找到同名的列，然后调用setter方法设值
	 */
	public static <T> T baseConvertToBean(ResultSet rs, Class<T> clazz) {
		T bean = null;
		try {
			bean = clazz.newInstance();
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int countColumns = rsmd.getColumnCount();
			
			// 这里要注意一下，getDeclaredFields与getFields是用区别的
			// getDeclaredFields获得的是类中直接声明的属性，不包括继承而来的属性
			Field[] fields = clazz.getDeclaredFields();
			if (fields.length != 0) {
				for (Field field : fields) {
					String fieldName = field.getName();
					
					for (int i = 1; i <= countColumns; i++) {
						String columnName = "";
						if (rsmd.getColumnLabel(i) != null && !"".equals(rsmd.getColumnLabel(i))) {
							columnName = rsmd.getColumnLabel(i);
						} else {
							columnName = rsmd.getColumnName(i);
						}
						
						if (fieldName.equals(columnName)) {
							Class<?> fieldType = field.getType();
							
							String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
							
							// 一个类在vm中，只存在一个Class实例，所以可用==
							// bean中最常用四种类型：String，BigDecimal，Long，Integer
							if (fieldType == String.class) {
								Method setter = clazz.getMethod(setMethodName, fieldType);
								setter.invoke(bean, rs.getString(i));
							} else if (fieldType == BigDecimal.class) {
								Method setter = clazz.getMethod(setMethodName, fieldType);
								setter.invoke(bean, rs.getBigDecimal(i));
							} else if (fieldType == Long.class || fieldType == long.class) {
								Method setter = clazz.getMethod(setMethodName, fieldType);
								setter.invoke(bean, rs.getLong(i));
							} else if (fieldType == Integer.class || fieldType == int.class) {
								Method setter = clazz.getMethod(setMethodName, fieldType);
								setter.invoke(bean, rs.getInt(i));
							}
						}
					}
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return bean;
	}
	
	/**
	 * 将ResultSet转为bean，加強版
	 * 无论bean有无继承都适用
	 * 
	 * 思路：根据model里包含的属性名，去ResultSet中找到同名的列，然后调用setter方法设值
	 */
	public static <T> T betterConvertToBean(ResultSet rs, Class<T> clazz) {
		T bean = null;
		try {
			bean = clazz.newInstance();
			
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int countColumns = rsmd.getColumnCount();
			
			Class tempLoop = clazz;
			while (tempLoop != Object.class) {
			// 这里要注意一下，getDeclaredFields与getFields是用区别的
			// getDeclaredFields获得的是类中直接声明的属性，不包括继承而来的属性
				Field[] fields = tempLoop.getDeclaredFields();
				
				if (fields.length != 0) {
					for (Field field : fields) {
						String fieldName = field.getName();
						
						for (int i = 1; i <= countColumns; i++) {
							String columnName = "";
							if (rsmd.getColumnLabel(i) != null && !"".equals(rsmd.getColumnLabel(i))) {
								columnName = rsmd.getColumnLabel(i);
							} else {
								columnName = rsmd.getColumnName(i);
							}
							
							if (fieldName.equals(columnName)) {
								Class<?> fieldType = field.getType();
								
								String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
								
								// 一个类在vm中，只存在一个Class实例，所以可用==
								// bean中最常用四种类型：String，BigDecimal，Long，Integer
								if (fieldType == String.class) {
									Method setter = tempLoop.getMethod(setMethodName, fieldType);
									setter.invoke(bean, rs.getString(i));
								} else if (fieldType == BigDecimal.class) {
									Method setter = tempLoop.getMethod(setMethodName, fieldType);
									setter.invoke(bean, rs.getBigDecimal(i));
								} else if (fieldType == Long.class || fieldType == long.class) {
									Method setter = tempLoop.getMethod(setMethodName, fieldType);
									setter.invoke(bean, rs.getLong(i));
								} else if (fieldType == Integer.class || fieldType == int.class) {
									Method setter = tempLoop.getMethod(setMethodName, fieldType);
									setter.invoke(bean, rs.getInt(i));
								}
							}
						}
					}
				}
				tempLoop = tempLoop.getSuperclass();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return bean;
	}
	
	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error("close connection is failed.", e);
				throw new RuntimeException("close connection is failed.", e);
			}
		}
	}
	
	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				logger.error("close statement is failed.", e);
				throw new RuntimeException("close statement is failed.", e);
			}
		}
	}
	
	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("close resultset is failed.", e);
				throw new RuntimeException("close resultset is failed.", e);
			}
		}
	}
}
