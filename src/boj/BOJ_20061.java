package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 1. 설계 : 00:33:56
 * 2. 구현 : 01:10:29
 * 3. 전체 : 01:44:26
 * 
 * */

public class BOJ_20061 {
	
	
	public static boolean[][] blue;
	public static boolean[][] green;
	
	public static int N;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		blue = new boolean[4][6];
		green = new boolean[6][4];
		
		int type, x, y;
		int score = 0;
		for(int i=0; i<N; i++) {
			
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");

			type = Integer.parseInt(st.nextToken());
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
			
			score += solve(type, x, y);
			
		}
		
		int tile = 0;
		for(int i=0; i<4; i++) {
			for(int j=0; j<6; j++) {
				if(blue[i][j]) tile++;
				if(green[j][i]) tile++;
			}
		}
		
		System.out.println(score);
		System.out.println(tile);
	}
	
	public static int solve(int type, int x, int y) {
		
		int score = 0;
		int count;
		
		/*         blue         */
		for(int c=0; c<6; c++) {
			if(!isempty(blue, type, x, c)) {
				fill(blue, type, x, c-1);
				break;
			}
			else if(c==5) {
				fill(blue, type, x, c);
			}
		}
		
		// fill
		for(int c=5; c>=0; c--) {
			boolean allfill = true;
			for(int r=0; r<4; r++) {
				if(!blue[r][c]) {
					allfill = false;
					break;
				}
			}
			
			if(allfill) {
				score++;
				for(int nc=c-1; nc>=0; nc--) {
					for(int nr=0; nr<4; nr++) {
						blue[nr][nc+1] = blue[nr][nc];
					}
				}
				
				for(int nr=0; nr<4; nr++) {
					blue[nr][0] = false;
				}
				
				c++;
			}
		}
		
		// over
		count = 0;
		for(int c=0; c<2; c++) {
			for(int r=0; r<4; r++) {
				if(blue[r][c]) {
					count++;
					break;
				}
			}
		}
		if(count>0) {
			for(int c=5-count; c>=0; c--) {
				for(int r=0; r<4; r++) {
					blue[r][c+count] = blue[r][c];
				}
			}
			for(int c=0; c<2; c++) {
				for(int r=0; r<4; r++) {
					blue[r][c] = false;
				}
			}
		}
		
		
		/*         green         */
		for(int r=0; r<6; r++) {
			if(!isempty(green, type, r, y)) {
				fill(green, type, r-1, y);
				break;
			}
			else if(r==5) {
				fill(green, type, r, y);
			}
		}
		
		// check fill
		for(int r=5; r>=0; r--) {
			boolean allfill = true;
			for(int c=0; c<4; c++) {
				if(!green[r][c]) {
					allfill = false;
					break;
				}
			}
			
			if(allfill) {
				score++;
				for(int nr=r-1; nr>=0; nr--) {
					for(int nc=0; nc<4; nc++) {
						green[nr+1][nc] = green[nr][nc];
					}
				}
				
				for(int nc=0; nc<4; nc++) {
					green[0][nc] = false;
				}
				
				r++;
			}
		}
		
		// check over
		count = 0;
		for(int r=0; r<2; r++) {
			for(int c=0; c<4; c++) {
				if(green[r][c]) {
					count++;
					break;
				}
			}
		}
		if(count>0) {
			for(int r=5-count; r>=0; r--) {
				for(int c=0; c<4; c++) {
					green[r+count][c] = green[r][c];
				}
			}
			for(int r=0; r<2; r++) {
				for(int c=0; c<4; c++) {
					green[r][c] = false;
				}
			}
		}
		
		print(type, x, y);
		
		return score;
		
	}

	public static void fill(boolean[][] map, int type, int x, int y) {
		
		if(type == 1) {
			map[x][y] = true;
		}
		else if(type == 2) {
			map[x][y] = true;
			map[x][y+1] = true;
		}
		else {
			map[x][y] = true;
			map[x+1][y] = true;
		}
		
	}
	
	
	public static boolean isempty(boolean[][] map, int type, int x, int y) {
		
		if(type == 1) {
			return (!map[x][y]);
		}
		else if(type == 2) {
			if(isvalid(map, x, y+1)) {
				return (!map[x][y] && !map[x][y+1]);
			}
			else return false;
		}
		else {
			if(isvalid(map, x+1, y)) {
				return (!map[x][y] && !map[x+1][y]);
			}
			else return false;
		}
		
	}
	
	
	public static boolean isvalid(boolean[][] map, int x, int y) {
		
		int maxx = map.length;
		int maxy = map[0].length;
		
		return ( 0<=x && x<maxx && 0<=y && y<maxy );
	}
	
	public static void print(int type, int x, int y) {
		
//		boolean[][] red = new boolean[4][4];
//		
//		fill(red, type, x, y);
//		
//		for(int i=0; i<4; i++) {
//			for(int j=0; j<4; j++) {
//				System.out.print((red[i][j]?1:0) + " ");
//			}
//			System.out.print("  ");
//			for(int j=0; j<6; j++) {
//				System.out.print((blue[i][j]?1:0) + " ");
//			}
//			System.out.println();
//		}
//		System.out.println();
//		
//		for(int i=0; i<6; i++) {
//			for(int j=0; j<4; j++) {
//				System.out.print((green[i][j]?1:0) + " ");
//			}
//			System.out.println();
//		}
//
//		System.out.println();
//		System.out.println();
	}
	
}


