package crazy.java;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * C3P0数据库连接池用法样例
 */
public class C3P0Demo {

	public static void main(String[] args) throws PropertyVetoException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		/*
		 *  以下配置为C3P0数据源的基本配置项
		 *  具体参数值是根据项目实际来设置的
		 *  而且这些配置内容，是配置在配置文件中，通过读取配置文件的方式设置的，实际中是不会在代码中写死配置内容的
		 */
		
		
		// 设置连接池连接数据库所用的驱动
		ds.setDriverClass("com.mysql.jdbc.Driver");
		// 设置连接数据库的URL
		ds.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/jdbc_test?characterEncoding=utf8");
		// 设置用户名
		ds.setUser("root");
		// 设置数据库连接密码
		ds.setPassword("xzw295077145");
		// 设置连接池最大连接数
		ds.setMaxPoolSize(40);
		// 设置连接池最小连接数
		ds.setMinPoolSize(2);
		// 设置连接池初始连接数
		ds.setInitialPoolSize(10);
		// 设置连接池缓存Statement的最大数
		ds.setMaxStatements(180);
		
		try {
			// 获得数据库连接
			Connection conn = ds.getConnection();
			// 剩下的操作就和使用JDBC一样了
			
			// 最后，把Connection还给连接池，而不是释放物理资源
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
