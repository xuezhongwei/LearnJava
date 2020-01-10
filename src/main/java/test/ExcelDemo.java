package test;

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
		// 获得Workbook
		List<Workbook> workbookList = getWorkBook();
		StringBuilder sb = new StringBuilder();
		for (Workbook workbook : workbookList) {
			// 获得工作表
			Sheet sheet = workbook.getSheetAt(0);
			// 获得行
			Row row = sheet.getRow(0);
			int colNum = row.getLastCellNum();
			int targetIndexX = -1;
			int targetIndexY = -1;
			for (int i = 0; i < colNum; i++) {
				// 获得单元格
				Cell cell = row.getCell(i);
				String colName = cell.getStringCellValue();
				if ("融单编号".equals(colName)) {
					targetIndexX = i;
				}
				if ("延期费用收益".equals(colName)) {
					targetIndexY = i;
				}
			}
			if (targetIndexX != -1 && targetIndexY != -1) {
				int rowNum = sheet.getLastRowNum();
				for (int i = 1; i <= rowNum; i++) {
					row = sheet.getRow(i);
					// 注意，这里一定要判空
					if (row != null) {
						Cell ebillCodeCell = row.getCell(targetIndexX);
						Cell delayFeeIncomeCell = row.getCell(targetIndexY);
						
						// 注意，这里一定要判空
						if (ebillCodeCell != null && delayFeeIncomeCell != null) {
							String ebillCode = ebillCodeCell.getStringCellValue();
							double delayFeeIncome = delayFeeIncomeCell.getNumericCellValue();
							String updateFeeSql = "update eb_service_fee_clear set amount = " + delayFeeIncome + ", actual_amount = " + delayFeeIncome + " where ebill_code = '" + ebillCode + "';";
							System.out.println(updateFeeSql);
						}
					}
				}
			}
		}
		//String str = sb.substring(0, sb.lastIndexOf(","));
		//System.out.println("{" + str + "}");
	}
	
	public static List<Workbook> getWorkBook() throws IOException {
		List<Workbook> workbookList = new ArrayList<>();
		
		String basePath = "C:\\Users\\Dev-005\\Desktop\\12月修改融资返点报表数据";

		File[] files = loadFile(basePath);
		for (File file : files) {
			// 获得Excel文件的输入流
			InputStream is = new FileInputStream(file);
			String fileName = file.getName();
			String expandedName = fileName.substring(fileName.lastIndexOf(".") + 1);
			if (SUFFIX_XLS.equals(expandedName)) {
				// xls文件用HSSFWorkbook处理
				HSSFWorkbook hssf = new HSSFWorkbook(is);
				workbookList.add(hssf);
			} else if (SUFFIX_XLSX.equals(expandedName)) {
				// xlsx文件用XSSFWorkbook处理
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
					// 返回xls或者xlsx文件
					boolean flag = pathname.getName().contains("xls") || pathname.getName().contains("xlsx");
					return flag;
				}
			});
			return files;
		} else {
			return new File[] {file};
		}
	}
	
	/**
	 * 生成修复融资返点报表数据SQL
	 */
	public static void installEbEntShareReportSql(String filePath) {
		File
	}
}
