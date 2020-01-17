package my.utils;

import java.util.Map;

/**
 * 获取当前系统及运行信息
 */
public final class SystemUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 获得运行时对象 
	 */
	public static Runtime getRuntime() {
		return Runtime.getRuntime();
	}
	
	/**
	 * 获得当前系统用户的名称
	 */
	public static String getUserName() {
		String userName = "";
		Map<String, String> envMap = System.getenv();
		userName = envMap.get("USERNAME");
		if (StringUtils.isEmptyOrNull(userName)) {
			userName = System.getProperty("user.name");
		}
		return userName;
	}
}
