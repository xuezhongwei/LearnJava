package api.spring.tx.basic;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TestDao {
	@Resource
	private DataSource ds;
	public void insert() throws InterruptedException {
		JdbcTemplate jt = new JdbcTemplate(ds);
		
		jt.update("insert into customer(name,contact,telephone,email,remark) values('xzw','xzw','110','example@email.com','test')");
		System.out.println("come here !");
		Thread.sleep(5000);
		throw new RuntimeException("test rollback");
	}
}
