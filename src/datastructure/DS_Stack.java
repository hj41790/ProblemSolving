package datastructure;

import java.util.Stack;

public class DS_Stack {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Stack<Integer> s = new Stack<Integer>();
		s.add(1);
		s.add(2);
		s.add(3);
		s.add(4);
		s.add(5);

		while(!s.isEmpty()) {
			System.out.println(s.pop());
		}
	}

}
