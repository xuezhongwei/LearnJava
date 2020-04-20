package clazz.usage;

import java.nio.CharBuffer;

public class BufferDemo {
	public static void main(String[] args) {
		// 当使用put()和get()来访问Buffer中的数据时，有相对和绝对两种
		// 相对：无参数的put()和get()，从当前position处开始读或者写，然后position的值会按处理元素的个数自动增加
		// 绝对：带参数的put(int index)和get(int index)，从指定位置读或者写，position的值不变
		CharBuffer buff = CharBuffer.allocate(8);
		System.out.println("capacity:" + buff.capacity());
		System.out.println("limit:" + buff.limit());
		System.out.println("position:" + buff.position());
		
		// 放入元素
		buff.put('a');
		buff.put('b');
		buff.put('c');
		
		System.out.println("after add three elements, position = " + buff.position());
		
		// 调用flip，进入读模式
		// 所谓读模式，就是position=0，limit=最后一个元素的下一个位置
		buff.flip();
		System.out.println("after execute flip method, limit = " + buff.limit());
		System.out.println("after execute flip method, position = " + buff.position());
		
		// 取出第一个元素
		System.out.println("1st element ,position = 0, value= " + buff.get());
		System.out.println("new position = " + buff.position());
		
		// 调用clear，进入写模式
		// 所谓写模式，就是position=0，limit=capacity
		buff.clear();
		System.out.println("after execute clear method, limit = " + buff.limit());
		System.out.println("after execute clear method, position = " + buff.position());
		// 缓冲区内数据并没有被清除，此时读取内容
		System.out.println("after execute clear method, 3st position value = " + buff.get());
		System.out.println("new position = " + buff.position());
	}
}
