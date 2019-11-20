package clazz.usage;

import java.util.Calendar;
import java.util.Date;

public class CalendarDemo {
	public static void main(String[] args) {
		
		// Calendar是一个抽象类，只能通过getInstance()来获得一个实例
		Calendar calendar = Calendar.getInstance();
		//
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		// 24小时制
		int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
		// 12小时制
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		int milliSecond = calendar.get(Calendar.MILLISECOND);
		long timeInMillis = calendar.getTimeInMillis();
		
		// 
		Date date = calendar.getTime();
		
		calendar.set(Calendar.YEAR, 1);
		calendar.add(Calendar.YEAR, 1);
	}
}
