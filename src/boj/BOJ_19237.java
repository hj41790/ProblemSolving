package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 1. 설계 : 00:32:43
 * 2. 구현 : 00:43:52
 * 3. 전체 : 01:16:35
 * 
 * */

public class BOJ_19237 {
	
	public static final int[] dx = {0, 0, -1, 1};
	public static final int[] dy = {-1, 1, 0, 0};
	
	public static Shark[] sharks;
	public static int[][] num, smell;
	public static int N, M, K;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		num = new int[N][N];
		smell = new int[N][N];
		sharks = new Shark[M+1];
		
		// make map
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j=0; j<N; j++) {
				num[i][j] = Integer.parseInt(st.nextToken());
				if(num[i][j] > 0) {
					smell[i][j] = K;
					sharks[num[i][j]] = new Shark(i, j);
				}
			}
		}
		
		st = new StringTokenizer(br.readLine(), " ");
		for(int i=1; i<=M; i++) {
			sharks[i].d = Integer.parseInt(st.nextToken()) - 1;
		}
		
		for(int i=1; i<=M; i++) {
			
			sharks[i].dir = new int[4][4];
			
			for(int j=0; j<4; j++) {
				st = new StringTokenizer(br.readLine(), " ");
				for(int k=0; k<4; k++) {
					sharks[i].dir[j][k] = Integer.parseInt(st.nextToken()) - 1;
				}
			}
			
		}
		
		System.out.println(solve());
	}
	
	public static int solve() {
		
		int time = 0;
		
		while(time<=1000) {
			
			print(time);
			
			time++;
			
			int count = 0;
			
			// 상어 이동
			for(int i=1; i<=M; i++) {
				if(!sharks[i].alive) continue;
				move(sharks[i], i);
				count++;
			}
			
			// 냄새 계산
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					if(smell[i][j] > 0) {
						smell[i][j]--;
						if(smell[i][j] == 0) {
							num[i][j] = 0;
						}
					}
				}
			}
			
			for(int i=1; i<=M; i++) {
				if(!sharks[i].alive) continue;
				
				Shark s = sharks[i];
				if(num[s.r][s.c] > 0 && num[s.r][s.c] != i) {
					s.alive = false;
					count--;
				}
				else {
					num[s.r][s.c] = i;
					smell[s.r][s.c] = K;
				}
			}
			
			// 상어 수 계산
			if(count == 1) break;
			
		}
		
		if(time > 1000) time = -1;
		
		return time;
		
	}
	
	
	public static void move(Shark s, int snum) {
		
		int[] dirs = s.dir[s.d];
		boolean find = false;
		
		int nr, nc;

//		System.out.printf("(%d %d) dir(%d)\n", s.r, s.c, s.d);
		
		for(int i=0; i<4; i++) {
			
			nr = s.r + dy[dirs[i]];
			nc = s.c + dx[dirs[i]];
			
			if(isvalid(nr, nc) && smell[nr][nc] == 0) {
				s.r = nr;
				s.c = nc;
				s.d = dirs[i];
				find = true;
				break;
			}
		}
		
		if(!find) {
			
			for(int i=0; i<4; i++) {

				nr = s.r + dy[dirs[i]];
				nc = s.c + dx[dirs[i]];
				
				if(isvalid(nr, nc) && num[nr][nc] == snum) {
					s.r = nr;
					s.c = nc;
					s.d = dirs[i];
					find = true;
					break;
				}
			}
			
		}

//		System.out.printf("(%d %d) dir(%d)\n", s.r, s.c, s.d);
		
	}
	
	public static boolean isvalid(int r, int c) {
		if(0<=r && r<N && 0<=c && c<N) return true;
		else return false;
	}
	
	public static void print(int time) {
//		System.out.println("t = " + time);
//		for(int i=0; i<N; i++) {
//			for(int j=0; j<N; j++) {
//				System.out.print(smell[i][j] + " ");
//			}
//			System.out.println();
//		}
//		System.out.println();
	}
	
}

class Shark{
	int r, c, d;
	int[][] dir;
	boolean alive;
	public Shark(int _r, int _c) {
		this.r = _r;
		this.c = _c;
		this.d = 0;
		this.alive = true;
		this.dir = null;
	}
}