package test;

public class TestInnerClass {
	private static int i;
	private int j;
	public class innerClass1 {
		private void test() {
			System.out.print(i);
		}
	}
	
	public static class innerClass2 {
		//public static int i;
		private void test() {
		
		}
	}
}

class UseInnerClass {
	public void test() {
		new TestInnerClass().new innerClass1();
		new TestInnerClass.innerClass2();
	}
}