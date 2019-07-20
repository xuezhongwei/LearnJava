package crazy.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC编程模板
 * 
 */
public class JDBCDemo {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// step1:加载特定数据库的JDBC驱动
		// 有三种加载JDBC驱动的方法，以下是最常用的方法
		Class.forName("com.mysql.jdbc.Driver");
		
		// step2:配置数据源
		// 数据源通常配置在配置文件中
		String url = "jdbc:mysql://127.0.0.1:3306/sys";
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
		String sql = "select variable var, value, set_time setTime, set_by setBy from sys_config "; // sql中最后的;可以不要
		ResultSet rs = stmt.executeQuery(sql);
		
		// step6:处理查询结果集
		// ResultSet中有一个游标，用于遍历查询结果集，当使用next方法时，游标位置自动向后移动一位
		// 初始时，游标位置默认在第一条记录前一个位置。通常使用while循环遍历结果集。
		// ResultSet中定义了一系列getXxx方法用于获取列值，如果找不到指定列会报错。
		// getXxx(int columnIndex) // 根据列的编号获得该列值。编号从1开始
		// getXxx(String columnLable) // 根据列的SQL AS别名来获得列值。当SQL中没有给列取别名，则别名和表中定义的列名相同。
		while (rs.next()) {
			// variable列的别名是第一列，且取别名为var，所以下边var1和var2是相同的。
			String var1 = rs.getString(1);
			String var2 = rs.getString("var");
		}
		
		// step7:一定要关闭资源
		// 请确保代码在所有情况下，甚至在异常和错误条件下，都能关闭和释放JDBC资源
		rs.close();
		stmt.close();
		conn.close();
	}
}
