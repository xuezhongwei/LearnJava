package little.project.crawler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CrwalHuaweiCloudPriceData {
	public static void main(String[] args) throws IOException {
		HtmlResolveHandler htmlResolveHandler = new HtmlResolveHandler();
		
		String inputFilePath = "./html/huawei.html";
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String curDate = formatter.format(calendar.getTime());
		String outputFilePath = "./html/" + curDate + " 华为 定价.xls";
		
		htmlResolveHandler.resolveHuaweiCloudPrice(inputFilePath, outputFilePath);
	}
}
