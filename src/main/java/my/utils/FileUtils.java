package my.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FileUtils {
	private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
	/**
	 * 将字符串写入指定文件
	 */
	public static void writeStrInFile(String str, File file) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			byte[] buffer = str.getBytes("utf-8");
			os.write(buffer);
			os.flush();
		} catch (FileNotFoundException e) {
			logger.error("open FileOutputSream failed.");
			throw new RuntimeException("open FileOutputSream failed.", e);
		} catch (IOException e) {
			logger.error("write FileOutputSream failed.");
			throw new RuntimeException("write FileOutputSream failed.", e);
		} finally {
			close(os);
		}
	}
	
	/**
	 * 将字符串写入指定文件
	 */
	public static void writeStrInFile(String str, String filePath) {
		OutputStream os = null;
		try {
			if (!isExist(filePath)) {
				mkdirs(filePath);
			}
			os = new FileOutputStream(filePath);
			byte[] buffer = str.getBytes("utf-8");
			os.write(buffer);
			os.flush();
		} catch (FileNotFoundException e) {
			logger.error("open FileOutputSream failed.");
			throw new RuntimeException("open FileOutputSream failed.", e);
		} catch (IOException e) {
			logger.error("write FileOutputSream failed.");
			throw new RuntimeException("write FileOutputSream failed.", e);
		} finally {
			close(os);
		}
	}
	/**
	 * 关闭输入流 
	 */
	public static void close(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				logger.error("close InputStream failed.");
				throw new RuntimeException("close InputStream failed.", e);
			}
		}
	}
	/**
	 * 关闭输出流 
	 */
	public static void close(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				logger.error("close OutputStream failed.");
				throw new RuntimeException("close OutputStream failed.", e);
			}
		}
	}
	/**
	 * 判断指定路径是否存在
	 */
	public static boolean isExist(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}
	/**
	 * 如果一个文件不存在，则创建文件相关路径
	 */
	public static void mkdirs(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			File parentFile = file.getParentFile();
			parentFile.mkdirs();
		}
	}
	
	/**
	 * 如果一个文件不存在，则创建文件相关路径
	 */
	public static void mkdirs(File file) {
		if (!file.exists()) {
			File parentFile = file.getParentFile();
			parentFile.mkdirs();
		}
	}
	
	public static void main(String[] args) {
		String filePath = "targetCode/test.java";
		mkdirs(filePath);
	}
}
