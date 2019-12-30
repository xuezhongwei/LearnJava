package api.poi.excel;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
		List<Workbook> workbookList = getWorkBook();
		StringBuilder sb = new StringBuilder();
		for (Workbook workbook : workbookList) {
			int perFileCount = 0;
			Sheet sheet = workbook.getSheetAt(0);
			Row row = sheet.getRow(0);
			int colNum = row.getLastCellNum();
			int targetIndex = -1;
			for (int i = 0; i < colNum; i++) {
				Cell cell = row.getCell(i);
				String colName = cell.getStringCellValue();
				if ("融单编号".equals(colName)) {
					targetIndex = i;
					break;
				}
			}
			if (targetIndex != -1) {
				int rowNum = sheet.getLastRowNum();
				for (int i = 1; i <= rowNum; i++) {
					row = sheet.getRow(i);
					if (row != null) {
						Cell targetCell = row.getCell(targetIndex);
						if (targetCell != null) {
							perFileCount++;
							String targetValue = targetCell.getStringCellValue();
							sb.append("\"" + targetValue + "\",");
							System.out.println(targetValue);
						}
					}
				}
			}
		}
		String str = sb.substring(0, sb.lastIndexOf(","));
		System.out.println("{" + str + "}");
		System.out.println("select * from eb_bill_tx_list where tran_code = 'EB000034' and ebill_code in (" + str + ");");
		System.out.println("select * from eb_voucher_record where template_no = 'B2' and biz_id in (" + str + ");");
	}
	
	public static List<Workbook> getWorkBook() throws IOException {
		List<Workbook> workbookList = new ArrayList<>();
		
		String basePath = "C:\\Users\\Dev-005\\Desktop\\toHandleExcel";

		File[] files = loadFile(basePath);
		for (File file : files) {
			InputStream is = new FileInputStream(file);
			String fileName = file.getName();
			String expandedName = fileName.substring(fileName.lastIndexOf(".") + 1);
			if (SUFFIX_XLS.equals(expandedName)) {
				HSSFWorkbook hssf = new HSSFWorkbook(is);
				workbookList.add(hssf);
			} else if (SUFFIX_XLSX.equals(expandedName)) {
				XSSFWorkbook xssf = new XSSFWorkbook(is);
				workbookList.add(xssf);
			}
		}
		return workbookList;
	}
	public static File[] loadFile(String basePath) {
		File file = new File(basePath);
		if (file.isDirectory()) {
			File[] files = file.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					// 返回是目录或者xls或者xlsx文件
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
	// 获取某个目录下所有指定类型的文件
	
}
