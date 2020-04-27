package test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		//installEbEntShareReportSql("C:\\Users\\Dev-005\\Desktop\\尾款自动融资协议编号查询.xlsx");
	}
	
	public static void dealExcel() throws IOException {
		// 获得Workbook
		List<Workbook> workbookList = getWorkBook();
		StringBuilder sb = new StringBuilder();
		for (Workbook workbook : workbookList) {
			// 获得工作表
			Sheet sheet = workbook.getSheetAt(0);
			/*
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
			*/
			Map<String, String> ebillMap = loadMidData();
			int rowNum = sheet.getLastRowNum();
			for (int i = 1; i <= rowNum; i++) {
				Row row = sheet.getRow(i);
				// 注意，这里一定要判空
				if (row != null) {
					Cell ebillCodeCell = row.getCell(0);
					Cell delayFeeIncomeCell = row.getCell(2);
					
					// 注意，这里一定要判空
					if (ebillCodeCell != null) {
						String ebillCode = ebillCodeCell.getStringCellValue();
						String agreementCode = ebillMap.get(ebillCode);
						delayFeeIncomeCell.setCellValue(agreementCode);
						System.out.println(ebillCode);
					}
				}
			}
			OutputStream os = new FileOutputStream("C:\\Users\\Dev-005\\Desktop\\尾款自动融资协议编号查询 - 副本.xlsx");
			workbook.write(os);
			os.flush();
		}
		//String str = sb.substring(0, sb.lastIndexOf(","));
		//System.out.println("{" + str + "}");
	}
	
	public static List<Workbook> getWorkBook() throws IOException {
		List<Workbook> workbookList = new ArrayList<>();
		
		String basePath = "C:\\Users\\Dev-005\\Desktop\\尾款自动融资协议编号查询.xlsx";

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
	 * filePath:运营提供的修复数据的excel文件路径
	 * @throws IOException 
	 */
	public static void installEbEntShareReportSql(String filePath) throws IOException {
		File file = new File(filePath);
		InputStream is = new FileInputStream(file);
		String fileName = file.getName();
		String expandedName = fileName.substring(fileName.lastIndexOf(".") + 1);
		Workbook workBook = null;
		if (SUFFIX_XLS.equals(expandedName)) {
			// xls文件用HSSFWorkbook处理
			workBook = new HSSFWorkbook(is);
		} else if (SUFFIX_XLSX.equals(expandedName)) {
			// xlsx文件用XSSFWorkbook处理
			workBook = new XSSFWorkbook(is);
		}
		
		// 获得工作表
		Sheet sheet = workBook.getSheetAt(0);
		// 获得行
		Row row = sheet.getRow(0);
		int colNum = row.getLastCellNum();
		int targetIndexEbillCode = -1;
		int targetIndexDelayFeeIncome = -1;
		int targetIndexDelayInterestIncome = -1;
		int targetIndexDelayDays = -1;
		int targetIndexInterestShareAmt = -1;
		
		for (int i = 0; i < colNum; i++) {
			// 获得单元格
			Cell cell = row.getCell(i);
			String colName = cell.getStringCellValue();
			if ("融单编号".equals(colName)) {
				targetIndexEbillCode = i;
			}
			if ("延期费用收益".equals(colName)) {
				targetIndexDelayFeeIncome = i;
			}
			if ("延期利息收益".equals(colName)) {
				targetIndexDelayInterestIncome = i;
			}
			if ("延期天数".equals(colName)) {
				targetIndexDelayDays = i;
			}
			if ("保理返点金额".equals(colName)) {
				targetIndexInterestShareAmt = i;
			}
		}
		if (targetIndexEbillCode != -1 && targetIndexDelayFeeIncome != -1 && targetIndexDelayInterestIncome != -1 && targetIndexDelayDays != -1 && targetIndexInterestShareAmt != -1) {
			int rowNum = sheet.getLastRowNum();
			StringBuilder ebillCodes = new StringBuilder();
			for (int i = 1; i <= rowNum; i++) {
				row = sheet.getRow(i);
				// 注意，这里一定要判空
				if (row != null) {
					Cell ebillCodeCell = row.getCell(targetIndexEbillCode);
					Cell delayFeeIncomeCell = row.getCell(targetIndexDelayFeeIncome);
					Cell delayInterestIncomeCell = row.getCell(targetIndexDelayInterestIncome);
					Cell delayDaysCell = row.getCell(targetIndexDelayDays);
					Cell interestShareAmtCell = row.getCell(targetIndexInterestShareAmt);
					
					// 注意，这里一定要判空
					if (ebillCodeCell != null && delayFeeIncomeCell != null) {
						String ebillCode = ebillCodeCell.getStringCellValue();
						double delayFeeIncome = delayFeeIncomeCell.getNumericCellValue();
						double delayInterestIncome = delayInterestIncomeCell.getNumericCellValue();
						int delayDays = (int)delayDaysCell.getNumericCellValue();
						double interestShareAmt = interestShareAmtCell.getNumericCellValue();
						
						StringBuilder updateEntShareSql = new StringBuilder();
						updateEntShareSql.append("update eb_ent_share_report set ");
						updateEntShareSql.append("delay_days = '").append(delayDays).append("', ");
						updateEntShareSql.append("delay_interest_income = '").append(delayInterestIncome).append("', ");
						updateEntShareSql.append("delay_fee_income = '").append(delayFeeIncome).append("', ");
						updateEntShareSql.append("interest_share_amt = '").append(interestShareAmt).append("', ");
						DateFormat format = new SimpleDateFormat("yyyyMMdd");
						String currentDate = format.format(new Date());
						updateEntShareSql.append("remark = '").append(currentDate).append("修复数据' ");
						updateEntShareSql.append("where ebill_code = '").append(ebillCode).append("';");
						
						System.out.println(updateEntShareSql.toString());
						
						ebillCodes.append(ebillCode).append(",");
					}
				}
			}
			
			if (ebillCodes.length() != 0) {
				System.out.println("ebillCodes = " + ebillCodes.substring(0, ebillCodes.lastIndexOf(",")));
			}
		}
	}
	
	private static Map<String,String> loadMidData() throws IOException {
		String path = "C:\\Users\\Dev-005\\Desktop\\2020-04-26 尾款自动融资协议编号查询.txt";
		File file = new File(path);
		InputStream is = new FileInputStream(file);
		// 使用
		BufferedReader br = new BufferedReader(new InputStreamReader(is)); 
		
		Map<String, String> retMap = new HashMap<>();
		
		String line = null;
		while((line = br.readLine()) != null) {
			String[] strs = line.split(",");
			if (strs.length == 2) {
				retMap.put(strs[0], strs[1]);
			} else {
				retMap.put(strs[0], "");
			}
		}
		return retMap;
	}
}
