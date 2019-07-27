package clazz.usage;

import java.util.Random;

/**
 * Random类用法
 * ThreadLocalRandom类用法
 */
public class RandomDemo {

	public static void main(String[] args) {
		/*
		 * Random与ThreadLocalRandom产生的都是伪随机数
		 * 当使用相同的随机数种子时，会产生相同的随机数
		 * ThreadLocalRandom用于高并发场景
		 * 
		 * 使用Random的好处就是，方便
		 * 比使用Math.random()方法方便
		 * 
		 * Random提供各种nextXxx()方法，用于获得指定类型、指定范围的随机数
		 */
		
		// Random的无参数构造器，以当前时间作为随机数种子
		Random rand = new Random();
	
		// 获得[0,100]的随机整数
		int randInt = rand.nextInt(100);
		
		// ThreadLocalRandom与Random用法类似
	}
}
