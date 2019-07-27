package clazz.usage;

import java.io.IOException;

/**
 * Runtime类用法
 */
public class RuntimeDemo {

	public static void main(String[] args) throws IOException, InterruptedException {
		/*
		 * Runtime类是一个单例类
		 * 用于获得当前运行系统的信息
		 * 执行系统命令，类似cmd
		 */
		
		// 获得一个Runtime对象
		Runtime rt = Runtime.getRuntime();
		
		// 类似cmd功能，执行特定系统命令，这个功能很强大
		System.out.print("begin");
		// 如果使用相对路径，则是相对于当前项目根目录路径起点
		Process process = rt.exec("javac -encoding utf-8 -d . ./src/main/java/clazz/usage/RuntimeDemo.java");
		// 阻塞当前线程，等待process执行完
		process.waitFor();
	}

}
