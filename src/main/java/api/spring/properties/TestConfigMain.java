package api.spring.properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestConfigMain {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("api/spring/properties/beanDefinition.xml");
		User user = ctx.getBean(User.class);
		System.out.print(user);
	}

}
