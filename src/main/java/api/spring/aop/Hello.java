package api.spring.aop;

import org.springframework.stereotype.Component;

@Component
public class Hello {
	@MyAnnotation
	public void sayHello() {
		System.out.println("say hello.");
	}
}
