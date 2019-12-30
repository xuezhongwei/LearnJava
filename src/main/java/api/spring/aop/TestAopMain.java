package api.spring.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAopMain {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("api/spring/aop/beanDefinition.xml");
		Hello hello = ctx.getBean(Hello.class);
		hello.sayHello();
	}
}
