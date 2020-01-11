package api.spring.tx.programtx;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class Main {
	// 测试编程式事务
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("api/spring/tx/programtx/beanDefinition.xml");
		JdbcTemplateDemo jdbcDemo = ctx.getBean(JdbcTemplateDemo.class);
		// 使用默认的事务定义类
		TransactionDefinition transactionDef = new DefaultTransactionDefinition();
		// 获得事务管理器
		PlatformTransactionManager transactionManager = (PlatformTransactionManager)ctx.getBean("transactionManager");
		// 开启事务
		TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDef);
		try {
			jdbcDemo.insert();
			// 提交事务
			System.out.println("transaction commit.");
			transactionManager.commit(transactionStatus);
		} catch (Exception e) {
			// 回滚事务
			System.out.println("transaction roll back.");
			transactionManager.rollback(transactionStatus);
		}
		System.out.println("program over.");
	}
}
