package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_19236 {
	
	public static int[] dx = {0, -1, -1, -1, 0, 1, 1, 1};
	public static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int[][] num = new int[4][4];
		int[][] dir = new int[4][4];
		
		for(int i=0; i<4; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j=0; j<4; j++) {
				num[i][j] = Integer.parseInt(st.nextToken());
				dir[i][j] = Integer.parseInt(st.nextToken())-1;
			}
		}
		
		int first = num[0][0];
		num[0][0] = 0;
		
		System.out.println(first + solve(num, dir, 0, 0, dir[0][0]));
		
		
	}
	
	public static int solve(int[][] num, int[][] dir, int r, int c, int d) {
		
		int max = 0;
		int row = 0, col = 0;
		int nrow = 0, ncol = 0;
		
		print(num, r, c, d);
		
		//copy
		int[][] nnum = new int[4][4];
		int[][] ndir = new int[4][4];
		for(int i=0; i<4; i++) {
			System.arraycopy(num[i], 0, nnum[i], 0, 4);
			System.arraycopy(dir[i], 0, ndir[i], 0, 4);
		}
		
		for(int n=1; n<=16; n++) {
			
			boolean find = false;
			
			for(int i=0; i<4; i++) {
				for(int j=0; j<4; j++) {
					
					if(nnum[i][j] == n) {
						find = true;
						row = i;
						col = j;
						break;
					}
				}
				if(find) break;
			}
			
			if(!find) continue;
			
			int i;
			for(i=0; i<8; i++) {
				nrow = row + dy[ndir[row][col]];
				ncol = col + dx[ndir[row][col]];
				if(isvalid(nrow, ncol) && !(nrow == r && ncol == c)) break;
				else ndir[row][col] = (ndir[row][col] + 1) % 8;
			}

			if(i<8) {
				int tmpn = nnum[nrow][ncol];
				int tmpd = ndir[nrow][ncol];
				
				nnum[nrow][ncol] = nnum[row][col];
				ndir[nrow][ncol] = ndir[row][col];
				
				nnum[row][col] = tmpn;
				ndir[row][col] = tmpd;
			}
			
		}
		
		// shark move
		for(int i=1; i<4; i++) {
			
			nrow = r + dy[d] * i;
			ncol = c + dx[d] * i;
			
			if(!isvalid(nrow, ncol)) break;
			
			if(nnum[nrow][ncol] > 0) {
				
				int tmp = nnum[nrow][ncol];
				nnum[nrow][ncol] = 0;
				
				int submax = solve(nnum, ndir, nrow, ncol, ndir[nrow][ncol]) + tmp;
				if(submax > max) max = submax;
				
//				System.out.printf("submax(%d) max(%d)\n", submax, max);

				nnum[nrow][ncol] = tmp;
				
			}
			
		}
		
		return max;
	}
	
	public static boolean isvalid(int r, int c) {
		if(0<=r && r<4 && 0<=c && c<4) return true;
		else return false;
	}

	public static void print(int[][] map, int r, int c, int d) {
//		System.out.println("d = " + (d+1) + " yummy = " + map[r][c]);
//		for(int i=0; i<4; i++) {
//			for(int j=0; j<4; j++) {
//				if(i == r && j == c) System.out.print(" x ");
//				else System.out.printf("%2d ", map[i][j]);
//			}
//			System.out.println();
//		}
//		System.out.println();
		
	}
	
}