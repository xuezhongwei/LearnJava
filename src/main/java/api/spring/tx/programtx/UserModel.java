package api.spring.tx.programtx;

public class UserModel {
	int id;
	String name;
	int age;
	String telephone;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@Override
	public String toString() {
		return "UserModel [id=" + id + ", name=" + name + ", age=" + age + ", telephone=" + telephone + "]";
	}
	
}
