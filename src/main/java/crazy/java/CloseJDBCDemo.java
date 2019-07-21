package crazy.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 如何正确关闭JDBC资源
 * 数据库连接资源用完了，就要关闭
 * 而且，一定要确保万无一失的关闭数据库连接资源
 * 即使是在使用数据连接池时，仍然需要关闭
 * 
 * 可以参照以下博文：
 * https://blog.csdn.net/javy_codercoder/article/details/49178529
 */
public class CloseJDBCDemo {

	public static void main(String[] args) {
		
	}
	public static void correctDemo() throws ClassNotFoundException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 获得Connection对象的方法可以是原生JDBC的方式，也可以是数据库连接池
			conn = getConnection();
			pstmt = conn.prepareStatement("");
			rs = pstmt.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/*
			 * 最有一定要在finally中关闭数据库连接资源
			 * 无需显式关闭ResultSet，因为当创建该ResultSet的Statement对象重新执行/切换下一个结果集/关闭时，ResultSet会自动关闭
			 * 一定是先关闭Statement，再关闭Connection。否则会报错。
			 * 而且要分开关闭，最后还需要把Statement/Connection对象的引用设置为null。
			 * 因为，当Statement/Connection关闭之后，就不可用了，但是对应的Statement对象和Connection对象还存在。设为null之后，就可以垃圾回收了。
			 */
			
			// 关闭Statement
			if (null != pstmt) {
				try {
					// 关闭时，若有异常也要处理
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					// 设为null，让VM进行gc
					pstmt = null;
				}
			}
			
			// 关闭Connection
			if (null != conn) {
				try {
					// 当使用数据库连接池时，以下代码只是把资源还给数据库连接池
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					// 设为null，让VM进行gc
					conn = null;
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
