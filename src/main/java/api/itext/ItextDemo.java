package api.itext;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class ItextDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

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

	       //解决中文编码
	       ITextFontResolver fontResolver = iTextRenderer.getFontResolver();
	       if ("linux".equals(getCurrentOperationSystem())) {
	           fontResolver.addFont("/usr/share/fonts/chiness/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	       } else {
	           fontResolver.addFont("c:/Windows/Fonts/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
	       }

	       iTextRenderer.layout();
	       iTextRenderer.createPDF(os);
	       os.flush();
	       os.close();
	   }
}
