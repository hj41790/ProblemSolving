package swea;

import java.util.Queue;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA_5653 {
	
	static class Point{
		int row;
		int col;
		public Point(int r, int c) {
			this.row = r;
			this.col = c;
		}
	}
	
	static class Cell{
		int state;
		int life;
		int time;
		
		public Cell(int s, int l){
			this.state = s;
			this.life = l;
			this.time = l;
		}
	}
	
	public static final int EMPTY 		= 0;
	public static final int INACTIVE 	= 1;
	public static final int ACTIVE 		= 2;
	public static final int DEAD 		= 3;
	
	public static final int[] dx = {0, 1, 0, -1};
	public static final int[] dy = {-1, 0, 1, 0};
	
	public static Cell[][] map;
	
	public static int TC, N, M, K;
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		TC = Integer.parseInt(br.readLine());
		for(int tc=1; tc<=TC; tc++) {
			
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			map = new Cell[N+370][M+370];
			
			Queue<Point> init = new LinkedList<Point>();
			
			for(int i=0; i<N; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				for(int j=0; j<M; j++) {
					int life = Integer.parseInt(st.nextToken());
					
					if(life > 0) {
						map[i+155][j+155] = new Cell(INACTIVE, life);
						init.add(new Point(i+155, j+155));
					}
					
				}
			}
			
			int res = solve(init);
			System.out.println("#" + tc + " " + res);
			
		}
		

	}

	public static int solve(Queue<Point> q) {
		
		Queue<Point> active, inactive, expand;
		Queue<Point> nActive, nInactive;
		
		int nrow, ncol;
		
		active = new LinkedList<Point>();
		inactive = q;
		
		for(int k=0; k<K; k++) {
			

			//System.out.println("[TIME] " + (k+1));
			
			nActive = new LinkedList<Point>();
			nInactive = new LinkedList<Point>();
			expand = new LinkedList<Point>();
			
			// active
			while(!active.isEmpty()) {
				Point p = active.poll();
				Cell c = map[p.row][p.col];
				
				if(c.life == c.time) {
					// expand
					for(int i=0; i<4; i++) {
						nrow = p.row + dy[i];
						ncol = p.col + dx[i];
						
						Cell tmp = map[nrow][ncol];
						if(tmp == null) {
							
							//System.out.printf("add (%d, %d)\n", nrow-155, ncol-155);
							
							map[nrow][ncol] = new Cell(EMPTY, c.life);
							expand.add(new Point(nrow, ncol));
						}
						else if(tmp.state == EMPTY){
							if(c.life > tmp.life) {
								tmp.life = c.life;
								tmp.time = c.time;
							}
						}
					}
				}
				
				c.time -= 1;
				if(c.time == 0) {
					c.state = DEAD;
				}
				else {
					nActive.add(p);
				}
			}
			
			// inactive
			while(!inactive.isEmpty()) {
				Point p = inactive.poll();
				Cell c = map[p.row][p.col];
				
				c.time -= 1;
				if(c.time == 0) {
					
					//System.out.printf("active (%d, %d)\n", p.row-155, p.col-155);
					
					c.state = ACTIVE;
					c.time = c.life;
					nActive.add(p);
				}
				else {
					nInactive.add(p);
				}
			}
			
			// expand
			while(!expand.isEmpty()) {
				Point p = expand.poll();
				Cell c = map[p.row][p.col];
				c.state = INACTIVE;
				nInactive.add(p);
			}
			
			active = nActive;
			inactive = nInactive;
			
			print();
		}
		
		return (active.size() + inactive.size());
	}
	
	
	public static void print() {
		/*
		for(int i=145; i<165; i++) {
			for(int j=145; j<165; j++) {
				if(map[i][j] == null)
					System.out.print("  ");
				else
					System.out.print(map[i][j].state + " ");
				
			}
			
			System.out.print("   ");
			
			for(int j=145; j<165; j++) {
				if(map[i][j] == null)
					System.out.print("  ");
				else
					System.out.print(map[i][j].life + " ");
			}
			
			System.out.println();
		}
		

		System.out.println();
		*/
	}
	
}
