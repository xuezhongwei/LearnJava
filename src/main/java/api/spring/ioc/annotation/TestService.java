package api.spring.ioc.annotation;

import org.springframework.stereotype.Service;

@Service
public class TestService implements ServiceInterface {
	@Override
	public void doSomething() {
		System.out.println("doSomething method has been called.");
	}
}
