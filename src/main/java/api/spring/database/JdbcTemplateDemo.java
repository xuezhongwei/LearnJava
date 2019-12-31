package api.spring.database;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcTemplateDemo {
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public void insert() {
		String sql = "insert into user(name, age, telephone) values (?, ? , ?)";
		Object[] args = new Object[] {"zhangsan", 90, "110"};
		int count = jdbcTemplate.update(sql, args);
	}
}
