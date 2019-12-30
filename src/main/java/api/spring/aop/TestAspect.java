package api.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
public class TestAspect {
	//@Before("execution(* api.spring.aop.*.*(..))")
	public void beforeAdvice(JoinPoint jp) {
		System.out.println("before advice method.");
	}
	
	//@AfterReturning(returning = "ret", pointcut="execution(* api.spring.aop.*.*(..))")
	public void afterReturningAdvice(Object ret) {
		System.out.println("after returning advice method.");
	}
	
	//@AfterThrowing(throwing="exception",pointcut="execution(* api.spring.aop.*.*(..))")
	public void afterThrowingAdvice(Throwable exception) {
		System.out.println("after throwing advice method.");
	}
	
	//@Pointcut("execution(* api.spring.aop.*.*(..))")
	public void pointcut() {
		System.out.println("this is point cut");
	}
	
	//@After("annotationpointcut()")
	public void afterAdvice() {
		System.out.println("after advice method.");
	}
	
	//@Pointcut("@annotation(api.spring.aop.MyAnnotation)")
	public void annotationpointcut() {
	}
}
