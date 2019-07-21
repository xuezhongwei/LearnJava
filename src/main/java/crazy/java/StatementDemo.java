package crazy.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Statement,PreparedStatement
 * 用法样例
 */
public class StatementDemo {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		/*
		 * Statement与PreparedStatement用法的差别
		 * 推荐使用PreparedStatement
		 * （1）PreparedStatement比Statement执行效率高，因为PreparedStatement会对sql进行预编译
		 * （2）PreparedStatement不存在SQL注入的风险，而Statement需要将参数拼接进SQL，就存在SQL注入风险
		 * 
		 * 除此之外，PreparedStatement是Statement的子接口。Statement有的方法，PreparedStatement都有。
		 */
		Connection conn = getConnection();
		
		// PreparedStatement用法
		// SQL中需要的参数使用占位符代替，通常用？
		// 不存在SQL注入风险
		String loginSql1 = "select * from user where user_name = ? and password = ? ";
		PreparedStatement pstmt = conn.prepareStatement(loginSql1);
		// 为SQL中的参数设值，参数编号从左到右，从1开始
		pstmt.setString(1, "xzw");
		pstmt.setString(2, "123456");
		// 使用无参数的executeQuery(),executeUpdate()
		ResultSet rs = pstmt.executeQuery();
		
		// Statement用法
		Statement stmt = conn.createStatement();
		String userName = "xzw";
		String password = "123456";
		// 将参数拼接进SQL，存在SQL注入风险
		String loginSql2 = "select * from user where user_name = '" + userName + "' and password = '" + password + "'";
		// 使用有参数的executeQuery(String sql),executeUpdate(String sql)
		ResultSet rs2 = stmt.executeQuery(loginSql2);
	}
	
	/**
	 * 获得数据库连接
	 */
	private static Connection getConnection() throws ClassNotFoundException, SQLException {
		// step1:加载特定数据库的JDBC驱动
		// 有三种加载JDBC驱动的方法，以下是最常用的方法
		Class.forName("com.mysql.jdbc.Driver");
		
		// step2:配置数据源
		// 数据源通常配置在配置文件中
		String url = "jdbc:mysql://127.0.0.1:3306/sys";
		String user = "root";
		String password = "xzw295077145";
		
		// step3:获得数据库连接
		return DriverManager.getConnection(url, user, password);
	}
}
