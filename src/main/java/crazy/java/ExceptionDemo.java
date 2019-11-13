package crazy.java;

public class ExceptionDemo {
	/**
	 * unchecked exception，是不需要显式处理的；而checked exception需要显式处理
	 * RuntimeException及其子类属于unchecked exception
	 * 异常转义：就是对捕获的异常进行转换
	 * （1）实际业务中，异常不能直接抛出，要抛出自定义的业务异常
	 * （2）避免系统信息被暴露
	 * （3）接口声明无异常，但实现类里有异常，此时可将异常转化为运行时异常抛出
	 * 
	 * 异常链：捕获了A异常，然后抛出B异常，那么必须把A异常作为B异常构造方法的参数。这样既实现类异常转义，同时可以保留A异常的异常信息。
	 * 否则，会丢失A异常信息，无法排查问题。
	 * 
	 */
	
	public static void main(String[] args) {
		TestExceptionInterface temp = new TestExceptionInterfaceImpl();
		temp.foo();
	}
}

class UncheckedException extends RuntimeException {
	String exceptionCode;
	public UncheckedException() {
		super();
	}
	public UncheckedException(Throwable e) {
		super(e);
	}
	public UncheckedException(String message) {
		super(message);
	}
	public UncheckedException(String message, Throwable e) {
		super(message, e);
	}
	public UncheckedException(String exceptionCode, String message) {
		super(message);
		this.exceptionCode = exceptionCode;
	}
	public UncheckedException(String exceptionCode, String message, Throwable e) {
		super(message, e);
		this.exceptionCode = exceptionCode;
	}
}

class CheckedException extends Exception {
	String exceptionCode;
	public CheckedException() {
		super();
	}
	public CheckedException(Throwable e) {
		super(e);
	}
	public CheckedException(String message) {
		super(message);
	}
	public CheckedException(String message, Throwable e) {
		super(message, e);
	}
	public CheckedException(String exceptionCode, String message) { 
		super(message);
		this.exceptionCode = exceptionCode;
	}
	public CheckedException(String exceptionCode, String message, Throwable e) {
		super(message, e);
		this.exceptionCode = exceptionCode;
	}
}

interface TestExceptionInterface {
	void foo();
}

class TestExceptionInterfaceImpl implements TestExceptionInterface {
	/*
	@Override
	public void foo() throws CheckedException {
		foo1();
	}
	*/
	@Override
	public void foo() {
		try {
			foo1();
		} catch (CheckedException e) {
			// 这里有两个细节
			// 1.异常转换
			// 2.异常链
			// throw new UncheckedException("这是一个未检测异常");
			throw new UncheckedException("这是一个未检测异常", e);
		}
	}
	
	private void foo1() throws CheckedException {
		throw new CheckedException("这是被检查的异常");
	}
}
