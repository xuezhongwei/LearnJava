package my.utils;

public final class StringUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static boolean isEmptyOrNull(String str) {
		if (null == str || str.length() == 0) {
			return true;
		} else { 
			return false;
		}
	}
}
