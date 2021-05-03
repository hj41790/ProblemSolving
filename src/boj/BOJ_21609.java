package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_21609 {

	public static final int[] dx = {0, 1, 0, -1};
	public static final int[] dy = {-1, 0, 1, 0};
	
	public static final int ROCK = -1;
	public static final int RAINBOW = -2;
	
	public static int N, M;
	
	public static int[][] map;
	
	public static boolean[][] maxMap;
	public static int maxCount;
	public static int maxRainbow;
	public static Pair maxPoint;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 0) map[i][j] = RAINBOW;
			}
		}
		
		System.out.println(solve());
		
	}
	
	public static int solve() {
		int score = 0;
		
		while(true) {
			
			maxMap = null;
			maxCount = 0;
			maxRainbow = 0;
			maxPoint = null;
			
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					if(map[i][j] > 0) find_maxMap(i, j);
				}
			}
			
			if(maxCount < 2) break;
			
			score += maxCount * maxCount;
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					if(maxMap[i][j]) map[i][j] = 0;
				}
			}

			print("-----\n보석 캔 후 : ");
			
			drop();
			print("drop :");
			
			rotate();
			print("rotate:");
			
			drop();
			print("drop :");
		}
		
		return score;
	}
	
	
	public static void find_maxMap(int r, int c) {
		
		if(maxMap != null && maxMap[r][c]) return;
		
		Queue<Pair> pq = new LinkedList<Pair>();
		
		boolean[][] visit = new boolean[N][N];
		int count = 0;
		int rainbow = 0;
		Pair point = null;
		
		int nr, nc;
		
		int color = map[r][c];
		pq.add(new Pair(r, c));
		visit[r][c] = true;
		while(!pq.isEmpty()) {
			Pair p = pq.poll();
			
			count++;
			if(map[p.r][p.c] == RAINBOW) rainbow++;
			
			for(int i=0; i<4; i++) {
				nr = p.r + dy[i];
				nc = p.c + dx[i];
				if(!isvalid(nr, nc) || visit[nr][nc]) continue;
				if(map[nr][nc] == color || map[nr][nc] == RAINBOW) {
					visit[nr][nc] = true;
					pq.add(new Pair(nr, nc));
				}
			}
			
		}
		
		if(count < 2) return;
		
		point = find_point(visit);
		
		boolean change = false;
		if(count > maxCount) change = true;
		else if(count == maxCount) {
			if(rainbow > maxRainbow) change = true;
			else if(rainbow == maxRainbow) {
				if(point.r > maxPoint.r) change = true;
				else if(point.r == maxPoint.r) {
					if(point.c > maxPoint.c) change = true;
				}
			}
		}
		
		if(change) {
			maxMap = visit;
			maxCount = count;
			maxRainbow = rainbow;
			maxPoint = point;
		}
		
	}
	
	public static Pair find_point(boolean[][] visit) {
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(visit[i][j] && map[i][j] != RAINBOW) {
					return new Pair(i, j);
				}
			}
		}
		
		return null;
	}
	
	public static void drop() {
		
		int rbase;
		
		for(int c=0; c<N; c++) {
			rbase = 0;
			for(int r=0; r<N; r++) {
				if(map[r][c] == ROCK) rbase = r+1;
				else if(map[r][c] == 0) {
					
					for(int k=r; k>rbase; k--) {
						map[k][c] = map[k-1][c];
					}
					map[rbase][c] = 0;
					
				}
			}
		}
		
	}
	
	public static void rotate() {
		
		int[][] nmap = new int[N][N];
		
		for(int r=0; r<N; r++) {
			for(int c=0; c<N; c++) {
				nmap[r][c] = map[c][N-1-r];
			}
		}
		
		map = nmap;
	}
	
	public static boolean isvalid(int r, int c) {
		return (0<=r && r<N && 0<=c && c<N);
	}
	
	public static void print(String s) {
		
//		System.out.println(s);
//		for(int r=0; r<N; r++) {
//			for(int c=0; c<N; c++) {
//				System.out.printf("%2d ", map[r][c]);
//			}
//			System.out.println();
//		}
//		System.out.println();
	}

}

class Pair{
	int r, c;
	public Pair(int _r, int _c) {
		this.r = _r;
		this.c = _c;
	}
}