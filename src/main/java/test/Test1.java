package test;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class Test1 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		if (Integer.TYPE == int.class) {
			Class voidClazz = void.class;
		}
		System.out.println("line 1 " + Test1.class.getResource(""));
		System.out.println("line 1 " + Test1.class.getClassLoader().getResource(""));
		System.out.println("line 2 " + Test1.class.getResource("/"));
		
		
		
		  Enumeration<URL> urls =
		  Thread.currentThread().getContextClassLoader().getResources("org"); while
		  (urls.hasMoreElements()) { URL url = urls.nextElement();
		  System.out.println(url.toString()); }
		 
		 
	}

}
