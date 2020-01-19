package my.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 生成代码工具类
 *
 */
public class GenerateCodeUtils {
	private static Logger logger = LoggerFactory.getLogger(GenerateCodeUtils.class);
	/**
	 * （1）生成与数据库表对应的model
	 * （2）生成与数据库表mapper.xml
	 * 
	 * 因为不同企业对mybatis的使用会存在差异，所以这个实现会存在差异
	 * 可以生成指定表的代码，也可以生成所有表的代码
	 * 
	 * 要考虑到表结构的更新
	 */
	public static final String MODIFIER_PUBLIC = "public";
	public static final String MODIFIER_PRIVATE = "private";
	public static final String FORMAT_TAB = "\t";
	public static final String FORMAT_LF = "\n";
	public static final String LEFT_BRACE = "{";
	public static final String RIGTH_BRACE = "}";
	public static final String SPACE = " ";
	public static final String SEMICOLON = ";";
	public static final String PREFIX_SET = "set";
	public static final String PREFIX_GET = "get";
	
	public static final String TARGET_PATH = "targetCode";
	public static final String PATH_SEPARATOR = "/";
	
	private static class ColumnInfo {
		private String columnName;
		private String typeName;
		private String remarks;
		
		public String getColumnName() {
			return columnName;
		}
		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}
		public String getTypeName() {
			return typeName;
		}
		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
	}
	/**
	 * 
	 */
	public static void zejinVersion() {
		String packageName = "com";
		String tableName = "user";
		try {
			generateModel(packageName, tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void generateModel(String packageName, String tableName) throws SQLException {
		Connection conn = SQLUtils.getConnection();
		Statement state = conn.createStatement();
		DatabaseMetaData dbmd = conn.getMetaData();
		
		// getColums最一般的用法
		// getColums(null, null, tableName, "%") 获得某个表的所有字段信息
		StringBuilder fields = new StringBuilder();
		StringBuilder setterAndGetterMethod = new StringBuilder();
		
		ResultSet rs = dbmd.getColumns(null, null, tableName, "%");
		List<ColumnInfo> columnInfoList = getColumnInfoList(rs);
		
		for (ColumnInfo columnInfo : columnInfoList) {
            // 列名
            String columnName = columnInfo.getColumnName();
            // 类型名称
            String typeName = columnInfo.getTypeName();
            // 注释
            String remarks = columnInfo.getRemarks();
            
            // 这里只列了几个常用类型，可根据实际情况调整
            String fieldType = new String();
            if (typeName.equalsIgnoreCase("bigint")) {
            	fieldType = "Long";
            } else if (typeName.equalsIgnoreCase("DECIMAL")) {
            	fieldType = "BigDecimal";
            } else if (typeName.equalsIgnoreCase("int")) {
            	fieldType = "Integer";
            } else {
            	fieldType = "String";
            }
            
            // field注释
            String comments = FORMAT_TAB + "/**\n" + FORMAT_TAB + " * " + remarks + FORMAT_LF + FORMAT_TAB + "*/\n"; 
            String fieldName = StringUtils.convertToLowerCaseCamel(columnName);
            
            fields.append(comments).append(FORMAT_TAB).append(MODIFIER_PRIVATE).append(SPACE).append(fieldType).append(SPACE).append(fieldName).append(";\n");
            
            // setter method
            String setterReturnType = "void";
            String setterName = PREFIX_SET + StringUtils.convertToUpperCaseCamel(columnName);
            String parameterType = fieldType;
            String parameterName = fieldName;
            String setterBody = "this." + fieldName + " = " + fieldName + ";\n";
            StringBuilder setterMethod = new StringBuilder();
            setterMethod.append(FORMAT_TAB).append(MODIFIER_PUBLIC).append(" ").append(setterReturnType).append(" ").append(setterName).append("(").append(parameterType).append(" ").append(parameterName).append(")")
            .append(" {\n").append(FORMAT_TAB).append(FORMAT_TAB).append(setterBody).append(FORMAT_TAB).append("}\n");
            
            // getter method
            String getterReturnType = fieldType;
            String getterName = PREFIX_GET + StringUtils.convertToUpperCaseCamel(columnName);
            String getterBody = "return this." + fieldName + ";\n";
            StringBuilder getterMethod = new StringBuilder();
            getterMethod.append(FORMAT_TAB).append(MODIFIER_PUBLIC).append(" ").append(getterReturnType).append(" ").append(getterName).append("()")
            .append(" {\n").append(FORMAT_TAB).append(FORMAT_TAB).append(getterBody).append(FORMAT_TAB).append("}\n");
            
            setterAndGetterMethod.append(setterMethod).append(getterMethod);
		}
		
		SQLUtils.close(state);
		SQLUtils.close(conn);
		
		// 拼接类的申明
		StringBuilder classDeclare = new StringBuilder();
		String packageStr = "package " + packageName + ";\n";
		String className = StringUtils.convertToUpperCaseCamel(tableName);
		classDeclare.append(packageStr).append(MODIFIER_PUBLIC).append(" ").append("class").append(" ").append(className);
		
		StringBuilder classBody = new StringBuilder();
		classBody.append(" {\n").append(fields).append(FORMAT_LF).append(setterAndGetterMethod).append("}");
		
		StringBuilder classStr = new StringBuilder();
		classStr.append(classDeclare).append(classBody);
		
		System.out.println(classStr.toString());
		
		String fileName = className + ".java";
		String filePath = TARGET_PATH + PATH_SEPARATOR + fileName;
		FileUtils.writeStrInFile(classStr.toString(), filePath);
	}
	
	private static void generateMapper(String namespace) {
		// resutlType
		// select
		// insert
		// delete
		// update
		
	}
	
	private static List<ColumnInfo> getColumnInfoList(ResultSet rs) {
		List<ColumnInfo> columnInfoList = new ArrayList<>();
		try {
			while (rs.next()) {
			    // 列名
				String columnName = rs.getString("COLUMN_NAME");
				 // 类型名称
		        String typeName = rs.getString("TYPE_NAME");
		        // 注释
		        String remarks = rs.getString("REMARKS");
		        
		        ColumnInfo columnInfo = new ColumnInfo();
		        columnInfo.setColumnName(columnName);
		        columnInfo.setTypeName(typeName);
		        columnInfo.setRemarks(remarks);
		        
		        columnInfoList.add(columnInfo);
			}
		} catch (SQLException e) {
			logger.error("", e);
		}
		return columnInfoList;
	}
	
	public static void main(String[] args) {
		zejinVersion();
	}
}
