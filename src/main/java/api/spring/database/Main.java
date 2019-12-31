package api.spring.database;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("api/spring/database/beanDefinition.xml");
		JdbcTemplateDemo jdbcDemo = ctx.getBean(JdbcTemplateDemo.class);
		
	}

}
