package demo;

import org.junit.Before;
import org.junit.Test;

public class JunitDemo {
	@Before
	public void init() {
		System.out.println("this is init method.");
	}
	
	@Test
	public void test() {
		System.out.println("this is test");
	}
	
	@Test
	public void test1() {
		System.out.println("this is test1");
	}
}
