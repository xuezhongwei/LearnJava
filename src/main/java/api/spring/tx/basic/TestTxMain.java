package api.spring.tx.basic;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestTxMain {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("api/spring/tx/beanDefinition.xml");
		TestDao tdao = ctx.getBean(TestDao.class);
		tdao.insert();
		System.out.println("over !");
	}
}
