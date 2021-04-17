package swea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class SWEA_5644 {
	
	static class Point{
		int row;
		int col;
		public Point(int r, int c) {
			this.row = r;
			this.col = c;
		}
	}
	
	static class BC{
		int num;
		int row;
		int col;
		int size;
		int power;
		
		public BC(int n, int r, int c, int s, int p) {
			this.num = n;
			this.row = r;
			this.col = c;
			this.size = s;
			this.power = p;
		}
	}
	
	static class MAP{
		ArrayList<BC> bclist;
		public MAP() {
			this.bclist = new ArrayList<BC>();
		}
		
		public int getSize() {
			return this.bclist.size();
		}
	}
	
	static class User{
		int row;
		int col;
		ArrayList<BC> available;
		int[] moveInfo;
		
		public User(int r, int c, int[] m) {
			this.row = r;
			this.col = c;
			this.available = null;
			this.moveInfo = m;
		}
		
		public void move(int idx) {
			int dir = this.moveInfo[idx];
			
			this.row = this.row + dy[dir];
			this.col = this.col + dx[dir];
			
			available = map[this.row][this.col].bclist;
		}
	}
	
	public static final int[] dx = {0, 0, 1, 0, -1};
	public static final int[] dy = {0, -1, 0, 1, 0};
	public static final Point[] userpoint = { new Point(1,1), new Point(10, 10)};
	
	public static int TC, M, A;

	public static MAP[][] map;
	public static User[] users;
	public static BC[] BCList;
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		TC = Integer.parseInt(br.readLine());
		for(int tc=1; tc<=TC; tc++) {
			
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			
			M = Integer.parseInt(st.nextToken());
			A = Integer.parseInt(st.nextToken());
			
			map = new MAP[11][11];
			for(int i=1; i<=10; i++) {
				for(int j=1; j<=10; j++) {
					map[i][j] = new MAP();
				}
			}

			users = new User[2];
			for(int i=0; i<2; i++) {

				int[] moveinfo = new int[M+1];
				
				st = new StringTokenizer(br.readLine(), " ");
				
				moveinfo[0] = 0;
				for(int j=1; j<=M; j++) {
					moveinfo[j] = Integer.parseInt(st.nextToken());
				}
				users[i] = new User(userpoint[i].row, userpoint[i].col, moveinfo);
			}
			
			BCList = new BC[A];
			for(int i=0; i<A; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int c = Integer.parseInt(st.nextToken());
				int r = Integer.parseInt(st.nextToken());
				int s = Integer.parseInt(st.nextToken());
				int p = Integer.parseInt(st.nextToken());
				BC bc = new BC(i, r, c, s, p);
				BCList[i] = bc;
				registerBC(bc);
			}
			
			int res = solve();
			
			System.out.println("#" + tc + " " + res);
			
		}

	}
	
	public static int solve() {
		
		int res = 0;
		
		for(int t=0; t<=M; t++) {

			//System.out.println(t + " : ");
			for(int i=0; i<2; i++) {
				users[i].move(t);
				//System.out.println("user " + i + " : ("+users[i].row + ", " + users[i].col + ") : "+users[i].available.size());
			}
			
			res += find_max_power();
			//System.out.println();
		}
		
		return res;
	}
	
	public static int find_max_power() {
		
		int[] choice = new int[A];
		
		ArrayList<BC> bcA = users[0].available;
		ArrayList<BC> bcB = users[1].available;
		
		int max = 0;
		
		if(bcA.size() == 0) {
			
			for(int i=0; i<bcB.size(); i++) {
				int pwr = BCList[bcB.get(i).num].power;
				if(pwr > max) max = pwr;
			}
			
		}
		else if(bcB.size() == 0) {
			
			for(int i=0; i<bcA.size(); i++) {
				int pwr = BCList[bcA.get(i).num].power;
				if(pwr > max) max = pwr;
			}
		}
		else {

			for(int i=0; i<bcA.size(); i++) {
				choice[bcA.get(i).num]++;
				
				for(int j=0; j<bcB.size(); j++) {

					choice[bcB.get(j).num]++;
					
					int tmp = 0;
					for(int k=0; k<A; k++) {
						if(choice[k] > 0) tmp += BCList[k].power;
					}
					if(tmp > max) {
						max = tmp;
						//System.out.println("" + tmp);
					}
					
					choice[bcB.get(j).num]--;
					
				}

				choice[bcA.get(i).num]--;
			}
			
		}
		
		
		return max;
	}
	
	public static void registerBC(BC bc) {
		
		int[][] visit = new int[11][11];
		Queue<Point> q = new LinkedList<Point>();
		
		
		map[bc.row][bc.col].bclist.add(bc);
		visit[bc.row][bc.col] = 1; 
		q.add(new Point(bc.row, bc.col));
		
		while(!q.isEmpty()) {
			Point p = q.poll();
			
			if(visit[p.row][p.col] <= bc.size) {

				for(int i=1; i<=4; i++) {
					int nrow = p.row + dy[i];
					int ncol = p.col + dx[i];

					if(idx_valid(nrow, ncol) && visit[nrow][ncol] == 0) {
						visit[nrow][ncol] = visit[p.row][p.col] + 1;
						map[nrow][ncol].bclist.add(bc);
						q.add(new Point(nrow, ncol));
					}
				}	
			}
		}
		
//		for(int i=1; i<11; i++) {
//			for(int j=1; j<11; j++) {
//				System.out.print(visit[i][j] + " ");
//			}
//			System.out.println();
//		}
//		System.out.println();
		
	}
	
	public static boolean idx_valid(int r, int c) {
		if(1<=r && r<=10 && 1<=c && c<=10) return true;
		else return false;
	}

}
