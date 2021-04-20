package swea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA_2112 {
	
	public static int TC, D, W, K;
	
	public static int injectionTime;
	public static int[][] map;
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		TC = Integer.parseInt(br.readLine());
		for(int tc=1; tc<=TC; tc++) {
			
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			
			D = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			map = new int[D][W];
			int[] injectmap = new int[D];
			
			for(int i=0; i<D; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				for(int j=0; j<W; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			injectionTime = 0;
			boolean pass = false;
			
			while(!pass) {
				
				pass = solve(injectmap, 0, 0);
				
				if(pass) break;
				else injectionTime++;
				
				//if(injectionTime > D) break;
			}
			
			System.out.println("#" + tc + " " + injectionTime);
			
		}

	}
	
	public static boolean solve(int[] injectmap, int start, int depth) {
		
		if(depth == injectionTime) {
			return test(injectmap);
		}
		
		boolean pass = false;
		
		for(int row=start; row<D; row++) {
			
			for(int i=1; i<=2; i++) {

				injectmap[row] = i;
				
				pass = solve(injectmap, row+1, depth+1);
				
				injectmap[row] = 0;
				
				if(pass) break;
			}
			
			if(pass) break;
		}
		
		return pass;
	}
	
	public static boolean test(int[] injectmap) {
		
//		print(injectmap);
		
		boolean pass = true;
		
		if(K==1) return true;
		
		for(int col=0; col<W; col++) {
			
			boolean localpass = false;
			int num = 1;
			
			int prev = (injectmap[0]>0) ? (injectmap[0] - 1) : map[0][col];
			
			for(int row=1; row<D; row++) {
				
				int curr = (injectmap[row]>0) ? (injectmap[row] - 1) : map[row][col];
				
				if(prev == curr) {
					num++;
					if(num >= K) {
						localpass = true;
						break;
					}
				}
				else {
					num = 1;
				}
				
				prev = curr;
			}
			
			if(!localpass) {
				pass = false;
				break;
			}
		}
		
		return pass;
	}
	

	public static void print(int[] injectmap) {
		
		for(int i=0; i<D; i++) System.out.print(injectmap[i] + " ");
		System.out.println();
	}

	public static void print2(int[][] map, boolean[] injectmap) {
		
//		for(int row=0; row<D; row++) {
//			System.out.print((injectmap[row] ? 1 : 0) + " : ");
//			for(int col=0; col<W; col++) {
//				System.out.print(map[row][col] + " ");
//			}
//			System.out.println();
//		}
//		System.out.println();
//		System.out.println();
	}
	
}
