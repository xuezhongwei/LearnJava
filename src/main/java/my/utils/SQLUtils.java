package my.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLUtils {
	public static void main(String[] args) throws SQLException {
		Connection conn = getConnection();
		Statement state = conn.createStatement();
		String sql = "select * from test";
		ResultSet rs = state.executeQuery(sql);
		
		DatabaseMetaData dbmd = conn.getMetaData();
		//printTableToModel(dbmd);
		//printMapperResultMap(dbmd);
		//printMapperInsertSql(dbmd);
		//printAllTableInfo(dbmd);
		printResultSetColumnName(rs);
		state.close();
		conn.close();
	}
	
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String url = "jdbc:mysql://127.0.0.1:3306/lanyuan";
			String user = "root";
			String password = "root";
			
			Connection conn = DriverManager.getConnection(url, user, password);
			if (null != conn) {
				System.out.println("数据库连接成功！");
			}
			return conn;
		} catch (Exception e) {
			System.out.println("数据库连接失败！");
			throw new RuntimeException("", e);
		}
	}
	/**
	 * 生成与数据库表对应的model
	 * 
	 * 获得某个表的所有字段信息
	 * 例如：字段名，字段类型，注释
	 * @param databaseMetaData
	 */
	public static void printTableToModel(DatabaseMetaData databaseMetaData) {
		ResultSet rs;
		try {
			// getColums最一般的用法
			// getColums(null, null, tableName, "%") 获得某个表的所有字段信息
			rs = databaseMetaData.getColumns(null, null, "eb_ent_price_template_temp", "%");
			while(rs.next()){
				// 更多信息请查阅文档
	            // 列名
	            String columnName = rs.getString("COLUMN_NAME");
	            // 类型名称
	            String typeName = rs.getString("TYPE_NAME");
	            // 注释
	            String remarks = rs.getString("REMARKS");
	            
	            System.out.println(columnName + " " + typeName + " " + remarks);
	            
	            // 拼接model字符串
	            String comments = "/**\n" + " * " + remarks + "\n */\n"; 
	            String var = "private ";
	            // 这里只列了几个常用类型，可根据实际情况调整
	            if (typeName.equalsIgnoreCase("bigint")) {
	            	var += "Long ";
	            } else if (typeName.equalsIgnoreCase("DECIMAL")) {
	            	var += "BigDecimal ";
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
	public static void printMapperResultMap(DatabaseMetaData dbmd) {
		ResultSet rs;
		try {
			rs = dbmd.getColumns(null, "%", "eb_ent_price_template_temp", "%");
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
			rs = dbmd.getColumns(null, "%", "eb_ent_price_template_temp", "%");
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
}
