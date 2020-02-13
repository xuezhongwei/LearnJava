package my.utils;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
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
	
	public static final String SELECT_ID = "basicSelect";
	public static final String INSERT_ID = "basicInsert";
	public static final String UPDATE_ID = "basicUpdate";
	public static final String DELETE_ID = "basicDelete";
	
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
			generateMapper("", packageName, tableName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void generateModel(String packageName, String tableName) throws SQLException {
		// getColums最一般的用法
		// getColums(null, null, tableName, "%") 获得某个表的所有字段信息
		StringBuilder fields = new StringBuilder();
		StringBuilder setterAndGetterMethod = new StringBuilder();
		
		List<ColumnInfo> columnInfoList = getColumnInfoList(tableName);
		
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
	
	private static void generateMapper(String namespace, String packageName, String tableName) {
		// resutlMap
		// select
		// insert
		// delete
		// update
		List<ColumnInfo> columnList = getColumnInfoList(tableName);
		// 获得一个Document实例
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding("utf-8");
		// 设置doctype
		String publicId= "-//mybatis.org//DTD mapper 3.0//EN";
		String systemId = "http://mybatis.org/dtd/mybatis-3-mapper.dtd";
		doc.addDocType("mapper", publicId, systemId);
		
		// 添加根节点mapper
		Element root = doc.addElement("mapper");
		root.addAttribute("namespace", namespace);
		
		// 创建resultMap元素
		Element resultMap = generateResultMap(packageName, tableName, columnList);
		root.add(resultMap);
		
		// 创建select元素
		Element select = generateSelect(packageName, tableName, columnList);
		root.add(select);
		
		// 创建insert元素
		Element insert = generateInsert(packageName, tableName, columnList);
		root.add(insert);
		
		// 创建delete元素
		Element delete = generateDelete(packageName, tableName, columnList);
		root.add(delete);
		
		// 创建update元素
		Element update = generateUpdate(packageName, tableName, columnList);
		root.add(update);
		
		//printDoc(doc);
		String fileName = tableName + "Mapper.xml";
		String filePath = TARGET_PATH + PATH_SEPARATOR + fileName;
		writeDoc(doc, filePath);
	}
	
	private static List<ColumnInfo> getColumnInfoList(String tableName) {
		List<ColumnInfo> columnInfoList = new ArrayList<>();
		try {
			Connection conn = SQLUtils.getConnection();
			Statement state = conn.createStatement();
			DatabaseMetaData dbmd = conn.getMetaData();
			ResultSet rs = dbmd.getColumns(null, null, tableName, "%");
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
			SQLUtils.close(state);
			SQLUtils.close(conn);
		} catch (SQLException e) {
			logger.error("", e);
		}
		return columnInfoList;
	}
	
	private static Element generateResultMap(String packageName, String tableName, List<ColumnInfo> columnList) {
		Element resultMap = DocumentHelper.createElement("resultMap");
		resultMap.addAttribute("id", StringUtils.convertToLowerCaseCamel(tableName));
		resultMap.addAttribute("type", packageName + "." + StringUtils.convertToUpperCaseCamel(tableName));
		
		for (ColumnInfo column : columnList) {
			Element result = DocumentHelper.createElement("result");
			result.addAttribute("column", column.getColumnName()).addAttribute("property", StringUtils.convertToLowerCaseCamel(column.getColumnName()));
			resultMap.add(result);
		}
		return resultMap;
	}
	
	private static Element generateSelect(String packageName, String tableName, List<ColumnInfo> columnList) {
		Element select = DocumentHelper.createElement("select");
		select.addAttribute("id", SELECT_ID);
		select.addAttribute("parameterType", packageName + "." + StringUtils.convertToUpperCaseCamel(tableName));
		select.addAttribute("resultMap", StringUtils.convertToLowerCaseCamel(tableName));
		
		String sql = "select * from " + tableName + " where 1 = 1 ";
		select.addText(sql);
		
		for (ColumnInfo column : columnList) {
			String field = StringUtils.convertToLowerCaseCamel(column.getColumnName());
			Element ifEle = DocumentHelper.createElement("if");
			String test = field + " != null and " + field + " != ''";
			ifEle.addAttribute("test", test);
			String condition = " and " + column.getColumnName() + " = #{" + field + "}";
			ifEle.addText(condition);
			
			select.add(ifEle);
		}
		return select;
	}
	
	private static Element generateInsert(String packageName, String tableName, List<ColumnInfo> columnList) {
		Element insert = DocumentHelper.createElement("insert");
		insert.addAttribute("id", INSERT_ID);
		insert.addAttribute("parameterType", packageName + "." + StringUtils.convertToUpperCaseCamel(tableName));
		
		String sql = "insert into " + tableName ;
		
		StringBuilder columns = new StringBuilder();
		StringBuilder fields = new StringBuilder();
		for (ColumnInfo column : columnList) {
			String field = StringUtils.convertToLowerCaseCamel(column.getColumnName());
			columns.append(column.getColumnName()).append(",");
			fields.append("#{").append(field).append("}").append(",");
		}
		String temp = columns.substring(0, columns.lastIndexOf(","));
		String temp1 = fields.substring(0, fields.lastIndexOf(","));
		sql = sql + "(" + temp + ") values (" + temp1 + ")";
		insert.addText(sql);
		return insert;
	}
	
	private static Element generateDelete(String packageName, String tableName, List<ColumnInfo> columnList) {
		Element delete = DocumentHelper.createElement("delete");
		delete.addAttribute("id", DELETE_ID);
		delete.addAttribute("parameterType", packageName + "." + StringUtils.convertToUpperCaseCamel(tableName));
		
		String sql = "delete from " + tableName + " where id = #{id}";
		delete.addText(sql);
		return delete;
	}
	
	private static Element generateUpdate(String packageName, String tableName, List<ColumnInfo> columnList) {
		Element update = DocumentHelper.createElement("update");
		update.addAttribute("id", UPDATE_ID);
		update.addAttribute("parameterType", packageName + "." + StringUtils.convertToUpperCaseCamel(tableName));
		
		String sql = "update " + tableName + " set ";
		
		StringBuilder sets = new StringBuilder();
		for (ColumnInfo column : columnList) {
			String field = "#{" + StringUtils.convertToLowerCaseCamel(column.getColumnName()) + "}";
			sets.append(column.getColumnName()).append(" = ").append(field).append(", ");
		}
		String temp = sets.substring(0, sets.lastIndexOf(","));
		sql = sql  + temp + " where id = #{id}";
		update.addText(sql);
		return update;
	}
	/**
	 * 将Document内容打印到控制台
	 */
	private static void printDoc(Document doc) {
		// 采用默认格式化参数（可自行设置换行、缩进等格式化参数）
		OutputFormat format = OutputFormat.createPrettyPrint();
		// 避免乱码
		format.setEncoding("utf-8");
		
		// XMLWriter可设置两个参数：输出流对象（不设置，则取默认值：System.out），格式化对象（不设置，则取默认值）
		try {
			XMLWriter xmlWriter = new XMLWriter(format);
			xmlWriter.write(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 将Document内容写入到指定文件中
	 */
	private static void writeDoc(Document doc, String filePath) {
		// 采用默认格式化参数（可自行设置换行、缩进等格式化参数）
		OutputFormat format = OutputFormat.createPrettyPrint();
		// 避免乱码
		format.setEncoding("utf-8");
		
		// XMLWriter可设置两个参数：输出流对象（不设置，则取默认值：System.out），格式化对象（不设置，则取默认值）
		try {
			OutputStream os = new FileOutputStream(filePath);
			XMLWriter xmlWriter = new XMLWriter(os, format);
			xmlWriter.write(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		zejinVersion();
	}
}
