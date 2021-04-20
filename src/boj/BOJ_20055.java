package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * 1. 설계 : 00:35:06.11
 * 2. 구현 : 00:20:17.89
 * 3. 전체 : 00:55:24.00
 * 
 * */


public class BOJ_20055 {
	
	public static int N, K;
	public static Block[] belt;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		belt = new Block[N * 2];
		
		st = new StringTokenizer(br.readLine(), " ");
		for(int i=0; i<N*2; i++) {
			int a = Integer.parseInt(st.nextToken());
			belt[i] = new Block(a);
		}
		
		System.out.println(solve());
	}
	
	public static int solve() {
		
		int step = 0;
		int count = 0;
		
		int up = 0;
		int down = N-1;
		
		
		while(count < K) {
			
			step++;
			
			// 1
			up = (up==0) ? ((2*N)-1) : (up-1);
			down = (down==0) ? ((2*N)-1) : (down-1);
			
			// 1-1
			if(belt[down].robot) belt[down].robot = false;
			
			// 2
			for(int i=1; i<N; i++) {
				int idx = down-i;
				int curr = (idx < 0) ? ((2*N) + idx) : idx;
				int next = (curr+1)%(2*N);
				
				if(belt[curr].robot == false) continue;
				else if(belt[next].robot == false && belt[next].A > 0) {
					
					belt[curr].robot = false;
					belt[next].robot = true;
					belt[next].A--;
					
					if(belt[next].A == 0) count++;
					
				}
			}
			
			// 3 
			if(belt[up].robot == false && belt[up].A > 0) {
				
				belt[up].robot = true;
				belt[up].A--;
				
				if(belt[up].A == 0) count++;
				
			}
			
			// 4 
			if(belt[down].robot) belt[down].robot = false;
			
		}
		
		return step;
	}

}

class Block{
	
	int A;
	boolean robot;
	
	public Block(int a) {
		this.A = a;
		this.robot = false;
	}
	
}