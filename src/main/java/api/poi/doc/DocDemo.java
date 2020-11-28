package api.poi.doc;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hwpf.HWPFDocument;

public class DocDemo {
	
	public static void main(String[] args) {
		
	}
	
	public static void readDoc() {
		File file = new File("");
		String str = "";
		try {
			FileInputStream fis = new FileInputStream(file);
			HWPFDocument doc = new HWPFDocument(fis);
			//String doc1 = doc.get
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}


