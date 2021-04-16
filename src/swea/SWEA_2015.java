package swea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class SWEA_2015 {
	
	static class Pair{
		int row;
		int col;
		
		public Pair(int _row, int _col) {this.row = _row; this.col = _col;}
	}
	
	static final int[] dx = new int[] {1, 1, -1, -1};
	static final int[] dy = new int[] {-1, 1, 1, -1};
	
	static int TC, N;
	
	static int[][] map;
	static int[] straight;
	static boolean[] desert;
	static boolean[][] visit;
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		int res, max;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		TC = Integer.parseInt(br.readLine());
		
		for(int tc=1; tc<=TC; tc++) {
			
			max = -1;
			
			N = Integer.parseInt(br.readLine());
			
			map = new int[N][N];
			visit = new boolean[N][N];
			for(int i=0; i<N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine(), " ");
				for(int j=0; j<N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					visit[i][j] = false;
				}
			}
			
			straight = new int[4];
			for(int i=0; i<4; i++) straight[i] = 0;
			
			
			desert = new boolean[101];
			for(int i=0; i<101; i++) desert[i] = false;

			
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					res = dfs(i, j, 0);
					if(res > max) max = res;
				}
			}
			
			System.out.println("#"+tc+" "+max);
		}
		
		
		

	}
	
	public static int dfs(int row, int col, int diridx) {
		
		boolean pass = true;
		
		int res = -1;
		int nrow, ncol, tmp;
		
		desert[map[row][col]] = true;
		visit[row][col] = true;
		
		if(diridx > 1) {
			
			Stack<Pair> stack = new Stack<Pair>();
			
			nrow = row;
			ncol = col;
			
			for(int i=0; i<straight[0]-1; i++) {
				nrow = nrow + dy[2];
				ncol = ncol + dx[2];
				
				if(index_valid(nrow, ncol) && !desert[map[nrow][ncol]]) {
					desert[map[nrow][ncol]] = true;
					visit[nrow][ncol] = true;
					stack.add(new Pair(nrow, ncol));
				}
				else {
					pass = false;
					break;
				}
			}
			
			if(pass) {
				
				for(int i=0; i<straight[1]-1; i++) {
					nrow = nrow + dy[3];
					ncol = ncol + dx[3];
					
					if(index_valid(nrow, ncol) && !desert[map[nrow][ncol]]) {
						desert[map[nrow][ncol]] = true;
						visit[nrow][ncol] = true;
						stack.add(new Pair(nrow, ncol));
					}
					else {
						pass = false;
						break;
					}
				}
				
				if(pass) {
					res = (straight[0] + straight[1]) * 2;
				}
			}

			while(!stack.isEmpty()) {
				Pair p = stack.pop();
				desert[map[p.row][p.col]] = false;
				visit[p.row][p.col] = false;
			}
			
		}
		else {
			// Straight
			nrow = row + dy[diridx];
			ncol = col + dx[diridx];
			
			if(index_valid(nrow, ncol) && !desert[map[nrow][ncol]]) {
				straight[diridx]++;
				tmp = dfs(nrow, ncol, diridx);
				res = (tmp > res) ? tmp : res;
				straight[diridx]--;
			}

			// Cornering
			if(straight[diridx] > 0) {
				nrow = row + dy[diridx + 1];
				ncol = col + dx[diridx + 1];
				if(index_valid(nrow, ncol) && !desert[map[nrow][ncol]]) {
					straight[diridx + 1]++;
					
					tmp = dfs(nrow, ncol, diridx + 1);
					res = (tmp > res) ? tmp : res;
					straight[diridx + 1]--;
				}
			}
		}

		visit[row][col] = false;
		desert[map[row][col]] = false;
		
		return res;
	}
	
	public static boolean index_valid(int row, int col) {
		if(0 <= row && row < N && 0 <= col && col < N) return true;
		else return false;
	}

}
