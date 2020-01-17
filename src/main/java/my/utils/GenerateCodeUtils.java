package my.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 生成代码工具类
 *
 */
public class GenerateCodeUtils {
	/**
	 * （1）生成与数据库表对应的model
	 * （2）生成与数据库表mapper.xml
	 * 
	 * 因为不同企业对mybatis的使用会存在差异，所以这个实现会存在差异
	 * 可以生成指定表的代码，也可以生成所有表的代码
	 * 
	 * 要考虑到表结构的更新
	 */
	
	/**
	 * @throws SQLException 
	 * 
	 */
	public static void zejinVersion() {
		String packageName = "";
		String tableName = "user";
		try {
			generateModel(packageName, tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void generateModel(String packageName, String tableName) throws SQLException {
		Connection conn = SQLUtils.getConnection();
		Statement state = conn.createStatement();
		DatabaseMetaData dbmd = conn.getMetaData();
		
		// getColums最一般的用法
		// getColums(null, null, tableName, "%") 获得某个表的所有字段信息
		ResultSet rs = dbmd.getColumns(null, null, tableName, "%");
		StringBuilder fields = new StringBuilder();
		while (rs.next()) {
            // 列名
            String columnName = rs.getString("COLUMN_NAME");
            // 类型名称
            String typeName = rs.getString("TYPE_NAME");
            // 注释
            String remarks = rs.getString("REMARKS");
            
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
            var += ";\n";
            fields.append(comments + var);
        }
		System.out.println(fields.toString());
		
		// 拼接类的申明
		StringBuilder classDeclare = new StringBuilder();
		classDeclare.append("public class ");
		
		SQLUtils.close(state);
		SQLUtils.close(conn);
	}
	
	private static void generateMapper(String namespace) {
		
	}
	
	public static void main(String[] args) {
		zejinVersion();
	}
}
