package my.utils;

import java.text.SimpleDateFormat;

public final class DateUtils {
	public static final String DATE_FORMAT_0 = "yyyyMMdd";
	public static final String DATE_FORMAT_1 = "yyyy-MM-dd";
	public static final String DATE_FORMAT_2 = "yyyy/M/d";
	public static final String DATE_FORMAT_3 = "yyyy/MM/dd";
	public static final String DATE_FORMAT_4 = "yyyy.MM.dd";
	public static final String DATE_FORMAT_5 = "HHmmss";
	public static final String DATE_FORMAT_6 = "HH:mm:ss";
	public static final String DATE_FORMAT_7 = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT_8 = "yyyy-MM";
	public static final String DATE_FORMAT_9 = "yyyyMM";
	
	public static void main(String[] args) {
		
	}
	
	public static long getTimeStamp(String dateString) {
		String createTime = "2016-10-24 21:59:06";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
		   return sdf.parse(createTime).getTime();
		} catch (Exception e) {
		   e.printStackTrace();
		}
		return 0;
	}
}
