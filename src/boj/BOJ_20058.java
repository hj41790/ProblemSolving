package boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_20058 {
	
	
	public static final int[] dx = {0, 1, 0, -1};
	public static final int[] dy = {-1, 0, 1, 0};
	
	
	public static int mapsize, N, Q;
	public static int[][] map, nmap;
	
	public static int[] request;
	
	public static BufferedReader br;
	public static StringTokenizer st;
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		
		mapsize = (int)Math.pow((double)2, (double)N);
		
		map = new int[mapsize][mapsize];
		nmap = new int[mapsize][mapsize];
		
		for(int i=0; i<mapsize; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j=0; j<mapsize; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		st = new StringTokenizer(br.readLine(), " ");
		
		request = new int[Q];
		for(int i=0; i<Q; i++) {
			request[i] = Integer.parseInt(st.nextToken());
		}
		
		solve();
	}
	
	public static void solve() {
		
		int L, size, subsize, rbase, cbase, nx, ny;
		
		for(int step=0; step<Q; step++) {
			
			L = request[step];
			size = (int)Math.pow((double)2, (double)L);
			subsize = (int)Math.pow((double)2, (double)N-L);
			
			// rotate
			for(int i=0; i<subsize; i++) {
				
				rbase = size * i;
				
				for(int j=0; j<subsize; j++) {
					
					cbase = size * j;
					
					rotate(rbase, cbase, size);
				}
			}
			
			// array copy
			for(int i=0; i<mapsize; i++) {
				System.arraycopy(nmap[i], 0, map[i], 0, mapsize);
			}
			
			// calculate
			for(int i=0; i<mapsize; i++) {
				for(int j=0; j<mapsize; j++) {
					
					if(map[i][j] == 0) continue;
					
					int count = 0;
					for(int dir=0; dir<4; dir++) {
						
						nx = j + dx[dir];
						ny = i + dy[dir];
						
						if(isvalid(nx, ny) && nmap[ny][nx] > 0) count++;
					}
					
					if(count<3) map[i][j]--;
					
				}
			}
		}
		
//		print();
		
		int totalice = 0;
		int totalblock = 0;
		int max = 0;
		
		boolean[][] visit = new boolean[mapsize][mapsize];
		for(int i=0; i<mapsize; i++) {
			for(int j=0; j<mapsize; j++) {
				totalice += map[i][j];
				if(!visit[i][j] && map[i][j] > 0) {
					totalblock = bfs(i, j, visit);
					max = (totalblock > max) ? totalblock:max;
				}
			}
		}
		
		System.out.println(totalice);
		System.out.println(max);
	}

	public static void rotate(int rbase, int cbase, int size) {
		
		int row, col, nrow, ncol;
		
		for(int r=0; r<size; r++) {
			
			row = rbase + r;
			ncol = cbase + (size-1-r);
			
			for(int c=0; c<size; c++) {
				
				col = cbase + c;
				nrow = rbase + c;
				
				nmap[nrow][ncol] = map[row][col];
			}
			
		}
		
		
	}
	
	public static int bfs(int row, int col, boolean[][] visit) {
		
		Queue<Point> q = new LinkedList<Point>();
		
		int nrow, ncol;
		int count = 1;
		
		visit[row][col] = true;
		q.add(new Point(row, col));
		
		while(!q.isEmpty()) {
			
			Point p = q.poll();
			
			for(int i=0; i<4; i++) {
				
				nrow = p.row + dy[i];
				ncol = p.col + dx[i];
				
				if(isvalid(nrow, ncol)&& !visit[nrow][ncol] && map[nrow][ncol] > 0) {
					count++;
					visit[nrow][ncol] = true;
					q.add(new Point(nrow, ncol));
				}
				
			}
			
		}
		
		return count;
	}
	
	public static boolean isvalid(int x, int y) {
		if(0<=x && x<mapsize && 0<=y && y<mapsize) return true;
		else return false;
	}
	
	public static void print() {
		for(int i=0; i<mapsize; i++) {
			for(int j=0; j<mapsize; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
}

class Point{
	int row, col;
	public Point(int r, int c) {
		this.row = r;
		this.col = c;
	}
}
