package api.spring.ioc.annotation;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

@Controller
public class TestController {
	@Resource
	ServiceInterface testServiceImpl;
	
	public void doSomething() {
		System.out.println("haha");
		testServiceImpl.doSomething();
	}
	@PostConstruct
	private void init() {
		System.out.println("controller de init method");
	}
	@PreDestroy
	private void destroy() {
		System.out.println("controller de destroy method");
	}
}
