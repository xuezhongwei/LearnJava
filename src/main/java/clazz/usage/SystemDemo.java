package clazz.usage;

import java.util.Properties;

/**
 * System使用
 *
 */
public class SystemDemo {

	public static void main(String[] args) {
		/*
		 * System类不能创建实例
		 * 提供了一系列类方法
		 */
		
		// 获得系统当前时间的毫秒数，从1970年1月1日开始
		long currentTimeMillis = System.currentTimeMillis();
		
		// 实际开发中，System最常见的用法是配置系统参数
		System.setProperty("", "");
		Properties props = new Properties();
		System.setProperties(props);
		
		// 获得指定对象的精确hashCode值
		// 如果两个对象的identityHashCode值相同，则两个对象绝对是同一个对象
		Integer obj1 = 1;
		Integer obj2 = obj1;
		int identityHashCode1 = System.identityHashCode(obj1);
		int identityHashCode2 = System.identityHashCode(obj2);
		System.out.print(identityHashCode1 == identityHashCode2 ? "相同对象" : "不同对象");
		
	}

}
