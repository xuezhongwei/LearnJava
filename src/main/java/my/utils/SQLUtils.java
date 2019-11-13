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
		String sql = "select * from eb_ent_price_template_temp";
		ResultSet rs = state.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		
		DatabaseMetaData dbmd = conn.getMetaData();
		//printColumnInfo(dbmd);
		//printMapperResultMap(dbmd);
		printMapperInsertSql(dbmd);
		state.close();
		conn.close();
	}
	
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String url = "jdbc:mysql://192.168.2.250:3306/dev_izejin";
			String user = "izejin";
			String password = "qeadzc132";
			
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
	
	public static void printColumnInfo(DatabaseMetaData databaseMetaData) {
		ResultSet rs;
		try {
			rs = databaseMetaData.getColumns(null, "%", "eb_ent_price_template_temp", "%");
			while(rs.next()){
	            //列名
	            String columnName = rs.getString("COLUMN_NAME");
	            //类型
	            String typeName = rs.getString("TYPE_NAME");
	            //注释
	            String remarks = rs.getString("REMARKS");
	            
	            String comments = "/**\n" + " * " + remarks + "\n */\n"; 
	            
	            String var = "private ";
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
	
	public static void printMapperResultMap(DatabaseMetaData dbmd) {
		ResultSet rs;
		try {
			rs = dbmd.getColumns(null, "%", "eb_ent_price_template_temp", "%");
			while(rs.next()){
	            //列名
	            String columnName = rs.getString("COLUMN_NAME");
	            //类型
	            String typeName = rs.getString("TYPE_NAME");
	            //注释
	            String remarks = rs.getString("REMARKS");
	            
	            String comments = "/**\n" + " * " + remarks + "\n */\n"; 
	            
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
}
