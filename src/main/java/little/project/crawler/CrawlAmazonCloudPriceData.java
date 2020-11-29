package little.project.crawler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 爬取亚马逊云价格数据 
 */
public class CrawlAmazonCloudPriceData {
	public static void main(String[] args) throws IOException {
		System.out.println("----------------");
		HtmlResolveHandler htmlResolveHandler = new HtmlResolveHandler();
		
		String inputFilePath = "./html/amazonprice.html";
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String curDate = formatter.format(calendar.getTime());
		String outputFilePath = "./html/" + curDate + " Amazon EC2 Linux 定价.xls";
		
		htmlResolveHandler.resolveAmazonCloudPrice(inputFilePath, outputFilePath);
	}
}
