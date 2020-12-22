package crazy.java;

public class ThreadDemo {
	
	public static void main(String[] args) throws InterruptedException {
		Printer printer = new Printer();
		PrintThread print1 = new PrintThread(printer, "print1");
		print1.start();
		PrintThread print2 = new PrintThread(printer, "print2");
		print2.start();
		Thread.sleep(5000);
		System.out.println("----------");
	}
	
	static class Printer {
		int i = 0;
		
		public void print() {
			System.out.println(Thread.currentThread().getName() + " print " + i);
			i++;
		}
	}
	
	static class PrintThread extends Thread {
		private Printer printer;
		@Override
		public void run() {
			for (int index = 0; index < 100; index++) {
				synchronized (printer) {
					printer.notify();
					printer.print();
					try {
						printer.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		public PrintThread(Printer printer, String name) {
			super(name);
			this.printer = printer;
		}
	}
	
}
