package crazy.java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import org.openxmlformats.schemas.presentationml.x2006.main.SldDocument;

public class NServer {
	private Selector selector = null;
	static final int PORT = 30000;
	private Charset charset = Charset.forName("utf-8");
	public void init() throws IOException {
		// 通过静态方法open()获得selector对象
		selector = Selector.open();
		// 通过静态方法open()获得ServerSocketChannel实例
		ServerSocketChannel server = ServerSocketChannel.open();
		InetSocketAddress isa = new InetSocketAddress("127.0.0.1", PORT);
		// 将该ServerSocketChannel绑定到指定ip地址
		server.bind(isa);
		// 设置ServerSocketChannel以非阻塞方式工作
		server.configureBlocking(false);
		// 将ServerSocketChannel注册到指定的Selector对象
		server.register(selector, SelectionKey.OP_ACCEPT);
		// 通过Selector对象的select()方法，获得注册在该Selector对象上需要进行IO操作的SelectableChannel
		// 如果没有的话，无参数的select()方法将被阻塞。可以使用select(long timeout)或者selectNow()
		while (selector.select() > 0) {
			// 通过Selector对象的selectedKeys()方法获得注册在该Selector对象上的
			// 需要IO操作的SelectableChannel对应的的SelectionKey集合，从而获得对应的SelectableChannel对象
			for (SelectionKey sk : selector.selectedKeys()) {
				selector.selectedKeys().remove(sk);
				// 如果sk对应的SelectableChannel包含客户端的链接请求
				if (sk.isAcceptable()) {
					SocketChannel sc = server.accept();
					sc.configureBlocking(false);
					// 要设置SocketChannel支持的操作为 读
					sc.register(selector, SelectionKey.OP_READ);
					//System.out.println("current option = " + sk.readyOps());
					//sk.interestOps(SelectionKey.OP_ACCEPT);
				}
				if (sk.isReadable()) {
					SocketChannel sc = (SocketChannel) sk.channel();
					ByteBuffer buff = ByteBuffer.allocate(1024);
					String content = "";
					try {
						while (sc.read(buff) > 0) {
							buff.flip();
							content += charset.decode(buff);
							buff.clear();
						}
						System.out.println("读取的数据=" + content);
						sk.interestOps(SelectionKey.OP_READ);
					} catch (IOException e) {
						// 从Selector中删除指定的SelectionKey
						sk.cancel();
						if (sk.channel() != null) {
							sk.channel().close();
						}
					}
					if (content.length() > 0) {
						for (SelectionKey key : selector.keys()) {
							Channel targetChannel = key.channel();
							if (targetChannel instanceof SocketChannel) {
								// 将读到的内容写入该Channel中
								SocketChannel dest = (SocketChannel)targetChannel;
								dest.write(charset.encode(content));
							}
						}
					}
				}
			}
		}
	}
	public static void main(String[] args) throws IOException {
		new NServer().init();
	}
}
