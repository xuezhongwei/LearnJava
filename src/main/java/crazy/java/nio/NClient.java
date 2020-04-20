package crazy.java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

public class NClient {
	private Selector selector = null;
	static final int PORT = 30000;
	private Charset charset = Charset.forName("utf-8");
	private SocketChannel sc = null;
	public void init() throws IOException {
		// 通过静态方法open()获得selector对象
		selector = Selector.open();
		InetSocketAddress isa = new InetSocketAddress("127.0.0.1", PORT);
		// 通过静态方法open()获得ServerSocketChannel实例
		sc = SocketChannel.open(isa);
		// 设置ServerSocketChannel以非阻塞方式工作
		sc.configureBlocking(false);
		// 将ServerSocketChannel注册到指定的Selector对象
		sc.register(selector, SelectionKey.OP_READ);
		
		new ClientThread().start();
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			sc.write(charset.encode(line));
		}
	}
	
	private class ClientThread extends Thread {
		public void run() {
			try {
				while (selector.select() > 0) {
					for (SelectionKey sk : selector.selectedKeys()) {
						selector.selectedKeys().remove(sk);
						if (sk.isReadable()) {
							SocketChannel sc = (SocketChannel)sk.channel();
							ByteBuffer buff = ByteBuffer.allocate(1024);
							String content = "";
							while (sc.read(buff) > 0) {
								sc.read(buff);
								buff.flip();
								content += charset.decode(buff);
								System.out.println("聊天信息:" + content);
								sk.interestOps(SelectionKey.OP_READ);
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		new NClient().init();
	}
}
