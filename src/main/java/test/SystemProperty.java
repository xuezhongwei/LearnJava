package test;

import java.util.Properties;

/**
 * 往System里添加参数应注意
 */
public class SystemProperty {

	public static void main(String[] args) {
		/*
		 * System类不可实例化
		 * System类里有一个Properties对象，在初始化System时，
		 * VM会将当前系统有关的信息保存到这个Properties对象里
		 * 如果需要往System里添加自定义的参数，不要使用setProperties(Properties properties)方法，这样会把原有的properties对象覆盖掉
		 * 应该使用setProperty(String key, String value)方法，一个属性一个属性的添加
		 */
		// 打印出当前系统参数
		Properties sysProp = System.getProperties();
		System.out.println(System.getProperties());
		
		Properties myProp = new Properties();
		myProp.setProperty("key", "value");
		System.setProperties(myProp);
		// 原有系统参数已经没有了，只有一个<key, value>参数
		System.out.println(System.getProperties());
		
		System.setProperties(sysProp);
		// 往System里添加参数的正确做法
		// 方法1：
		System.setProperty("key", "value");
		System.out.println(System.getProperties());
		
		// 方法2：这种方法更好
		myProp.setProperty("hello", "world");
		for (Object key : myProp.keySet()) {
			sysProp.setProperty((String)key, myProp.getProperty((String)key));
		}
		System.out.println(System.getProperties());
	}

}
