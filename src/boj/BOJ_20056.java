package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * 1. 설계 : 00:39:49
 * 2. 구현 : 00:48:11
 * 3. 전체 : 01:28:01
 * 
 * */

public class BOJ_20056 {
	
	private static int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
	private static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};
	
	public static int N, M, K;
	
	public static Mapblock[][] map;
	public static Queue<Fireball> fbs;

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		map = new Mapblock[N][N];
		fbs = new LinkedList<Fireball>();

		int r, c, m, s, d;
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine(), " ");

			r = Integer.parseInt(st.nextToken())-1;
			c = Integer.parseInt(st.nextToken())-1;
			m = Integer.parseInt(st.nextToken());
			s = Integer.parseInt(st.nextToken());
			d = Integer.parseInt(st.nextToken());
			
			fbs.add(new Fireball(r, c, m, s, d));
		}
		
		System.out.println(solve());
		
	}
	
	public static int solve() {
		int res = 0;
		
		for(int k=0; k<K; k++) {
			
			while(!fbs.isEmpty()) {
				
				Fireball f = fbs.poll();
				move(f);
				
				if(map[f.r][f.c] == null) map[f.r][f.c] = new Mapblock(); 
				map[f.r][f.c].q.add(f); 
				map[f.r][f.c].dirs[f.d % 2]++;
				map[f.r][f.c].m += f.m;
				
			}
			
			print();
			
			for(int i=0; i<N; i++) {
				
				for(int j=0; j<N; j++) {
					
					if(map[i][j] == null || map[i][j].q.size() == 0) continue;
					
					Mapblock b = map[i][j];
					int size = b.q.size();
					
					if(size > 1) {
						int summ = 0;
						int sums = 0;
						int dirbase = (b.dirs[0] == size || b.dirs[1] == size)? 0 : 1;
						
						while(!b.q.isEmpty()) {
							Fireball f = b.q.poll();
							summ += f.m;
							sums += f.s;
						}
						
						if(summ/5 > 0) {
							for(int a=0; a<4; a++) {
								fbs.add(new Fireball(i, j, summ/5, sums/size, dirbase+a*2));
							}
						}
					}
					else if(size == 1) {
						fbs.add(b.q.poll());
					}
					
					b.dirs[0] = b.dirs[1] = 0;
					b.m = 0;
				}
				
			}
			
			print();
		}
		
		while(!fbs.isEmpty()) {
			res += fbs.poll().m;
		}
		
		
		return res;
	}
	
	public static void move(Fireball f) {
		
		int nrow, ncol;
		
		nrow = f.r + (dy[f.d]* f.s);
		while(nrow < 0) nrow += N;
		nrow %= N;
		
		ncol = f.c + (dx[f.d] * f.s);
		while(ncol < 0) ncol += N;
		ncol %= N;
		
		f.r = nrow;
		f.c = ncol;
	}
	
	public static void print() {
		
//		for(int i=0; i<N; i++) {
//			for(int j=0; j<N; j++) {
//				int m;
//				if(map[i][j] == null) m = 0;
//				else m = map[i][j].m;
//				System.out.printf("%2d ", m);
//			}
//			System.out.println();
//		}
//		System.out.println();
	}

}

class Fireball{
	int r, c;
	int m, s, d;
	public Fireball(int _r, int _c, int _m, int _s, int _d){
		this.r = _r;
		this.c = _c;
		this.m = _m;
		this.s = _s;
		this.d = _d;
	}
}

class Mapblock{
	Queue<Fireball> q;
	int[] dirs;
	int m;
	public Mapblock() {
		this.q = new LinkedList<Fireball>();
		this.dirs = new int[2];
		this.m = 0;
	}
}