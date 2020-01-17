package crazy.java;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {

	public static void  main(String[] args) {
		ExecutorService pool = Executors.newFixedThreadPool(2);
		/*
		 * Runnable target = () -> { for (int i = 0; i< 10; i++) {
		 * System.out.println(Thread.currentThread().getName() + "的i 值为：" + i); } };
		 */
		/*
		 * pool.submit(target); pool.submit(target); pool.submit(target);
		 * pool.submit(target); pool.submit(target); pool.submit(target);
		 * pool.shutdown();
		 */
	}
}
