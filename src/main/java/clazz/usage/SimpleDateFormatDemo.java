package clazz.usage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatDemo {

	/**
		G  Era designator  Text  AD  
		y  Year  Year  1996; 96  
		Y  Week year  Year  2009; 09  
		M  Month in year (context sensitive)  Month  July; Jul; 07  
		L  Month in year (standalone form)  Month  July; Jul; 07  
		w  Week in year  Number  27  
		W  Week in month  Number  2  
		D  Day in year  Number  189  
		d  Day in month  Number  10  
		F  Day of week in month  Number  2  
		E  Day name in week  Text  Tuesday; Tue  
		u  Day number of week (1 = Monday, ..., 7 = Sunday)  Number  1  
		a  Am/pm marker  Text  PM  
		H  Hour in day (0-23)  Number  0  
		k  Hour in day (1-24)  Number  24  
		K  Hour in am/pm (0-11)  Number  0  
		h  Hour in am/pm (1-12)  Number  12  
		m  Minute in hour  Number  30  
		s  Second in minute  Number  55  
		S  Millisecond  Number  978  
		z  Time zone  General time zone  Pacific Standard Time; PST; GMT-08:00  
		Z  Time zone  RFC 822 time zone  -0800  
		X  Time zone  ISO 8601 time zone  -08; -0800; -08:00  
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		/*
		 * SimpleDateFormat可以非常灵活的格式化Date对象，也可以将各种格式的日期字符串解析为Date对象
		 * 使用SimpleDateFormat需要指定日期格式
		 * format(Date obj)
		 * parse(String str)
		 */
		Date date = new Date();
		// 最常见的日期格式
		String dateFormat1= "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat format = new SimpleDateFormat(dateFormat1);
		// 将Date对象格式化
		String str = format.format(date);
		System.out.println(str);
		
		String dateStr = "19-11-18 1:1:1";
		// 解析日期字符串
		// 注意：日期字符串的格式必须符合指定格式
		Date date1 = format.parse(dateStr);
		System.out.println(date1);
		
		// 其他日期格式
		String dateFormat2 = "yyyy/MM/dd";
		String dateFormat3 = "yyyyMMdd";
		String dateFormat4 = "yyyy年MM月dd日";
		String dateFormat5 = "yyyy年第D天";
		String dateFormat6 = "一年中的第 D 天 一年中第w个星期 一月中第W个星期 在一天中k时 z时区";
	}

}
