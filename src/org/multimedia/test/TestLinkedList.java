package org.multimedia.test;

import java.util.LinkedList;
import java.util.List;

public class TestLinkedList {
	
	public static void main(String[] args) {
		List<String> lst = new LinkedList<>();
		
		lst.add("Test1");
		lst.add("Test2");
		lst.add("Test3");
		
		int index = 1;
		
		System.out.println(lst);
		
		for (int i = 0; i <= index; i++)
			System.out.println(lst.get(i));
		
		for (int i = index + 1; i < lst.size(); i++)
			lst.remove(i);
		
		lst.add("Test4");
		
		System.out.println(lst);
	}
	
}