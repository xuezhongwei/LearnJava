package test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC编程模板
 * 
 */
public class Test {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		test2();
	}
	public static void test() {
		int str = 1;
		String strByFormat = String.format("%05d", str);
		System.out.print(strByFormat);
	}
	public static void test1() throws Exception {
		// step1:加载特定数据库的JDBC驱动
		// 有三种加载JDBC驱动的方法，以下是最常用的方法
		Class.forName("com.mysql.jdbc.Driver");
		
		// step2:配置数据源
		// 数据源通常配置在配置文件中
		String url = "jdbc:mysql://127.0.0.1:3306/jdbc_test?characterEncoding=utf-8&allowMultiQueries=true";
		String user = "root";
		String password = "xzw295077145";
		
		// step3:获得数据库连接
		// Connection对象主要有两个作用：
		// (1) 创建Statement对象，用于执行SQL
		// (2) 处理事务
		Connection conn = DriverManager.getConnection(url, user, password);
		
		// step4:使用Connection创建Statement对象
		// Statement对象用于执行SQL
		Statement stmt = conn.createStatement();
		
		// step5:执行SQL
		// Statement可以执行所有功能的SQL，主要使用以下两个方法：
		// (1) ResultSet executeQuery(String sql) // 执行查询sql，返回查询结果
		// (2) int executeUpdate(String sql) // 返回受影响的行数。当执行DML时，返回0。
		String sql = "insert into dns (domain, ip) VALUES ('1','1');TRUNCATE table dns" ;
		boolean rs = stmt.execute(sql);
		
		stmt.close();
		conn.close();
	}
	static void test2() {
		switch(TestEnum.Test) {
		case Test :
			
		}
	}
}
