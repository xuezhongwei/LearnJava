package api.spring.service;

public class Chinese implements Person {
	private Axe axe;
	@Override
	public void useAxe() {
		System.out.println(axe.chop());
	}
	
	public void setAxe(Axe axe) {
		this.axe = axe;
		System.out.println("正在调用setter方法");
	}
}
