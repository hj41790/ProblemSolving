package swea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class SWEA_5648 {
	
	static class Atom{
		int x, y;
		int energy;
		int dir;
		boolean alive;
		public Atom(int _x, int _y, int e, int d) {
			this.x = _x;
			this.y = _y;
			this.energy = e;
			this.alive = true;
			
			switch(d) {
			case 0 : this.dir = 0; break;
			case 1 : this.dir = 2; break;
			case 2 : this.dir = 3; break;
			case 3 : this.dir = 1; break;
			}
		}
	}	

	public static final int[] dx = {0, 1, 0, -1};
	public static final int[] dy = {1, 0, -1, 0};
	
	public static int TC, N;
	public static int min_x, min_y, max_x, max_y;
	
	public static Atom[] atoms;

	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		TC = Integer.parseInt(br.readLine());
		for(int tc=1; tc<=TC; tc++) {
			
			min_x = Integer.MAX_VALUE;
			min_y = Integer.MAX_VALUE;
			max_x = Integer.MIN_VALUE;
			max_y = Integer.MIN_VALUE;

			N = Integer.parseInt(br.readLine());
			
			atoms = new Atom[N];
			
			for(int i=0; i<N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine(), " ");
				
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int d = Integer.parseInt(st.nextToken());
				int k = Integer.parseInt(st.nextToken());
				
				min_x = (x < min_x) ? x : min_x;
				min_y = (y < min_y) ? y : min_y;
				max_x = (x > max_x) ? x : max_x;
				max_y = (y > max_y) ? y : max_y;
				
				x = x * 2 + 2000;
				y = y * 2 + 2000;
				
				Atom a = new Atom(x, y, k, d);
				
				atoms[i] = a;
			}
			
			min_x = min_x * 2 + 2000;
			min_y = min_y * 2 + 2000;
			max_x = max_x * 2 + 2000;
			max_y = max_y * 2 + 2000;
			
			int totalenergy = solve();
			System.out.println("#" + tc + " " + totalenergy);
			
		}
	}
	
	public static int solve() {
		
		int total = 0;
		int[][] map = new int[4001][4001];
		
		while(true) {

			int alivecnt = 0;
			for(int i=0; i<N; i++) {
				Atom a = atoms[i];
				map[a.y][a.x] = 0;
				if(a.alive) alivecnt++;
			}			
			if(alivecnt < 2) break;


			for(int i=0; i<N; i++) {
				
				Atom a = atoms[i];
				
				if(!a.alive) continue;
				
				int nx = a.x + dx[a.dir];
				int ny = a.y + dy[a.dir];
				
				if(!isvalid(nx, ny)) {
					a.alive = false;
					continue;
				}
				
				a.x = nx;
				a.y = ny;
				map[ny][nx]++;
				
			}
			
			for(int i=0; i<N; i++) {
				Atom a = atoms[i];
				if(a.alive && map[a.y][a.x] > 1) {
					a.alive = false;
					total += a.energy;
				}
			}

		}
		
		return total;
		
	}

	public static boolean isvalid(int x, int y) {
		if(min_x <= x && x <= max_x && min_y <= y && y <= max_y) return true;
		else return false;
	}
}

