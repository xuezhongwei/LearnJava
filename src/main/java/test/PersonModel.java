package test;

public class PersonModel {
	/**
	 * 客户名称
	 */
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "PersonModel [name=" + name + "]";
	}
	
}
