package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/*
 * 1. 설계 : 00:44:43.72
 * 2. 구현 : 00:31:59.13
 * 3. 전체 : 01:16:42.86
 * 
 * */


public class BOJ_20057 {
	
	
	public static final int[] dx = {1, 0, -1, 0};
	public static final int[] dy = {0, -1, 0, 1};
	
	public static final int[] ratio = {1, 1, 7, 2, 7, 2, 10, 10, 5};
	
	public static final int[][] rdx = {	{-1, -1, 0, 0, 0, 0, 1, 1, 2},
										{-1, 1, -1, -2, 1, 2, -1, 1, 0},
										{1, 1, 0, 0, 0, 0, -1, -1, -2},
										{1, -1, 1, 2, -1, -2, 1, -1, 0}};
	
	public static final int[][] rdy = {	{-1, 1, -1, -2, 1, 2, -1, 1, 0},
										{1, 1, 0, 0, 0, 0, -1, -1, -2},
										{1, -1, 1, 2, -1, -2, 1, -1, 0},
										{-1, -1, 0, 0, 0, 0, 1, 1, 2}};
	
	public static int N;
	public static int[][] map;
	
	public static int[][] visit;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		map = new int[N][N];
		visit = new int[N][N];
		for(int i=0; i<N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		System.out.println(solve());
		
	}
	
	public static int solve() {
		
		int res = 0;
		
		int curx = N/2;
		int cury = N/2;
		int dirbase, dir;
		
		
		for(int i=1; i<N; i++) {
			dirbase = (i % 2) * 2;
			
			for(int j=0; j<2; j++) {
				dir = dirbase + j;
				
				for(int k=0; k<i; k++) {
					curx += dx[dir];
					cury += dy[dir];
					
					res += sandpush(curx, cury, dir);
				}
			}
		}
		
		// last
		dir = 2;
		for(int i=1; i<N; i++) {
			curx += dx[dir];
			cury += dy[dir];
			
			res += sandpush(curx, cury, dir);
		}
		
		return res;
		
	}
	
	public static int sandpush(int x, int y, int dir) {
		int res = 0;
		
		int sand, nx, ny;
		
		int[] ndx = rdx[dir];
		int[] ndy = rdy[dir];
		
		// ratio
		
		int firstsand = map[y][x];
		
		for(int i=0; i<9; i++) {
			
			sand = (firstsand * ratio[i]) / 100;
			nx = x + ndx[i];
			ny = y + ndy[i];
			
			if(isvalid(nx, ny)) {
				map[ny][nx] += sand;
			}
			else {
				res += sand;
			}
			
			map[y][x] -= sand;
			
		}
		
		// alpha
		nx = x + dx[dir];
		ny = y + dy[dir];
		
		if(isvalid(nx, ny)) {
			map[ny][nx] += map[y][x];
		}
		else {
			res += map[y][x];
		}
		
		map[y][x] = 0;
		
		return res;
	}
	
	public static boolean isvalid(int x, int y) {
		if(0<=x && x<N && 0<=y && y<N) return true;
		else return false;
	}
	
}
