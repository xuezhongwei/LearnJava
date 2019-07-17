package test;

public class Test {
	boolean flag;
	String iPhone;
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getiPhone() {
		return iPhone;
	}
	public void setiPhone(String iPhone) {
		this.iPhone = iPhone;
	}
	public static void main(String[] args) {
		System.out.println("hello world");
		test();
	}
	public static void test() {
		System.out.println("I am very happy tonight.");
	}
}
