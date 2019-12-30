package api.spring.ioc.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestIocAnnotationMain {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("api/spring/ioc/annotation/beanDefinition.xml");
		TestController testController = (TestController)context.getBean("testController");
		testController.doSomething();
	}
}
