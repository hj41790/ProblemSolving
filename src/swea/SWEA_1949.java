package swea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;


public class SWEA_1949 {
	
	static class Pair{
		int row;
		int col;
		
		public Pair(int _row, int _col)
		{
			this.row = _row; 
			this.col = _col;
		}
	}
	
	static final int[] dx = new int[] {0, 1, 0, -1};
	static final int[] dy = new int[] {-1, 0, 1, 0};
	
	static int TC, N, K, construction;
	
	static int[][] map;
	static boolean[][] visit;
	static ArrayList<Pair> list;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		int res, max, tmp;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		TC = Integer.parseInt(br.readLine());
		
		for(int cnt=1; cnt<=TC; cnt++) {
			
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			map = new int[N][N];
			visit = new boolean[N][N];
			list = new ArrayList<Pair>();
			construction = 1;
			
			res = -1;
			max = -1;
			
			for(int i=0; i<N; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				for(int j=0; j<N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					visit[i][j] = false;
					
					max = (map[i][j] > max) ? map[i][j] : max;
				}
			}
			
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					if(map[i][j] == max) {
						list.add(new Pair(i, j));
					}
				}
			}

			max = -1;
			Iterator<Pair> it = list.iterator();
			while(it.hasNext()) {
				Pair p = it.next();

				tmp = dfs(p.row, p.col);
				if(tmp > res) res = tmp;
				
			}
			
			
			System.out.println("#" + cnt + " " + res);
		}

	}
	
	public static int dfs(int row, int col) {
		
		int res = 1;
		int nrow, ncol, tmp, maxtmp;
		
		visit[row][col] = true;
		
		for(int i=0; i<4; i++) {
			nrow = row + dy[i];
			ncol = col + dx[i];
			
			if(index_valid(nrow, ncol) && !visit[nrow][ncol]) {
				
				if(map[nrow][ncol] < map[row][col]) {
					
					maxtmp = dfs(nrow, ncol) + 1;
					res = (maxtmp > res) ? maxtmp : res;
					
				}
				else if(construction > 0 && map[nrow][ncol] - K < map[row][col]) {

					construction--;
					tmp = map[nrow][ncol];
					map[nrow][ncol] = map[row][col] - 1;
					
					maxtmp = dfs(nrow, ncol) + 1;
					res = (maxtmp > res) ? maxtmp : res;

					map[nrow][ncol] = tmp;
					construction++;
				}
				
				
			}
			
		}
		
		visit[row][col] = false;
		
		return res;
	}
	
	public static boolean index_valid(int row, int col) {
		
		if(0 <= row && row < N && 0 <= col && col < N) return true;
		else return false;
	}

}
