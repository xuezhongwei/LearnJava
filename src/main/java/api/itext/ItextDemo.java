package api.itext;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontProvider;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class ItextDemo {

	public static void main(String[] args) throws Exception {
		test5();

	}
	
	// 最简单应用
	public static void test1() throws Exception {
		// 新建document对象，代表一个PDF文档对象
		Document document = new Document();
		
		// 建立一个pdfwriter与document对象关联，通过pdfwriter可以将文档写入到磁盘中
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("./file/test1.pdf"));
		
		// 打开pdf文档
		document.open();
		
		// 通过document的add方法，往pdf文档中添加内容
		document.add(new Paragraph("Hello world!"));
		
		// 关闭文档
		document.close();
	}
	
	// 添加文档属性
	public static void test2() throws Exception {
		// 创建文件
        Document document = new Document();
        // 建立一个书写器
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("./file/test2.pdf"));
        // 打开文件
        document.open();
        // 添加内容
        document.add(new Paragraph("Some content here"));
     
        // 设置属性
        // 标题
        document.addTitle("this is a title");
        // 作者
        document.addAuthor("H__D");
        // 主题
        document.addSubject("this is subject");
        // 关键字
        document.addKeywords("Keywords");
        // 创建时间
        document.addCreationDate();
        // 应用程序
        document.addCreator("hd.com");
        
        // 关闭文档
        document.close();
	}
	
	// pdf文档中添加图片
	public static void test3() throws Exception{
		// 创建文件
        Document document = new Document();
        // 建立一个书写器
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("./file/test3.pdf"));
        // 打开文件
        document.open();
        // 添加内容
        document.add(new Paragraph("HD content here"));
     
        // 读取图片
        Image image1 = Image.getInstance("./file/smile.JPG");
        // 设置图片位置的x轴和y周
        // 如果不设置，默认0,0
        image1.setAbsolutePosition(0f, 550f);
        // 设置图片的宽度和高度
        image1.scaleAbsolute(200, 200);
        // 将图片1添加到pdf文件中
        document.add(image1);
     
        // 图片2
        Image image2 = Image.getInstance("./file/cutegirl.gif");
        // 将图片2添加到pdf文件中
        document.add(image2);
        
        //关闭文档
        document.close();
        //关闭书写器
        writer.close();
	}
	
	// 在pdf文档中添加列表
	public static void test4() throws Exception {
		// 创建文件
        Document document = new Document();
        // 建立一个书写器
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("./file/test4.pdf"));
        // 打开文件
        document.open();
        // 添加内容
        document.add(new Paragraph("HD content here"));
     
        //添加有序列表
        List orderedList = new List(List.ORDERED);
        orderedList.add(new ListItem("Item one"));
        orderedList.add(new ListItem("Item two"));
        orderedList.add(new ListItem("Item three"));
        document.add(orderedList);

        //关闭文档
        document.close();
	}
	
	// 为PDF文档设置密码
	public static void test5() throws Exception {
		// 创建文件
        Document document = new Document();
        // 建立一个书写器
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("./file/test5.pdf"));

        // 用户密码
        String userPassword = "123456";
        // 拥有者密码
        String ownerPassword = "hd";
        // 设置加密
        writer.setEncryption(userPassword.getBytes(), ownerPassword.getBytes(), PdfWriter.ALLOW_PRINTING,
                PdfWriter.ENCRYPTION_AES_128);

        // 打开文件
        document.open();
        
        //添加内容
        document.add(new Paragraph("test password !!!!"));

        // 关闭文档
        document.close();
	}
	
	// 读取、修改pdf
	public static void test6() throws Exception {
		// 读取pdf文件
        PdfReader pdfReader = new PdfReader("C:/Users/H__D/Desktop/test1.pdf");
     
        // 修改器
        PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream("C:/Users/H__D/Desktop/test10.pdf"));
     
        Image image = Image.getInstance("C:/Users/H__D/Desktop/IMG_0109.JPG");
        image.scaleAbsolute(50, 50);
        image.setAbsolutePosition(0, 700);
     
        for(int i=1; i<= pdfReader.getNumberOfPages(); i++)
        {
            PdfContentByte content = pdfStamper.getUnderContent(i);
            content.addImage(image);
        }
     
        pdfStamper.close();
	}
	
	
	
	
	
	
	
	

	/**
	 * 用于HTML直接转换为PDF文件
	 *
	 * @param fileName
	 * @throws Exception
	 */
	public static void parseHTML2PDFFile(String pdfFile, InputStream htmlFileStream) throws Exception {
 
		BaseFont bfCN = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H",
				false);
		// 中文字体定义
		Font chFont = new Font(bfCN, 12, Font.NORMAL, BaseColor.BLUE);
		Font secFont = new Font(bfCN, 12, Font.NORMAL, new BaseColor(0, 204,
				255));
 
		Document document = new Document();
		PdfWriter pdfwriter = PdfWriter.getInstance(document,
				new FileOutputStream(pdfFile));
		pdfwriter.setViewerPreferences(PdfWriter.HideToolbar);
		document.open();
 
		int chNum = 1;
		Chapter chapter = new Chapter(new Paragraph("HTML文件转PDF测试", chFont),
				chNum++);
 
		Section section = chapter.addSection(new Paragraph("/dev/null 2&gt;&amp;1 详解",
				secFont));
		// section.setNumberDepth(2);
		// section.setBookmarkTitle("基本信息");
		section.setIndentation(10);
		section.setIndentationLeft(10);
		section.setBookmarkOpen(false);
		section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
		section.add(Chunk.NEWLINE);
		document.add(chapter);
 
		// html文件
		InputStreamReader isr = new InputStreamReader(htmlFileStream, "UTF-8");
 
		// 方法一：默认参数转换
		XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document, isr);
 
		// 方法二：可以自定义参数
		// HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
		// htmlContext.charSet(Charset.forName("UTF-8"));
		// htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
		// CSSResolver cssResolver = XMLWorkerHelper.getInstance()
		// .getDefaultCssResolver(true);
		// Pipeline&lt;?&gt; pipeline = new CssResolverPipeline(cssResolver,
		// new HtmlPipeline(htmlContext, new PdfWriterPipeline(document,
		// pdfwriter)));
		// XMLWorker worker = new XMLWorker(pipeline, true);
		// XMLParser p = new XMLParser();
		// p.addListener(worker);
		//
		// p.parse(isr);
		// p.flush();
 
		document.close();
	}	
	
	/**
    * html转换成PDF
    * @param htmlFile html文件
    * @param pdfPath  pdf路径
    * @throws Exception 异常
    */
   public static void htmlToPdf(File htmlFile, String pdfPath) throws Exception {

       OutputStream os = new FileOutputStream(pdfPath);
       ITextRenderer iTextRenderer = new ITextRenderer();
       iTextRenderer.setDocument(htmlFile);
       
       String currentOS = System.getProperty("os.name").toLowerCase();
       //解决中文编码
       ITextFontResolver fontResolver = iTextRenderer.getFontResolver();
       if ("linux".equals(currentOS)) {
           fontResolver.addFont("/usr/share/fonts/chiness/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
       } else {
           fontResolver.addFont("c:/Windows/Fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
       }

       iTextRenderer.layout();
       iTextRenderer.createPDF(os);
       os.flush();
       os.close();
   }
   
   public static ByteArrayOutputStream build(String text) throws Exception {
	   ByteArrayOutputStream os = new ByteArrayOutputStream();
	   Document document = new Document();
	   PdfWriter mPdfWriter = PdfWriter.getInstance(document, os);
	   document.open();
	   ByteArrayInputStream bin = new ByteArrayInputStream(text.getBytes());
	   XMLWorkerHelper.getInstance().parseXHtml(mPdfWriter, document, bin, StandardCharsets.UTF_8, new ChinaFontProvide());
	   document.close();
	   mPdfWriter.close();
	   return os;
   }
   
   public static final class ChinaFontProvide implements FontProvider {
		@Override
		public boolean isRegistered(String fontname) {
			return false;
		}
		
		@Override
		public Font getFont(String fontname, String encoding, boolean embedded, float size, int style, BaseColor color) {
			BaseFont bfChinese = null;
			try {
				bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			} catch (Exception e) {
				// TODO
			}
			return new Font(bfChinese, 12, Font.NORMAL);
		}
   }
   
   // 为什么下边这种方式生成不了PDF
   public static void test() throws DocumentException, IOException {
	   String outFilePath = "./file/html.pdf";
	   Document document = new Document();
	   PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(outFilePath));
	   document.open();
	   
	   FileReader isr = new FileReader("./file/html.html");
	   BufferedReader br = new BufferedReader(isr);
	   System.out.println(br.readLine());
	   
	   XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, isr);
	   
   }
}
