
package api.spring.ioc.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanTest {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("api/spring/ioc/beanDefinition.xml");
		System.out.println("获取bean之前");
		Person p = ctx.getBean(Person.class);
		p.useAxe();
		System.out.println(p.toString());
		
		DataSource ds = (DataSource)ctx.getBean("dataSource");
		System.out.println(ds.toString());
	}

}
