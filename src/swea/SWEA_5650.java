package swea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 1. 설계 : 0:26:13.42
 * 2. 구현 : 1:42:17.48
 * 3. 총 시간 : 2:08:30.91
 * 
 * 구현할 때 주요 로직은 직접 손코딩하고 구현으로 넘어갈 것
 * bottom-up 보다 top-down 방식으로 코딩하기 
 * ( solve 함수 짜다가 내부 함수 변경하게 되면 번거로워짐 )
 * 
 * */

public class SWEA_5650 {
	
	static class Point{
		int row;
		int col;
		public Point(int r, int c) {
			this.row = r;
			this.col = c;
		}
		public boolean check(int r, int c) {
			if(this.row == r && this.col == c) return true;
			else return false;
		}
	}
	
	static class Block {
		int type;
		int[] block;
		int row;
		int col;
		
		Wormhole worm;
		
		public Block(int t, int row, int col) {
			this.row = row;
			this.col = col;
			this.worm = null;
			
			if(t == -1) {
				this.type = BLACKHOLE;
			}
			else if(t == 0) {
				this.type = EMPTY;
				this.block = null;
			}
			else if(t <= 5) {
				this.type = BLOCK;
				this.block = blocksample[t-1];
			}
			else if(t <= 10) {
				this.type = WORMHOLE;
				if(wormholes[t] == null) {
					wormholes[t] = new Wormhole(row, col);
				}
				else {
					wormholes[t].add(row, col);
				}
				this.worm = wormholes[t];
			}
			else {
				this.type = WALL;
			}
		}
	}
	
	static class Ball{
		
		int firstrow;
		int firstcol;
		
		int row;
		int col;
		int dir;
		
		int score;
		
		public Ball(int r, int c, int d) {
			this.firstrow = r;
			this.firstcol = c;
			this.row = r;
			this.col = c;
			this.dir = d;
			this.score = 0;
		}
		
		public int start() {
			
			int nrow, ncol;
			Block b;
			
			do {
				nrow = this.row + dy[this.dir];
				ncol = this.col + dx[this.dir];
				b = map[nrow][ncol];
			}while(next(b));
			
			return this.score;
		}
		
		public boolean next(Block b) {
			
			this.row = b.row;
			this.col = b.col;
			
			if(b.type == WALL) {
				this.score++;
				this.dir = (this.dir + 2) % 4;
			}
			else if(b.type == BLOCK) {
				
				this.score++;
				
				int diridx = (this.dir + 2) % 4;
				
				if(b.block[diridx] == 0) {
					for(int i=0; i<4; i++) {
						
						if(b.block[i] == 0 && i != diridx) {
							this.dir = i;
							break;
						}
					}
					
				}
				else {
					this.dir = diridx;
				}
			}
			else if(b.type == WORMHOLE) {
				Point p = b.worm.getNextPoint(this.row, this.col);
				this.row = p.row;
				this.col = p.col;
			}
			
//			print(this.row, this.col);
			
			if(b.type == BLACKHOLE || isFirstPoint()) {
				return false;
			}
			
			return true;
		}
		
		private boolean isFirstPoint() {
			if(this.row == this.firstrow && this.col == this.firstcol) return true;
			else return false;
		}
	}
	
	static class Wormhole{
		
		Point p1;
		Point p2;
		
		public Wormhole(int r, int c) {
			p1 = new Point(r, c);
		}
		public void add(int r, int c) {
			p2 = new Point(r, c);
		}
		public Point getNextPoint(int r, int c) {
			if(p1.check(r, c)) return p2;
			else if(p2.check(r, c)) return p1;
			return null;
		}
	}
	
	public static final int WALL = -1;
	public static final int EMPTY = 0;
	public static final int BLOCK = 1;
	public static final int WORMHOLE = 2;
	public static final int BLACKHOLE = 3;
	
	public static final int[] dx = {0, 1, 0, -1};
	public static final int[] dy = {-1, 0, 1, 0};

	public static final int[][] blocksample = new int[][] { {0, 0, 1, 1},
																	{1, 0, 0, 1},
																	{1, 1, 0, 0}, 
																	{0, 1, 1, 0}, 
																	{1, 1, 1, 1}};
	public static Block[][] map;
	public static Wormhole[] wormholes;
	public static int TC, N;
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		TC = Integer.parseInt(br.readLine());
		for(int tc=1; tc<=TC; tc++){
			
			N = Integer.parseInt(br.readLine());
			
			wormholes = new Wormhole[12];
			map = new Block[N+2][N+2];
			
			for(int i=0; i<N+2; i++) {
				map[0][i] = new Block(99, 0, i);
				map[i][0] = new Block(99, i, 0);
				map[N+1][i] = new Block(99, N+1, i);
				map[i][N+1] = new Block(99, i, N+1);
			}
			
			for(int i=1; i<=N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine(), " ");
				for(int j=1; j<=N; j++) {
					map[i][j] = new Block(Integer.parseInt(st.nextToken()), i, j);
				}
			}
			
			int res = solve();
			
			System.out.println("#" + tc + " " + res);
		}
		

	}
	
	public static int solve() {
		
		int maxscore = 0;
		
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=N; j++) {
				if(map[i][j].type == EMPTY) {
					for(int d=0; d<4; d++) {
						Ball b = new Ball(i, j, d);
						int tmp = b.start();
						if(tmp > maxscore) maxscore = tmp;
					}
				}
			}
		}
		
		return maxscore;
		
//		Ball b = new Ball(3, 4, 1);
//		print(3, 4);
//		return b.start();
		
	}
	
	public static void print(int row, int col) {
		
		char[][] m = new char[N+2][N+2];
		
		for(int i=0; i<N+2; i++) {
			m[0][i] = '-';
			m[i][0] = '|';
			m[N+1][i] = '-';
			m[i][N+1] = '|';
		}
		
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=N; j++) {
				if(map[i][j].type == EMPTY) m[i][j] = ' ';
				else m[i][j] = Integer.toString(map[i][j].type).charAt(0);
			}
		}
		
		m[row][col] = 'x';
		
		for(int i=0; i<N+2; i++) {
			for(int j=0; j<N+2; j++) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

}
