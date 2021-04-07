package my.utils;

import java.util.Date;

public final class CheckUtils {

	public static void main(String[] args) {
		System.out.print(checkPercentStr("100.1"));
		System.out.print(new Date());
	}
	/**
	 * 校验金额字符串是否合法
	 * 小数点后最多两位的正数
	 */
	public static boolean checkMoneyStr(String moneyStr) {
		if (null != moneyStr) {
			return moneyStr.matches("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
		} else {
			return false;
		}
	}
	
	/**
	 * 校验金额字符串是否合法
	 * 小数点后最多两位的正数
	 */
	public static boolean checkPercentStr(String moneyStr) {
		if (null != moneyStr) {
			return moneyStr.matches("(^(([1-9]{1}\\d?)|([0]{1}))(\\.(\\d){0,2})?$)|(100(\\.0{0,2})?)");
			//return moneyStr.matches("100(\\.0{0,2})?");
		} else {
			return false;
		}
	}
	
	
	/**
	 * 校验金额字符串是否合法
	 * 小数点后最多两位的正数
	 * @param bit 整数位数限制
	 */
	public static boolean checkMoneyStr(String moneyStr, int bit) {
		if (bit < 1) {
			return false;
		}
		if (null != moneyStr) {
			return moneyStr.matches("^(([1-9]{1}\\d{0,"+ (bit - 1) +"})|([0]{1}))(\\.(\\d){0,2})?$");
		} else {
			return false;
		}
	}
	
	private CheckUtils() {
		// 工具类不让实例化
	}
}
