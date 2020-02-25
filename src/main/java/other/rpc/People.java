package other.rpc;

import java.io.Serializable;

public class People implements Serializable{
	private static final long serialVersionUID = 1L;
	private int a;
	private int b;
	public People(int a, int b) {
		this.a = a;
		this.b = b;
	}
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}
	
}
