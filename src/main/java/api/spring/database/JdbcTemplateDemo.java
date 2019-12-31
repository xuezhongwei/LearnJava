package api.spring.database;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class JdbcTemplateDemo {
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public void insert() {
		String sql = "insert into user(name, age, telephone) values (?, ?, ?)";
		Object[] args = new Object[] {"zhangsan", 90, "110"};
		// update可以执行update/delete/insert语句，底层使用preparedStatement实现
		// args为sql中占位符对应的参数
		// 返回受影响的行数
		int count = jdbcTemplate.update(sql, args);
	}
	
	public void update() {
		String sql = "update user set name = ? where id = ?";
		Object[] args = new Object[] {"lisi", 1};
		int count = jdbcTemplate.update(sql, args);
	}
	
	public void delete() {
		String sql = "delete from user where id = ? ";
		Object[] args = new Object[] {1};
		int count = jdbcTemplate.update(sql, args);
	}
	
	public void query() {
		String sql = "select * from user;";
		RowMapper<UserModel> rowMapper = new BeanPropertyRowMapper<>(UserModel.class);
		List<UserModel> list = jdbcTemplate.query(sql, rowMapper);
		for (UserModel user : list) {
			System.out.println(user.toString());
		}
	}
}
