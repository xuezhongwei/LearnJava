package api.spring.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanTest {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("api/spring/service/beanDefinition.xml");
		System.out.println("获取bean之前");
		Person p = ctx.getBean(Person.class);
		p.useAxe();
	}

}
