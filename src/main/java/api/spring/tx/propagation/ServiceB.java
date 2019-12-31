package api.spring.tx.propagation;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceB {
	@Resource
	private JdbcTemplate jdbTemplate;
	
	@Transactional
	public void testPropagationRequired() {
		
	}
}
