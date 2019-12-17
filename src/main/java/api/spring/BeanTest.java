package api.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanTest {

	public static void main(String[] args) {
		System.out.println(System.getProperty("java.class.path"));
		ApplicationContext atc = new ClassPathXmlApplicationContext("springBeanDefinition.xml");
		
		Person p = atc.getBean("person", Person.class);
		Person p1 = atc.getBean("person", Person.class);
		if (p == p1) {
			System.out.println("singleton model");
		}
		p.useAxe();
	}

}
