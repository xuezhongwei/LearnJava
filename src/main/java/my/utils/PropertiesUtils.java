package my.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PropertiesUtils {
	private static Logger logger = LoggerFactory.getLogger(SQLUtils.class);
	
	public static void main(String[] args) {
		Properties props = loadProperties();
		logger.info(props.toString());
	}
	/**
	 * 加载配置文件默认方法
	 * 默认配置文件名称为：config.properties
	 * （1）优先在该类所在路径下，查找配置文件
	 * （2）其次在classpath下查找配置文件
	 */
	public static Properties loadProperties() {
		String defaultConfig = "config.properties";
		Properties props = loadProperties(defaultConfig);
		if (props == null) {
			defaultConfig = "/config.properties";
			props = loadProperties(defaultConfig);
		}
		return props;
	}
	
	/**
	 *  加载配置文件
	 */
	public static Properties loadProperties(String configFilePath) {
		InputStream is = null;
		Properties props = null;
		try {
			is = SQLUtils.class.getResourceAsStream(configFilePath);
			if (is == null) {
				return null;
			}
			props = new Properties();
			props.load(is);
		} catch (FileNotFoundException e) {
			logger.error("打开配置文件失败！", e);
			throw new RuntimeException("打开配置文件失败！", e);
		} catch (IOException e) {
			logger.error("加载配置文件失败！", e);
			throw new RuntimeException("加载配置文件失败！", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error("关闭流失败！", e);
					throw new RuntimeException("关闭流失败！", e);
				}
			}
		}
		
		return props;
	}
	
	private PropertiesUtils() {
		// 工具类不让创建实例
	}
	
}
