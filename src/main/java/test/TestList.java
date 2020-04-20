package test;

import java.util.ArrayList;

public class TestList {

	public static void main(String[] args) {
		forEach();
	}
	
	public static void forEach() {
		ArrayList<String> list1 = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();
		
		list1.add("a");
		list1.add("b");
		list1.add("c");
		
		list1.stream().forEach(item->list2.add(item));
		list2.stream().forEach(item->System.out.println(item));
	}
}
