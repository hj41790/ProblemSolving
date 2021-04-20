package swea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 1. 설계 : 42:15.79
 * 2. 구현 : 1:03:30.89
 *
 */

public class SWEA_4014 {
	
	public static int TC, N, X;
	
	public static int[][] map;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		BufferedReader br = new  BufferedReader(new InputStreamReader(System.in));
		
		TC = Integer.parseInt(br.readLine());
		for(int tc=1; tc<=TC; tc++) {
			
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			
			N = Integer.parseInt(st.nextToken());
			X = Integer.parseInt(st.nextToken());
			
			map = new int[N][N];
			for(int i=0; i<N; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				for(int j=0; j<N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			int res = solve();
			
			System.out.println("#" + tc + " " + res);
		}

	}
	
	public static int solve() {
		
		int count = 0;
		
		int[] forward, backward;
		boolean[] install;
		
		// 가로
		for(int row=0; row<N; row++) {
			
			forward = new int[N];
			backward = new int[N];
			install = new boolean[N];
			
			// 연속 배열 만들기
			forward[0] = 1;
			for(int i=1; i<N; i++) {
				if(map[row][i] != map[row][i-1]) forward[i] = 1;
				else forward[i] = forward[i-1] + 1;
			}
			backward[N-1] = 1;
			for(int i=N-2; i>=0; i--) {
				if(map[row][i] != map[row][i+1]) backward[i] = 1;
				else backward[i] = backward[i+1] + 1;
			}
			
			// 계산
			boolean available = true;
			for(int i=1; i<N; i++) {
				
				if(map[row][i] != map[row][i-1]) {
					
					// 현재 높이가 1칸 높은 경우 = 이전칸에 활주로 놓아야 함
					if(map[row][i-1] + 1 == map[row][i]) {
						
						if( i<X || forward[i-1]<X ) {
							available = false;
							break;
						}
						
						for(int j=1; j<=X; j++) {
							if(install[i-j]) {
								available = false;
								break;
							}
						}
						
						// 설치
						for(int j=1; j<=X; j++) {
							install[i-j] = true;
						}
						
					}
					// 현재 높이가 1칸 낮은 경우 = 이번칸에 활주로 놓아야 함
					else if(map[row][i-1] - 1 == map[row][i]) {
						
						if( i+X > N || backward[i]<X ) {
							available = false;
							break;
						}
						
//						for(int j=0; j<X; j++) {
//							if(install[i+j]) {
//								available = false;
//								break;
//							}
//						}
						
						for(int j=0; j<X; j++) {
							install[i+j] = true;
						}
						
						i += X-1;
						
					}
					else {
						available = false;
						break;
					}
					
				}
				
			}
			
			if(available) {
//				System.out.printf("row(%d) : ", row);
//				for(int i=0; i<N; i++) System.out.printf("%d ", install[i]?1:0);
//				System.out.println();
				count++;
			}
			
		}
		
//		System.out.println();
		
		// 세로
		for(int col=0; col<N; col++) {
			
			forward = new int[N];
			backward = new int[N];
			install = new boolean[N];
			
			// 연속 배열 만들기
			forward[0] = 1;
			for(int i=1; i<N; i++) {
				if(map[i][col] != map[i-1][col]) forward[i] = 1;
				else forward[i] = forward[i-1] + 1;
			}
			backward[N-1] = 1;
			for(int i=N-2; i>=0; i--) {
				if(map[i][col] != map[i+1][col]) backward[i] = 1;
				else backward[i] = backward[i+1] + 1;
			}
			
			// 계산
			boolean available = true;
			for(int i=1; i<N; i++) {
				
				if(map[i][col] != map[i-1][col]) {
					
					if(map[i-1][col] + 1 == map[i][col]) {
						
						if( i<X || forward[i-1]<X ) {
							available = false;
							break;
						}
						
						for(int j=1; j<=X; j++) {
							if(install[i-j]) {
								available = false;
								break;
							}
						}
						
						for(int j=1; j<=X; j++) {
							install[i-j] = true;
						}
						
					}
					else if(map[i-1][col] - 1 == map[i][col]) {
												
						if( i+X > N || backward[i]<X ) {
							available = false;
							break;
						}
						
//						for(int j=0; j<X; j++) {
//							if(install[i+j]) {
//								available = false;
//								break;
//							}
//						}
						
						for(int j=0; j<X; j++) {
							install[i+j] = true;
						}
						
						i += X-1;
						
					}
					else {
						available = false;
						break;
					}
					
				}
				
			}
			
			if(available) {
//				System.out.printf("col(%d) : ", col);
//				for(int i=0; i<N; i++) System.out.printf("%d ", install[i]?1:0);
//				System.out.println();
				count++;
			}
			
		}
		
		return count;
	}

}
