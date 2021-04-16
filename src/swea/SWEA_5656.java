package swea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class SWEA_5656 {
	
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
	
	public static int TC, N, W, H;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		TC = Integer.parseInt(br.readLine());
		for(int tc=1; tc<=TC; tc++) {
		
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			
			N = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			
			int[][] map = new int[H][W];
			for(int i=0; i<H; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				for(int j=0; j<W; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			int res = solve(map, 0);	
			
			System.out.println("#"+tc+" "+res);
		}
		
	}
	
	public static int solve(int[][] map, int depth) {
		
		if(depth == N) {
			int count = 0;
			for(int i=0; i<H; i++) {
				for(int j=0; j<W; j++) {
					if(map[i][j] > 0) {
						count++;
					}
				}
			}
			return count;
		}
		
		int max = 0;
		for(int i=0; i<W; i++) {
			for(int j=0; j<H; j++) {
				if(map[j][i] > 0) {
					max = (map[j][i] > max) ? map[j][i] : max;
					break;
				}
					
			}
		}
		if(max == 0) return 0;
		
		int min = Integer.MAX_VALUE;
		for(int i=0; i<W; i++) {
			for(int j=0; j<H; j++) {
				if(map[j][i] > 0) {
					
					int[][] nmap = bomb(j, i, map);
					int tmp = solve(nmap, depth+1);
					min = (tmp < min) ? tmp : min;
					
					break;
				}
					
			}
		}
		
		return min;
	}

	public static int[][] bomb(int row, int col, int[][] map){

		boolean[][] visit = new boolean[H][W];
		Queue<Point> q = new LinkedList<Point>();
		
		int nrow, ncol, num;
		
		visit[row][col] = true;
		q.add(new Point(row, col));
		
		while(!q.isEmpty()) {
			Point p = q.poll();
			
			num = map[p.row][p.col];
			for(int i=1; i<num; i++) {

				for(int j=0; j<4; j++) {
					nrow = p.row + (dy[j] * i);
					ncol = p.col + (dx[j] * i);
					
					if(idx_valid(nrow, ncol) && map[nrow][ncol]>0 && !visit[nrow][ncol]) {
						visit[nrow][ncol] = true;
						if(map[nrow][ncol] > 1) q.add(new Point(nrow, ncol));
					}
				}
			}
		}
		
		int[][] newmap = new int[H][W];
		for(int i=0; i<W; i++) {
			int h = H-1;
			for(int j=H-1; j>=0; j--) {
				if(!visit[j][i]) {
					newmap[h][i] = map[j][i];
					h--;
				}
			}
		}
		
		return newmap;
	}
	
	public static boolean idx_valid(int row, int col) {
		if(0<=row && row<H && 0<=col && col<W) return true;
		else return false;
	}
}
