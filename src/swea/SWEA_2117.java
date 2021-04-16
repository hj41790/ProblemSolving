package swea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class SWEA_2117 {
	
	static class Point{
		int row;
		int col;
		public Point(int r, int c) {
			this.row = r; 
			this.col = c;
		}
	}
	
	public static final int[] dx = {0, 1, 0, -1};
	public static final int[] dy = {-1, 0, 1, 0};
	
	public static boolean[][] map;
	public static int TC, N, M, H;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		TC = Integer.parseInt(br.readLine());
		
		for(int tc=1; tc<=TC; tc++) {
			
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			H = 0;
			
			map = new boolean[N][N];
			for(int i=0; i<N; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				for(int j=0; j<N; j++) {
					map[i][j] = (Integer.parseInt(st.nextToken()) == 1);
					if(map[i][j]) H++;
				}
			}
			
			int MAX_K = find_maxK();
			int MAX_H = 0;
			
			//System.out.println("maxK : "+MAX_K);

			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					
					for(int k=1; k<MAX_K; k++) {
						
						int cnt = find_house(i, j, k);
						if(calculate(k, cnt) < 0) continue;
						
						MAX_H = (cnt > MAX_H) ? cnt : MAX_H;
					}
					
				}
			}
			
			System.out.println("#" + tc + " " + MAX_H);
			
		}

	}
	
	public static int find_house(int row, int col, int k) {
		
		Queue<Point> q = new LinkedList<Point>();
		int[][] dist = new int[N][N];
		
		int nrow, ncol;
		int count = 0;
		
		dist[row][col] = 1;
		q.add(new Point(row, col));
		
		while(!q.isEmpty()) {
			
			Point p = q.poll();
			
			if(dist[p.row][p.col] > k) break;
			
			if(map[p.row][p.col]) count++;

			for(int i=0; i<4; i++) {
				nrow = p.row + dy[i];
				ncol = p.col + dx[i];
				if(idx_valid(nrow, ncol) && dist[nrow][ncol] == 0) {
					dist[nrow][ncol] = dist[p.row][p.col] + 1;
					q.add(new Point(nrow, ncol));
				}
			}
		}
		
		return count;
	}
	
	public static int calculate(int k, int h) {
		return (h * M) - ((k*k) + ((k-1)*(k-1)));
	}
	
	public static int find_maxK() {
		
		int k;//, size;
		for(k=1; ; k++) {
			//size = (k*k) + ((k-1)*(k-1));
			if( calculate(k, H) < 0 ) break;
			//if( size > H || calculate(k, H) < 0 ) break;
		}
		return k;
	}
	
	public static boolean idx_valid(int row, int col) {
		if(0<=row && row<N && 0<=col && col<N) return true;
		else return false;
	}

}
