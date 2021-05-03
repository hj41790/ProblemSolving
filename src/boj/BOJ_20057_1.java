package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 1. 설계 : 00:25:55
 * 2. 구현 : 00:32:24
 * 3. 전체 : 00:58:20
 * 
 * */

public class BOJ_20057_1 {
	
	public static final int[] dx = {1, 0, -1, 0};
	public static final int[] dy = {0, -1, 0, 1};
	
	public static final int[] rdx = {1, -1, -1, 0, 0, 0, 0, 1, 1, 2};
	public static final int[] rdy = {0, -1, 1, -1, -2, 1, 2, -1, 1, 0};
	public static final int[] ratio = {0, 1, 1, 7, 2, 7, 2, 10, 10, 5};
	
	public static int N;
	public static int[][] map;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		map = new int[N][N];
		for(int i=0; i<N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		System.out.println(solve());

	}
	
	public static int solve() {
		
		int discard = 0;
		
		int[] nrdx = new int[10];
		int[] nrdy = new int[10];
		int[] tmp;
		
		int dirbase, d, nr, nc;
		int r = N/2;
		int c = N/2;
		
		for(int cnt=1; cnt<N; cnt++) {
			
			dirbase = (cnt % 2) * 2;
			for(int i=0; i<2; i++) {
				
				d = dirbase + i;
				
				System.arraycopy(rdx, 0, nrdx, 0, 10);
				System.arraycopy(rdy, 0, nrdy, 0, 10);
				
				if(d>0) {
					if(d%2 > 0) {
						tmp = nrdx;
						nrdx = nrdy;
						nrdy = tmp;
					}
					if(d<3) {
						for(int j=0; j<10; j++) {
							nrdx[j] *= -1;
							nrdy[j] *= -1;
						}
					}
				}
				
				for(int j=0; j<cnt; j++) {
					
					nr = r + dy[d];
					nc = c + dx[d];
					
					discard += pushsand(nr, nc, nrdx, nrdy);
					
					r = nr;
					c = nc;
				}
				
			}
		}
		
		
		
		// last push
		d = 2;
		
		System.arraycopy(rdx, 0, nrdx, 0, 10);
		System.arraycopy(rdy, 0, nrdy, 0, 10);
		
		if(d>0) {
			if(d%2 > 0) {
				tmp = nrdx;
				nrdx = nrdy;
				nrdy = tmp;
			}
			if(d<3) {
				for(int j=0; j<10; j++) {
					nrdx[j] *= -1;
					nrdy[j] *= -1;
				}
			}
		}
		
		for(int cnt=1; cnt<N; cnt++) {
			nr = r + dy[d];
			nc = c + dx[d];
			
			discard += pushsand(nr, nc, nrdx, nrdy);
			
			r = nr;
			c = nc;
		}
		
		
		return discard;
	}
	
	public static int pushsand(int r, int c, int[] nrdx, int[] nrdy) {
		
		int discard = 0;
		int nr, nc, sand;
		int firstsand = map[r][c];
		
		for(int i=1; i<10; i++) {
			
			nr = r + nrdy[i];
			nc = c + nrdx[i];
			sand = (firstsand * ratio[i]) / 100;
					
			if(isvalid(nr, nc)) {
				map[nr][nc] += sand;
			}
			else {
				discard += sand;
			}
			
			map[r][c] -= sand;
		}
		
		nr = r + nrdy[0];
		nc = c + nrdx[0];
		if(isvalid(nr, nc)) {
			map[nr][nc] += map[r][c];
		}
		else {
			discard += map[r][c];
		}
		map[r][c] = 0;
		
		return discard;
	}
	
	public static boolean isvalid(int r, int c) {
		return (0<=r && r<N && 0<=c && c<N);
	}

}
