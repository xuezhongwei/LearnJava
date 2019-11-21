package clazz.usage;

import java.util.Date;

public class StringDemo {
	/*
	 * String的用法很简单
	 * 这里只列举一些常用的format格式化用法
	 * 
	 * 更多格式化用法，百度
	 */
	public static void main(String[] args) {
		System.out.println(String.format("hi, %s", "jack"));
		System.out.println(String.format("%05d", 1));
		
		Date date = new Date();
		
		// 类似： 星期四 十一月 14 22:43:27 CST 2019
		System.out.println(String.format("%tc", date));
		// yyyy-MM-dd
		System.out.println(String.format("%tF", date));
		// HH:mm:ss
		System.out.println(String.format("%tT", date));
		
	}

}
