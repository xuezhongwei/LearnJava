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
	 * 不能使用异常作为流程控制的方式
	 * 系统创建和处理异常是非常消耗资源的
	 * 
	 * 执行try块或者catch块中代码遇到return或者throw语句时，会先停下来，检查是否有finally块，如果有则先执行finally块，然后再回来执行return或者throw块
	 * 如果finally块中有return或者throw，则try块或catch块中的return或throw将不会被执行
	 * 
	 * throw有显式的和隐式的
	 * 隐式的就是出现异常之后，系统自动创建并throw一个异常
	 * 
	 */
	
	public static void main(String[] args) {
		TestExceptionInterfaceImpl temp = new TestExceptionInterfaceImpl();
		// temp.foo();
		//System.out.println(temp.foo5());
		temp.foo6();
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
		} finally {
			System.out.println("this is finally block");
		}
	}
	
	private void foo1() throws CheckedException {
		throw new CheckedException("这是被检查的异常");
	}
	
	private void foo2() {
		try {
			throw new CheckedException();
		} catch(CheckedException e) {
			// 如果catch一个根本不会抛出的checked exception，会报错
		}
	}
	
	public void foo3() {
		try {
			foo1();
		} catch (CheckedException e) {
			throw new UncheckedException("这是一个未检测异常", e);
		} finally {
			System.out.println("this is finally block");
			return;
		}
	}
	
	public String foo4() {
		try {
			foo1();
			return "here is try block";
		} catch (CheckedException e) {
			throw new UncheckedException("这是一个未检测异常", e);
		} finally {
			return "here is finally block";
		}
	}
	
	public String foo5() {
		String ret;
		try {
			ret = "here is try block";
			return ret;
		} finally {
			// 这里的修改并不会影响返回值
			// 挺诡异，不是很好理解
			ret = "here is finally block";
		}
	}
	
	/**
	 * test try-with-resource
	 * 
	 * 不是太理解这种语法究竟是怎么实现的
	 * 但这并不影响工作中的使用
	 */
	public void foo6() {
		try(AutoCloseResource resource = new AutoCloseResource()) {
			resource.read();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
	}
}

class AutoCloseResource implements AutoCloseable {
	public void read() {
		System.out.println("读取资源成功！");
	}
	@Override
	public void close() throws Exception {
		System.out.println(System.currentTimeMillis() + ": current resource is closed." );
		throw new RuntimeException("come here");
	}
	
}
