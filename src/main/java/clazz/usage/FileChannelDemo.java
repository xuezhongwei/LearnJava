package clazz.usage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo {

	public static void main(String[] args) throws Exception {
		// 虽然FileChannel既可以读，也可以写，但FileInputStream获取的FileChannel只能读，FileOutputStream获得FileChannel只能写
		
		File f = new File("pom.xml");
		FileChannel inChannel = new FileInputStream(f).getChannel();
		
		FileChannel outChannel = new FileOutputStream("a.txt").getChannel();
		// map()方法将Channel对应的部分或全部数据映射成Buffer，其实就是将数据加载到内存中了
		MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, f.length());
		System.out.println(buffer.toString());
		
		// 分多次读取文件内容
		FileChannel outChannel2 = new FileOutputStream("b.txt").getChannel();
		ByteBuffer byteBuffer = ByteBuffer.allocate(100);
		while (inChannel.read(byteBuffer) != -1) {
			// 一定要先flip，锁定Buffer的空白区
			byteBuffer.flip();
			outChannel2.write(byteBuffer);
			// 将Buffer初始化，为下次读取数据做准备
			byteBuffer.clear();
		}
	}

}
