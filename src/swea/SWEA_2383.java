package swea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class SWEA_2383 {
	
	static class Person{
		int row, col;
		int stair;
		int time;
		int state;
		
		public Person(int r, int c) {
			this.row = r;
			this.col = c;
			this.state = MOVE;
		}
	}
	
	static class Stair{
		int row, col;
		int length;
		int pCount;
		
		Queue<Person> waiting;
		
		public Stair(int r, int c, int l) {
			this.row = r;
			this.col = c;
			this.length = l;
			this.pCount = 0;
			
			this.waiting = new LinkedList<Person>();
		}
		
		public void enter(Person p) {
			if(this.pCount == 3) {
				p.state = WAIT;
				this.waiting.add(p);
			}
			else {
				this.pCount++;
				p.time = this.length;
				p.state = DOWN;
			}
		}
		
		public void exit(Person p) {
			this.pCount--;
			p.state = EXIT;
		}
	}
	
	public static final int MOVE = 0;
	public static final int READY = 1;
	public static final int WAIT = 2;
	public static final int DOWN = 3;
	public static final int EXIT = 4;
	
	public static Person[] people;
	public static Stair[] stairs;
	
	public static int TC, N, nPeople, nStairs;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		TC = Integer.parseInt(br.readLine());
		for(int tc=1; tc<=TC; tc++) {
			
			N = Integer.parseInt(br.readLine());
			
			nPeople = 0;
			nStairs = 0;
			
			people = new Person[10];
			stairs = new Stair[2];
			
			for(int i=0; i<N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine(), " ");
				for(int j=0; j<N; j++) {
					int tmp = Integer.parseInt(st.nextToken());
					if(tmp == 1) {
						// person
						people[nPeople] = new Person(i, j);
						nPeople++;
					}
					else if(tmp > 1) {
						stairs[nStairs] = new Stair(i, j, tmp);
						nStairs++;
					}
				}
			}
			
			int res = solve(0);
			System.out.println("#" + tc + " " + res);
		}
	}
	
	public static int solve(int depth) {

		if(depth == nPeople) {
			// 답 계산
			return calculate();
		}
		
		int tmp;
		int min = Integer.MAX_VALUE;
		
		for(int i=0; i<2; i++) {
			people[depth].stair = i;
			tmp = solve(depth+1);
			min = (tmp < min) ? tmp : min;
		}
		
		return min;
	}

	public static int calculate() {
		
		int totaltime = 0;
		boolean isEnd = false;

		for(int i=0; i<nPeople; i++) {			
			people[i].time = distance(people[i], stairs[people[i].stair]);
			people[i].state = MOVE;
		}
		
		while(!isEnd) {
			
			totaltime++;
			isEnd = true;
			
			for(int i=0; i<nPeople; i++) {
				Person p = people[i];
				
				if(p.state == MOVE) {
					p.time--;
					if(p.time == 0) {
						p.state = READY;
					}
				}
				else if(p.state == READY) {
					stairs[p.stair].enter(p);
				}
				else if(p.state == DOWN) {
					p.time--;
					if(p.time == 0) {
						stairs[p.stair].exit(p);
					}
				}
				
				if(p.state != EXIT) isEnd = false;
				
			}
			
			for(int i=0; i<2; i++) {

				if(stairs[i].pCount<3) {
					
					while(!stairs[i].waiting.isEmpty()) {

						Person tmp = stairs[i].waiting.poll();
						
						stairs[i].pCount++;
						tmp.time = stairs[i].length;
						tmp.state = DOWN;
						
						if(stairs[i].pCount == 3) break;
					}					
				}
			}
		}
		
		return totaltime;
		
	}
	
	public static int distance(Person p, Stair s) {
		
		return Math.abs(p.row - s.row) + Math.abs(p.col - s.col);
		
	}
	
}
