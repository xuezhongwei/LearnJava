package crazy.java;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * DBCP数据库连接池用法样例
 */
public class DBCPDemo {

	public static void main(String[] args) {
		BasicDataSource ds = new BasicDataSource();
		/*
		 * 以下配置为DBCP数据源的基本配置项
		 * 具体参数值是根据项目实际来设置的
		 * 而且这些配置内容，是配置在配置文件中，通过读取配置文件的方式设置的，实际中是不会在代码中写死配置内容的
		 */
		
		// 设置连接池所需的驱动
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		// 设置连接数据库的URL
		ds.setUrl("jdbc:mysql://127.0.0.1:3306/jdbc_test?characterEncoding=utf8");
		// 设置数据库连接的用户名
		ds.setUsername("root");
		// 设置数据库连接密码
		ds.setPassword("xzw295077145");
		// 设置连接池的初始连接数
		ds.setInitialSize(5);
		// 设置连接池最大连接数
		ds.setMaxActive(30);
		// 设置连接池最小空闲连接数
		ds.setMinIdle(2);
		
		try {
			// 获得Connection
			Connection conn = ds.getConnection();
			// 剩下的操作就和使用JDBC一样了
			
			// 最后，把Connection还给连接池，而不是释放物理资源
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
