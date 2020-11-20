package design.pattern.observer;

import java.util.Observable;
import java.util.Observer;
/**
 * 观察者模式中的观察者
 * Java中通过实现Observer接口实现
 *
 */
public class TestObserver implements Observer {
	/**
	 * 当被观察者通知观察者时，实际是调用了观察者的update方法
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
