package api.poi.excel;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDemo {
	private static String SUFFIX_XLS = "xls";
	private static String SUFFIX_XLSX = "xlsx";
	
	public static void main(String[] args) throws IOException {
		dealExcel();
	}
	
	public static void dealExcel() throws IOException {
		// 获得Workbook
		String baseDir= "";
		List<Workbook> workbookList = getWorkBookList(baseDir);
		StringBuilder sb = new StringBuilder();
		for (Workbook workbook : workbookList) {
			// 获得工作表
			// 每个excel中会有多个sheet。这里取第一个
			Sheet sheet = workbook.getSheetAt(0);
			
			// 获得数据行
			// 第0行为表头
			Row row = sheet.getRow(0);
			int colNum = row.getLastCellNum();
			int targetIndex = -1;
			
			// 遍历行的每个单元格
			for (int i = 0; i < colNum; i++) {
				// 获得单元格
				Cell cell = row.getCell(i);
				// 获得单元格的值
				String colName = cell.getStringCellValue();
				if ("融单编号".equals(colName)) {
					targetIndex = i;
					break;
				}
			}
			
			// 遍历每一行，不含表头行
			int rowNum = sheet.getLastRowNum();
			for (int i = 1; i <= rowNum; i++) {
				row = sheet.getRow(i);
				// 注意，这里一定要判空
				if (row != null) {
					// 处理指定单元格
					Cell targetCell = row.getCell(targetIndex);
					// 注意，这里一定要判空
					if (targetCell != null) {
						// 获得单元格的值
						String targetValue = targetCell.getStringCellValue();
						
						// 设置单元格的值
						targetCell.setCellValue("xxxx");
					}
				}
			}
			
			// 最后将修改后的内容写入输出流中
			OutputStream os = new FileOutputStream("C:\\Users\\Dev-005\\Desktop\\尾款自动融资协议编号查询 - 副本.xlsx");
			workbook.write(os);
			os.flush();
		}
	}
	
	/**
	 * 获取指定文件夹下所有的excel文件
	 * 一个Excel文件对应一个Workbook
	 */
	public static List<Workbook> getWorkBookList(String dir) throws IOException {
		List<Workbook> workbookList = new ArrayList<>();
		
		File[] files = loadExcelFile(dir);
		for (File file : files) {
			// 获得Excel文件的输入流
			InputStream is = new FileInputStream(file);
			String fileName = file.getName();
			String expandedName = fileName.substring(fileName.lastIndexOf(".") + 1);
			
			// xls文件用HSSFWorkbook处理
			if (SUFFIX_XLS.equals(expandedName)) {
				HSSFWorkbook hssf = new HSSFWorkbook(is);
				workbookList.add(hssf);
			// xlsx文件用XSSFWorkbook处理
			} else if (SUFFIX_XLSX.equals(expandedName)) {
				XSSFWorkbook xssf = new XSSFWorkbook(is);
				workbookList.add(xssf);
			}
		}
		return workbookList;
	}
	
	/**
	 * 获得指定路径对应的excel文件
	 * 一个Excel文件对应一个Workbook 
	 */
	public static Workbook getWorkBook(String filePath) throws IOException {
		List<Workbook> workbookList = new ArrayList<>();
		
		File[] files = loadExcelFile(filePath);
		for (File file : files) {
			// 获得Excel文件的输入流
			InputStream is = new FileInputStream(file);
			String fileName = file.getName();
			String expandedName = fileName.substring(fileName.lastIndexOf(".") + 1);
			
			// xls文件用HSSFWorkbook处理
			if (SUFFIX_XLS.equals(expandedName)) {
				HSSFWorkbook hssf = new HSSFWorkbook(is);
				workbookList.add(hssf);
			// xlsx文件用XSSFWorkbook处理
			} else if (SUFFIX_XLSX.equals(expandedName)) {
				XSSFWorkbook xssf = new XSSFWorkbook(is);
				workbookList.add(xssf);
			}
		}
		return workbookList.get(0);
	}
	/**
	 * 加载excel文件 
	 */
	public static File[] loadExcelFile(String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			File[] files = file.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					// 返回xls或者xlsx文件
					boolean flag = (pathname.getName().substring(pathname.getName().lastIndexOf(".") + 1).equals("xls")
							|| pathname.getName().substring(pathname.getName().lastIndexOf(".") + 1).equals("xlsx"))
							;
					return flag;
				}
			});
			return files;
		} else {
			return new File[] {file};
		}
	}
}
