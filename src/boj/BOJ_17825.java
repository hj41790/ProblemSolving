package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 1. 설계 : 00:28:21
 * 2. 구현 : 00:48:52
 * 3. 전체 : 01:17:14
 * */

public class BOJ_17825 {
	
	public static int[] number;
	public static User[] users;
	public static Node start, end;
	
	public static int maxscore = 0;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		number = new int[10];
		for(int i=0; i<10; i++) {
			number[i] = Integer.parseInt(st.nextToken());
		}
		
		// make map
		start = new Node(0);
		end = new Node(-1);
		
		Node _10 = null;
		Node _20 = null;
		Node _25 = new Node(25);
		Node _30 = null;
		Node _40 = null;
		
		Node n = start;
		for(int i=2; i<=40; i+=2) {
			n.red = new Node(i);
			if(i==10) _10 = n.red;
			else if(i==20) _20 = n.red;
			else if(i==25) _25 = n.red;
			else if(i==30) _30 = n.red;
			else if(i==40) _40 = n.red;
			
			n = n.red;
		}
		
		// _10 to _25
		_10.blue = new Node(13);
		_10.blue.red = new Node(16);
		_10.blue.red.red = new Node(19);
		_10.blue.red.red.red = _25;
		
		// _20 to _25
		_20.blue = new Node(22);
		_20.blue.red = new Node(24);
		_20.blue.red.red = _25;
		
		// _30 to _25
		_30.blue = new Node(28);
		_30.blue.red = new Node(27);
		_30.blue.red.red = new Node(26);
		_30.blue.red.red.red = _25;
		
		// 25 to 40
		_25.red = new Node(30);
		_25.red.red = new Node(35);
		_25.red.red.red = _40;
		
		// _40 to end
		_40.red = end;
		
		// end
		end.red = end;

		
		// make users
		users = new User[4];
		for(int i=0; i<4; i++) {
			users[i] = new User(start);
		}
		
		print(start);
		
		solve(0);
		
		System.out.println(maxscore);

	}
	
	public static void solve(int index) {
		
//		System.out.printf("idx(%d) ", index);
//		for(int i=0; i<4; i++) System.out.printf("%2d(%3d) ", users[i].node.num, users[i].score);
//		System.out.println();
		
		if(index == 10) {
			
			int tmp = 0;
			for(int i=0; i<4; i++) {
				tmp += users[i].score;
			}
			if(maxscore < tmp) maxscore = tmp;
			
			return;
		}
		
		int next = number[index];
		
		for(int i=0; i<4; i++) {
			
			Node currn = users[i].node;
			if(currn == end) continue;
			
			Node nextn = null;
			if(currn.blue != null) {
				nextn = currn.blue;
			}
			else {
				nextn = currn.red;
			}
			
			for(int j=1; j<next; j++) {
				nextn = nextn.red;
			}
			
			if(nextn == end) {
				currn.user = null;
				users[i].node = end;
				solve(index + 1);
				users[i].node = currn;
				currn.user = users[i];
			}
			else if(nextn.user == null){
				nextn.user = users[i];
				currn.user = null;
				users[i].node = nextn;
				users[i].score += nextn.num;
				
				solve(index + 1);
				
				users[i].score -= nextn.num;
				users[i].node = currn;
				currn.user = users[i];
				nextn.user = null;
			}
			
		}
		
	}
	
	public static void print(Node n) {
		
//		while(n != end) {
//			
//			if(n.blue != null) {
//				System.out.print("(" +n.blue.num + ") - ");
//			}
//			
//			System.out.print(n.num + " - ");
//			n = n.red;
//		}
//		System.out.println(n.num);
	}

}

class Node{
	
	int num;
	Node red, blue;
	User user;
	
	public Node(int n) {
		this.num = n;
		this.red = null;
		this.blue = null;
		this.user = null;
	}
}

class User{
	Node node;
	int score;
	
	public User(Node start) {
		this.node = start;
		this.score = 0;
	}
}