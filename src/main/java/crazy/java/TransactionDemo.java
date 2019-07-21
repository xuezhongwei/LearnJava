package crazy.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * JDBC事务使用样例
 */
public class TransactionDemo {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();
		try {
			// 关闭事务自动自动提交，即开启事务
			conn.setAutoCommit(false);
			
			// 执行各种操作
			// 创建Statement，执行sql，处理sql执行结果
			Statement stmt = conn.createStatement();
			String sql = "update xxxx";
			stmt.execute(sql);
			
			// 提交事务
			conn.commit();
		} catch (SQLException e) {
			// 当遇到异常，必须回滚事务
			conn.rollback();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
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
