package datastructure;

import java.util.PriorityQueue;

public class DS_PriorityQueue {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		
		pq.add(10);
		pq.add(20);
		pq.add(30);
		pq.add(15);
		pq.add(5);
		pq.add(0);
		
		while(!pq.isEmpty()) {
			System.out.println(pq.poll());
		}
		
		
		PriorityQueue<Test> pq2 = new PriorityQueue<Test>();
		
		pq2.add(new Test(10, "A"));
		pq2.add(new Test(20, "B"));
		pq2.add(new Test(30, "C"));
		pq2.add(new Test(25, "D"));
		pq2.add(new Test(15, "E"));
		pq2.add(new Test(5, "F"));
		pq2.add(new Test(0, "G"));

		while(!pq2.isEmpty()) {
			Test t = pq2.poll();
			System.out.printf("%s(%d)\n", t.name, t.age);
		}
	}

}

class Test implements Comparable<Test>{
	
	int age;
	String name;
	
	public Test(int _age, String _name) {
		
		this.age = _age;
		this.name = _name;
		
	}

	@Override
	public int compareTo(Test t) {
		// 매개변수 우선순위가 높은 것을 기준으로 작성
		if(this.age > t.age) return 1;
		else return -1;
	}
	
}
