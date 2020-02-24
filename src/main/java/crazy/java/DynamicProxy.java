package crazy.java;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;

public class DynamicProxy {
	public static void main(String[] args) {
		InvocationHandler invocationHandler = new HouseProxyInvocationHandler();
		User user = (User)Proxy.newProxyInstance(DynamicProxy.class.getClassLoader(), new Class[] {User.class}, invocationHandler);
		user.buyHouse("beijin,china", new BigDecimal(8888));
		
		HouseProxyInvocationHandler1 invocationHandler1 = new HouseProxyInvocationHandler1();
		User someone = new Someone();
		invocationHandler1.setTarget(someone);
		
		User user1 = (User)Proxy.newProxyInstance(DynamicProxy.class.getClassLoader(), new Class[] {User.class}, invocationHandler1);
		user1.buyHouse("shiyan, hubei", new BigDecimal(100));
	}
}

interface User {
	String buyHouse(String houseAddress, BigDecimal price);
	String sellHouse(String houseAddress, BigDecimal price);
}



class HouseProxyInvocationHandler implements InvocationHandler {
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		publishInfo();
		
		System.out.println("target method is " + method.getName() + " and params is " + joinStr(args));
		
		handleProcedure();
		return null;
	}
	
	private void publishInfo() {
		System.out.println("someone need buy house or sell house?");
	}
	
	private void handleProcedure() {
		System.out.println("handle some procedure.");
	}
	
	private String joinStr(Object[] args) {
		StringBuilder sb = new StringBuilder();
		if (args != null) {
			for (Object obj : args) {
				sb.append(obj.toString());
			}
		}
		return sb.toString();
	}
}

class Someone implements User {
	@Override
	public String buyHouse(String houseAddress, BigDecimal price) {
		String ret = "I want buy a house at " + houseAddress + " and price is " + price;
		System.out.println(ret);
		return ret;
	}

	@Override
	public String sellHouse(String houseAddress, BigDecimal price) {
		String ret = "I sell my house at " + houseAddress + " and price is " + price + ". I am very happy!";
		System.out.println(ret);
		return ret;
	}
	
}

class HouseProxyInvocationHandler1 implements InvocationHandler {
	private User target;
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		publishInfo();
		Object ret = method.invoke(target, args);
		handleProcedure();
		return null;
	}
	
	private void publishInfo() {
		System.out.println("someone need buy house or sell house?");
	}
	
	private void handleProcedure() {
		System.out.println("handle some procedure.");
	}
	
	public void setTarget(User user) {
		this.target = user;
	}
}