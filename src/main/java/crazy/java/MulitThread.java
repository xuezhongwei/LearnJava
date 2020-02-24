package crazy.java;

public class MulitThread {

	public static void main(String[] args) {
		Printer printer = new Printer();
		PrintThread thread1 = new PrintThread();
		thread1.setPrinter(printer);
		PrintThread thread2 = new PrintThread();
		thread2.setPrinter(printer);
		thread1.start();
		thread2.start();
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
