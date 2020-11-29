package little.project.crawler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class HtmlResolveHandler {
	
	public void resolveAmazonCloudPrice(String inputFilePath, String outputFilePath) throws IOException {
		Document doc = loadHtmlFile(inputFilePath);
		
		HSSFWorkbook xls = new HSSFWorkbook();
		//HSSFCellStyle cellStyle = getCellStyle(xls);
		
		Element body = doc.body();
		Elements dataModel = body.select(".lb-accordion-group");
		Iterator<Element> dataModelIterator = dataModel.iterator();
		
		Map<String, Integer> map = new HashMap<>();
		
		while (dataModelIterator.hasNext()) {
			Element dataModelItem = dataModelIterator.next();
			
			// sheet页
			Element tableDiv = dataModelItem.selectFirst(".lb-txt-none");
			// sheet名称
			String sheetName = tableDiv.text();
			String prefix = getSheetNamePrefix(sheetName, map);
			
			Sheet sheet = xls.createSheet(prefix + "-" + sheetName);
			
			// 数据表
			Element table = dataModelItem.selectFirst("table");
			// 表行
			Elements trs = table.select("tr");
			Iterator<Element> trsIterator = trs.iterator();
			int rowNum = 0;
			while (trsIterator.hasNext()) {
				Row row = sheet.createRow(rowNum++);

				Element tr = trsIterator.next();
				// 每一行的单元格
				Elements tds = tr.select("td");
				Iterator<Element> tdIterator = tds.iterator();
				int cellNum = 0;
				while (tdIterator.hasNext()) {
					sheet.setColumnWidth(cellNum, 20 * 256);
					Element td = tdIterator.next();
					// 单元格内容
					String cellValue = td.text();
					Cell cell = row.createCell(cellNum++);
					cell.setCellValue(cellValue);
					//cell.setCellStyle(cellStyle);
				}
			}
		}
		OutputStream os = new FileOutputStream(outputFilePath);
		xls.write(os);
		xls.close();
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
	
	
	public HSSFCellStyle getCellStyle(HSSFWorkbook workbook) {
		HSSFCellStyle cellStyle = workbook.createCellStyle();
        //设置水平居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置下边框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        //设置上边框
        cellStyle.setBorderTop(BorderStyle.THIN);
        //设置走边框
        cellStyle.setBorderLeft(BorderStyle.THIN);
        //设置右边框
        cellStyle.setBorderRight(BorderStyle.THIN);
        //设置字体
        HSSFFont font = workbook.createFont();
        font.setFontName("华文行楷");//设置字体名称
        font.setFontHeightInPoints((short)28);//设置字号
        font.setItalic(false);//设置是否为斜体
        font.setBold(true);//设置是否加粗
        font.setColor(IndexedColors.RED.index);//设置字体颜色
        cellStyle.setFont(font);
        //设置背景
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        
        return cellStyle;
	}
	
	private String getSheetNamePrefix(String sheetName, Map<String, Integer> map) {
		if (map.containsKey(sheetName)) {
			Integer count = map.get(sheetName);
			count = count + 1;
			map.put(sheetName, count);
		} else {
			map.put(sheetName, 1);
		}
		
		int count = map.get(sheetName);
		
		if (count == 1) {
			return "宁夏";
		} else {
			return "北京";
		}
	}
}
