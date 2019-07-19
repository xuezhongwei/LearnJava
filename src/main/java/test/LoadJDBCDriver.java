package test;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Driver;

/**
 * 加载JDBC驱动的方式
 */
public class LoadJDBCDriver {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		/*
		 * 加载jdbc驱动有三种方式
		 */
		// 方法1
		// 通过反射来加载Driver，Driver中存在如下静态初始化块，会Driver注册到DriverManager中
		/*
		 * static {
				try {
					java.sql.DriverManager.registerDriver(new Driver());
				} catch (SQLException E) {
					throw new RuntimeException("Can't register driver!");
				}
			}
		*/
		Class.forName("com.mysql.jdbc.Driver");
		
		// 方法2
		// 直接通过DriverManager.registerDriver()方法
		DriverManager.registerDriver(new Driver());
		
		// 方法3
		// 通过设置系统参数的方式
		System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
		// 然后直接使用DriverManager，DriverManager源码中有一个静态初始化块
		// 会加载DriverManager这个类时，在初始化的时候根据系统参数中jdbc.drivers属性，加载Driver
	}
}
