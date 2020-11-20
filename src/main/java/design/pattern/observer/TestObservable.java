package design.pattern.observer;

import java.util.Observable;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
/**
 * 观察者模式
 * 存在一个被观察的类，和多个观察者类
 * 这与订阅消息类似，多个消息订阅者订阅某个消息源，当消息源有更新时，会通知消息订阅者
 * 
 * Java中通过继承Observable类，即可定义被观察者类
 */
public class TestObservable extends Observable{
	// 定义被观察者，省略了
	
	
	public static void main(String[] args) {
		TestObservable observable = new TestObservable();
		
		TestObserver observer = new TestObserver();
		// 将观察者添加的观察者队列中
		observable.addObserver(observer);
		// 设置更新标志，表示被观察者内容有更新，用于通知观察者
		observable.setChanged();
		// 通知观察者观察者队列中的所有观察者
		observable.notifyObservers();
	}
}
