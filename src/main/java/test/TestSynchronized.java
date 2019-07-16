package test;

public class TestSynchronized {
	 public void method1() {
		 synchronized(this) {
			 try {
				 System.out.println(Thread.currentThread().getName() + "do method1");
				 Thread.sleep(2000);
			 } catch (InterruptedException e) {
				 e.printStackTrace();
			 }
		 }
	 }
	 
	 public void method2() {
		 synchronized(TestSynchronized.class) {
			 try {
				 System.out.println(Thread.currentThread().getName() + "do method2");
				 Thread.sleep(2000);
			 }  catch (InterruptedException e) {
				 e.printStackTrace();
			 }
		 }
	 }
	 
	 private Object lock = new Object();
	 public void method3() {
		 synchronized(lock) {
			 try {
				 System.out.println(Thread.currentThread().getName() + " do method3");
				 Thread.sleep(2000);
			 }  catch (InterruptedException e) {
				 e.printStackTrace();
			 }
		 }
	 }
	 
	 public static void main(String[] args) {
		 TestSynchronized objLock = new TestSynchronized();
		 Thread t1 = new Thread(new Runnable() {
				@Override
				public void run() {
					objLock.method2();
				}
			});
		 Thread t2 = new Thread(new Runnable() {
				@Override
				public void run() {
					objLock.method2();
				}
			});
		 t1.start();
		 t2.start();
	 }
}
