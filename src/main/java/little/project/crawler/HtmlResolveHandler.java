package little.project.crawler;

import java.io.FileInputStream;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlResolveHandler {
	
	public void resolveAmazonCloudPrice(String filePath) {
		Document doc = loadHtmlFile(filePath);
		
		Element body = doc.body();
		Elements ele = body.select(".lb-accordion-group");
		ele.forEach(e->System.out.println(e.text()));
		//System.out.print(ele.size());
	}
	/**
	 * 加载xml文件
	 */
	public Document loadHtmlFile(String file) {
		Document document = null;
		try {
			InputStream is = new FileInputStream(file);
			byte[] bf = new byte[is.available()];
			is.read(bf);
			String html = new String(bf);
			document = Jsoup.parse(html);
		} catch (Exception e) {
			throw new RuntimeException("load html file failed", e);
		}
		return document;
	}
	
}
