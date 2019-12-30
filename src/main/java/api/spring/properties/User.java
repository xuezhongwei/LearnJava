package api.spring.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class User {
	@Value("${qq}")
	private String qq;
	@Value("${pwd}")
	private String pwd;
	@Value("${name}")
	private String name;
	@Value("${age}")
	private int age;
	@Override
	public String toString() {
		return "User [qq=" + qq + ", pwd=" + pwd + ", name=" + name + ", age=" + age + "]";
	}
	
}
