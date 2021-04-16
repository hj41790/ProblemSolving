package swea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * 1. 이해 + 구현 	: 25:02:21
 * 2. 코딩 		: 28:12:19
 * 3. 총 소요시간 	: 53:14:40
 * */


public class SWEA_1953 {
	
	static class Pipe{
		public int row;
		public int col;
		public int time;
		public boolean visit;
		public boolean[] type;
		
		public Pipe(int r, int c, boolean[] t) {
			this.row = r;
			this.col = c;
			this.type = t;
			this.time = 0;
			this.visit = false;
		}
	}
	
	public static final int[] dx = {0, 1, 0, -1};
	public static final int[] dy = {-1, 0, 1, 0};
	
	public static final boolean[][] ptype = {	{false, false, false, false}, 
												{true, true, true, true},  
												{true, false, true, false}, 
												{false, true, false, true}, 
												{true, true, false, false}, 
												{false, true, true, false}, 
												{false, false, true, true}, 
												{true, false, false, true}};
	
	
	public static Pipe[][] map;
	public static int TC, N, M, L, R, C;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		int res;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		TC = Integer.parseInt(br.readLine());
		
		for(int tc = 1; tc <= TC; tc++) {
			
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			
			N = Integer.parseInt(st.nextToken()); 
			M = Integer.parseInt(st.nextToken());
			R = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			L = Integer.parseInt(st.nextToken());

			map = new Pipe[N][M];
			for(int i=0; i<N; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				for(int j=0; j<M; j++) {
					map[i][j] = new Pipe(i, j, ptype[Integer.parseInt(st.nextToken())]);
				}
			}
			
			res = BFS(R, C);
			
			System.out.println("#" + tc + " " + res);
		}
		

	}
	
	public static int BFS(int row, int col) {
		
		ArrayList<Pipe> l = new ArrayList<Pipe>();
		Queue<Pipe> q = new LinkedList<Pipe>();
		
		Pipe tmp;
		Pipe p = map[row][col];
		int nrow, ncol;
		
		p.visit = true;
		p.time = 1;
		
		l.add(p);
		q.add(p);
		
		while(!q.isEmpty()) {
			
			p = q.poll();
			
			if(p.time > L) break;
			
			for(int i=0; i<4; i++) {
				nrow = p.row + dy[i];
				ncol = p.col + dx[i];
				
				if(!idxvalid(nrow, ncol)) continue;
				
				tmp = map[nrow][ncol];
				if(!tmp.visit && p.type[i] && tmp.type[(i+2)%4]) {
					tmp.time = p.time + 1;
					tmp.visit = true;
					q.add(tmp);
					if(tmp.time <= L) l.add(tmp);
				}
			}
		}
		
		return l.size();
	}
	
	
	public static boolean idxvalid(int row, int col) {
		if(0 <= row && row < N && 0 <= col && col < M) return true;
		else return false;
	}

}
