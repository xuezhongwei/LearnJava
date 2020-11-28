package little.project.crawler;
/**
 * 爬取亚马逊云价格数据 
 */
public class CrawlAmazonCloudPriceData {
	public static void main(String[] args) {
		System.out.print("----------------");
		HtmlResolveHandler htmlResolveHandler = new HtmlResolveHandler();
		
		String filePath = "./html/amazonprice.html";
		htmlResolveHandler.resolveAmazonCloudPrice(filePath);
		
	}
}
