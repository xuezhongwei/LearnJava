package api.dom4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Main {

	public static void main(String[] args) throws IOException {
		
		//Document doc = createXml();
		xpathXml();
	}
	/**
	 * 加载xml文件
	 */
	public static Document loadXmlFile(String file) {
		Document doc = null;
		try {
			InputStream is = new FileInputStream(file);
			SAXReader saxReader = new SAXReader();
			doc = saxReader.read(is);
		} catch (Exception e) {
			throw new RuntimeException("load xml file failed", e);
		}
		return doc;
	}
	
	/**
	 * 加载xml字符串
	 */
	public static Document loadXmlStr(String str) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(str);
		} catch (DocumentException e) {
			throw new RuntimeException("parse xml string failed.", e);
		}
		return doc;
	}
	
	/**
	 * 创建一个xml文档，并添加元素
	 * @throws IOException 
	 */
	public static Document createXml() throws IOException {
		// 获得一个Document实例
		Document doc = DocumentHelper.createDocument();
		// 设置编码方式（默认utf-8）
		doc.setXMLEncoding("utf-8");
		// 设置doctype
		doc.addDocType("root", "123", "456");
		// 为文档加个注释(可添加多个注释)
		doc.addComment("这是一个测试xml");
		// 有且只有一个根节点
		// 首先创建根节点
		Element root = doc.addElement("root");
		// 为根节点添加一个属性
		root.addAttribute("namespace", "test");
		
		// 添加两个一级子元素，并设置属性和节点内容
		Element level11 = root.addElement("select").addAttribute("id", "select11").addAttribute("parameterType", "model").addText("select * from table");
		Element level12 = root.addElement("select").addAttribute("id", "select12").addAttribute("parameterType", "model").addText("select * from table");
		
		// 添加二级子元素
		Element level21 = level11.addElement("select").addAttribute("id", "select21").addAttribute("parameterType", "model").addText("select * from table");
		// 添加cdata元素
		level21.addCDATA("1 >= 1");
		
		// 将Document转为XML字符串（没有格式化的字符串）
		System.out.println(doc.asXML());
		
		// 采用默认格式化参数（可自行设置换行、缩进等格式化参数）
		OutputFormat format = OutputFormat.createPrettyPrint();
		// 避免乱码
		format.setEncoding("utf-8");
		
		// XMLWriter可设置两个参数：输出流对象（不设置，则取默认值：System.out），格式化对象（不设置，则取默认值）
		XMLWriter xmlWriter = new XMLWriter(format);
		xmlWriter.write(doc);
		
		return doc;
	}
	
	/**
	 * 解析一个xml文档
	 */
	public static void parseXml() {
		String filePath = "pom.xml";
		Document doc = loadXmlFile(filePath);
		
		Element root = doc.getRootElement();
		loopElement(root);
	}
	
	/**
	 * 遍历元素
	 */
	public static void loopElement(Element beginElement) {
		Iterator<Element> iteator = beginElement.elementIterator();
		while(iteator.hasNext()) {
			Element element = iteator.next();
			// 获得元素的名称
			String elementName = element.getName();
			
			// 获得元素的所有属性
			List<Attribute> attributes = element.attributes();
			for (Attribute attr : attributes) {
				// 获得元素属性名和属性值
				System.out.println("name=" + attr.getName() + "value=" + attr.getValue());
			}
			
			// 获得元素的文本内容（不含子元素）
			String text = element.getText();
			System.out.println(element.getName() + "=====" + text);
			
			if ("delete".equals(elementName)) {
				// 通过父元素来删除子元素
				Element parentEle = element.getParent();
				parentEle.remove(element);
				// 注意：删除之后，需要将Document内容重新写入xml文件，这样修改才会生效
			}
			
			// 递归遍历所以元素
			loopElement(element);
		}
	}
	
	public static void xpathXml() {
		String filePath = "pom.xml";
		Document doc = loadXmlFile(filePath);
		
		List<Node> nodes = doc.selectNodes("//dependency");
		System.out.println("size=" + nodes.size());
		for (Node node : nodes) {
			Element ele = (Element)node;
			System.out.println(ele.asXML());
		}
	}
}
