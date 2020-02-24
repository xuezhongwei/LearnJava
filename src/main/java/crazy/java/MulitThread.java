package crazy.java;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MulitThread {

	public static void main(String[] args) {
		/*
		 * Printer printer = new Printer(); PrintThread thread1 = new PrintThread();
		 * thread1.setPrinter(printer); PrintThread thread2 = new PrintThread();
		 * thread2.setPrinter(printer); thread1.start(); thread2.start();
		 */
		
		
		Telephone telephone = new Telephone();
		PhoneThread thread3 = new PhoneThread();
		thread3.setName("Thread-3");
		thread3.setTelephone(telephone);
		PhoneThread thread4 = new PhoneThread();
		thread4.setName("Thread-4");
		thread4.setTelephone(telephone);
		thread3.start();
		thread4.start();
	}
}

class PrintThread extends Thread {
	private Printer printer;
	
	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			try {
				printer.print();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setPrinter(Printer printer) {
		this.printer = printer;
	}
	
}

class PhoneThread extends Thread {
	private Telephone telephone;
	
	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			try {
				telephone.call();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void setTelephone(Telephone telephone) {
		this.telephone = telephone;
	}
}

class Printer {
	private int printFlag = 0;
	// 线程通信，达到两个线程交替执行的目的
	synchronized public void print() throws InterruptedException {
		String threadName = Thread.currentThread().getName();
		
		if (threadName.equals("Thread-1") && printFlag == 1
				|| threadName.equals("Thread-0") && printFlag == 0) {
			printFlag = (printFlag + 1) % 2; 
			System.out.println(threadName);
			// 通知在当前同步监视器上的线程
			notifyAll();
		} else {
			// 释放当前线程的同步监视器
			wait();
		}
	}
}

class Telephone {
	private final Lock lock = new ReentrantLock();
	private final Condition condition = lock.newCondition();
	
	private int printFlag = 0;
	// 线程通信，达到两个线程交替执行的目的
	public void call() throws InterruptedException {
		lock.lock(); 
		try {
			String threadName = Thread.currentThread().getName();
			
			if (threadName.equals("Thread-3") && printFlag == 1
					|| threadName.equals("Thread-4") && printFlag == 0) {
				printFlag = (printFlag + 1) % 2; 
				System.out.println(threadName);
				// 通知其他线程
				condition.signalAll();
			} else {
				// 释放
				condition.await();
			}
		} finally {
			lock.unlock();
		}
	}
}
