package swea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 1. 설계 : 00:30:22
 * 2. 구현 : 00:30:35
 * 3. 전체 : 01:00:57
 * 
 * */

public class SWEA_5650_1 {
	
	public static final int[][] blocks = { 	{0, 0, 0, 0}, //dummy
											{0, 0, 1, 1},
											{1, 0, 0, 1},
											{1, 1, 0, 0}, 
											{0, 1, 1, 0},
											{1, 1, 1, 1}};
	
	public static final int[] dx = {0, 1, 0, -1};
	public static final int[] dy = {-1, 0, 1, 0};
	
	public static final int BLACKHOLE = -1;
	public static final int WALL = 99;
	
	public static int TC, N;
	public static int[][] map;
	public static Wormhole[] wormholes;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		TC = Integer.parseInt(br.readLine());
		for(int tc=1; tc<=TC; tc++) {
			
			N = Integer.parseInt(br.readLine());
			
			wormholes = new Wormhole[11];
			for(int i=6; i<=10; i++) {
				wormholes[i] = new Wormhole();
			}
			
			map = new int[N+2][N+2];
			for(int i=0; i<N+2; i++) {
				map[i][0] = WALL;
				map[0][i] = WALL;
				map[N+1][i] = WALL;
				map[i][N+1] = WALL;
			}
			
			for(int i=1; i<=N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine(), " ");
				for(int j=1; j<=N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					if(map[i][j] >= 6) {
						wormholes[map[i][j]].add(new Pair(i, j));
					}
				}
			}
			
			System.out.println("#" + tc + " " + solve());
		}
	}
	
	public static int solve() {
		
		int maxscore = 0;
		
		for(int i=1; i<=N; i++) {
			for(int j=1; j<=N; j++) {
				
				if(map[i][j] != 0) continue;
				
				for(int d=0; d<4; d++) {
					Ball b = new Ball(i, j, d);
					gamestart(b);
					if(maxscore < b.score) maxscore = b.score;
				}
			}
		}
		
		return maxscore;
	}
	
	public static void gamestart(Ball b) {
		
		int rinit = b.r;
		int cinit = b.c;
		int nr, nc;
		
		while(true) {
			
			nr = b.r + dy[b.dir];
			nc = b.c + dx[b.dir];
			
			if(map[nr][nc] == BLACKHOLE || (nr==rinit && nc==cinit)) {
				break;
			}
			else if(map[nr][nc] == WALL) {
				b.dir = (b.dir + 2) % 4;
				b.score++;
			}
			else if(map[nr][nc] >= 6) {
				Wormhole h = wormholes[map[nr][nc]];
				Pair p = h.getNext(nr, nc);
				nr = p.r;
				nc = p.c;
			}
			else if(map[nr][nc] > 0) {
				b.dir = nextdir(map[nr][nc], b.dir);
				b.score++;
			}
			
			b.r = nr;
			b.c = nc;
		}
		
	}
	
	public static int nextdir(int btype, int entrydir){
		
		int[] block = blocks[btype];
		int oppositeIdx = (entrydir + 2) % 4;
		int nextIdx;
		
		if(block[oppositeIdx] > 0) {
			nextIdx = oppositeIdx;
		}
		else {
			nextIdx = (oppositeIdx+1)%4;
			if(block[nextIdx] != 0) nextIdx = (oppositeIdx+3)%4;
		}
		
		return nextIdx;
	}

}

class Ball{
	int r, c, dir, score;
	public Ball(int _r, int _c, int _d) {
		this.r = _r;
		this.c = _c;
		this.dir = _d;
		this.score = 0;
	}
}

class Wormhole{
	Pair p1, p2;
	public Wormhole() {
		p1 = null;
		p2 = null;
	}
	
	public void add(Pair p) {
		if(p1 == null) p1 = p;
		else if(p2 == null) p2 = p;
	}
	
	public Pair getNext(int r, int c) {
		
		if(p1.r == r && p1.c == c) return p2;
		else if(p2.r == r && p2.c == c) return p1;
		else return null;
		
	}
}


class Pair{
	int r, c;
	public Pair(int _r, int _c) {
		this.r = _r;
		this.c = _c;
	}
}