package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * 1. 설계 : 00:21:18
 * 2. 구현 : 01:26:13
 * 3. 전체 : 01:47:31
 * */

public class BOJ_19238 {
	
	public static final int[] dx = {0, -1, 1, 0};
	public static final int[] dy = {-1, 0, 0, 1};
	
	public static Taxi taxi;
	public static Client[] clients;
	
	public static int[][] map;
	public static int[][] visit;
	
	public static int N, M, F;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int sr, sc, dr, dc;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		F = Integer.parseInt(st.nextToken());
		
		clients = new Client[M+1];
		map = new int[N+1][N+1];
		visit = new int[N+1][N+1];
		
		for(int i=1; i<=N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j=1; j<=N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] > 0) map[i][j] = -1;
			}
		}
		
		st = new StringTokenizer(br.readLine(), " ");
		
		sr = Integer.parseInt(st.nextToken());
		sc = Integer.parseInt(st.nextToken());
		taxi = new Taxi(sr, sc, F);
		
		for(int i=1; i<=M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			sr = Integer.parseInt(st.nextToken());
			sc = Integer.parseInt(st.nextToken());
			dr = Integer.parseInt(st.nextToken());
			dc = Integer.parseInt(st.nextToken());
			
			clients[i] = new Client(sr, sc, dr, dc);
			map[sr][sc] = i;
		}
		
		System.out.println(solve());
		
	}
	
	public static int solve() {
		
		int[] init_visit = new int[N+1];
		Queue<Pos> q = new LinkedList<Pos>();
		Queue<Client> cq = new LinkedList<Client>();
		
		int subfuel=0, clientidx=0, nr, nc;
		
		print();
		
		for(int cnt=0; cnt<M; cnt++) {
			
			// step 1 : find client
			// initialize
			q.clear();
			cq.clear();
			subfuel = 9999;
			for(int i=1; i<=N; i++) {
				System.arraycopy(init_visit, 0, visit[i], 0, N+1);
			}
			
			visit[taxi.r][taxi.c] = 1;
			q.add(new Pos(taxi.r, taxi.c));
			while(!q.isEmpty()) {
				Pos p = q.poll();
				
				if(visit[p.r][p.c] > subfuel) break;
				
				if(map[p.r][p.c] > 0) {
					
					subfuel = (visit[p.r][p.c] < subfuel) ? visit[p.r][p.c] : subfuel; 
					if(visit[p.r][p.c] == subfuel) {
						cq.add(clients[map[p.r][p.c]]);
					}
				}
				
				for(int i=0; i<4; i++) {
					nr = p.r + dy[i];
					nc = p.c + dx[i];
					if(isvalid(nr, nc) && visit[nr][nc] == 0) {
						visit[nr][nc] = visit[p.r][p.c] + 1;
						q.add(new Pos(nr, nc));
					}
				}
			}

			Client c = cq.poll();
			while(!cq.isEmpty()) {
				
				Client tmp = cq.poll();
				if(tmp.src_r < c.src_r) c = tmp;
				else if(tmp.src_r == c.src_r && tmp.src_c < c.src_c) c = tmp;
				
			}
			
			// check fuel
			subfuel--;
			if(c == null) return -1;
			else if(taxi.fuel - subfuel <= 0) return -1;
			
			taxi.r = c.src_r;
			taxi.c = c.src_c;
			taxi.fuel -= subfuel;
			map[c.src_r][c.src_c] = 0;
			
			// step 2 : find destination
			// initialize
			subfuel = -1;
			q.clear();
			for(int i=1; i<=N; i++) {
				System.arraycopy(init_visit, 0, visit[i], 0, N+1);
			}
			
			visit[taxi.r][taxi.c] = 1;
			q.add(new Pos(taxi.r, taxi.c));
			while(!q.isEmpty()) {
				
				Pos p = q.poll();
				if(p.r == c.dest_r && p.c == c.dest_c) {
					subfuel = visit[p.r][p.c] - 1;
					break;
				}

				for(int i=0; i<4; i++) {
					nr = p.r + dy[i];
					nc = p.c + dx[i];
					if(isvalid(nr, nc) && visit[nr][nc] == 0) {
						visit[nr][nc] = visit[p.r][p.c] + 1;
						q.add(new Pos(nr, nc));
					}
				}
				
			}
			
//			System.out.println("  dest : " + subfuel);
			
			// check fuel
			if(subfuel < 0) return -1;
			else if(taxi.fuel - subfuel < 0) return -1;
			
			taxi.r = c.dest_r;
			taxi.c = c.dest_c;
			taxi.fuel += subfuel;
	
			print();
		}
		
		return taxi.fuel;
	}
	
	public static boolean isvalid(int r, int c) {
		
		return ( 0<r && r<=N && 0<c && c<=N && map[r][c]>=0 );
		
	}
	
	
	public static void print() {
		
//		for(int i=1; i<=N; i++) {
//			for(int j=1; j<=N; j++) {
//				System.out.printf("%2d ", map[i][j]);
//			}
//			System.out.println();
//		}
//		System.out.println();
		
	}
	
	

}

class Client{
	int src_r, src_c;
	int dest_r, dest_c;
	public Client(int r1, int c1, int r2, int c2) {
		this.src_r = r1;
		this.src_c = c1;
		this.dest_r = r2;
		this.dest_c = c2;
	}
}

class Taxi{
	int r, c;
	int fuel;
	public Taxi(int _r, int _c, int _f) {
		this.r = _r;
		this.c = _c;
		this.fuel = _f;
	}
}

class Pos{
	int r, c;
	public Pos(int _r, int _c) {
		this.r = _r; 
		this.c = _c;
	}
}