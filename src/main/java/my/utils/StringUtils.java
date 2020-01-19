package my.utils;

public final class StringUtils {
	public static final String FORMAT_TAB = "\t";
	public static final String FORMAT_LF = "\n";
	public static final String LEFT_BRACE = "{";
	public static final String RIGTH_BRACE = "}";
	public static final String SPACE = " ";
	public static final String SEMICOLON = ";";
	public static final String PATH_SEPARATOR = "/";
	
	/**
	 * 判断字符串是否为空串
	 */
	public static boolean isEmptyOrNull(String str) {
		if (null == str || str.length() == 0) {
			return true;
		} else { 
			return false;
		}
	}
	
	/**
	 * 以_分割的字符串转UpperCaseCamel
	 * 例：upper_case_camel转UpperCaseCamel
	 */
	public static String convertToUpperCaseCamel(String str) {
		if (!isEmptyOrNull(str)) {
			StringBuilder target = new StringBuilder();
			
			String[] words = str.split("_");
            for (int i = 0; i < words.length; i++) {
            	String temp = words[i].substring(0, 1).toUpperCase() + words[i].substring(1);
            	target.append(temp);
            }
            return target.toString();
		}
		return str;
	}
	
	/**
	 * 以_分割的字符串转lowerCaseCamel
	 * 例：lower_case_camel转lowerCaseCamel
	 */
	public static String convertToLowerCaseCamel(String str) {
		if (!isEmptyOrNull(str)) {
			StringBuilder target = new StringBuilder();
			
			String[] words = str.split("_");
			target.append(words[0]);
            for (int i = 1; i < words.length; i++) {
            	String temp = words[i].substring(0, 1).toUpperCase() + words[i].substring(1);
            	target.append(temp);
            }
            return target.toString();
		}
		return str;
	}
	

	public static void main(String[] args) {
		String str = "xx";
		System.out.print(convertToUpperCaseCamel(str));
	}
}
