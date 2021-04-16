package swea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

/*
 * 1. 필기 	: 49:39
 * 2. 코딩1 	: 51:36 - 시간초과
 * 3. 코딩2 	: 32:46
 * 4. 전체 	: 2:14:02
 * 
 * Hashmap, Set 같이 key 가지고 노는 애들은 순회같은거 하면 안되겠다 시간 너무 오래걸림
 * 있어보이게 풀어보려다 망한 케이스인듯
 * 
 * */


public class SWEA_2382 {
	
	static class Coord{
		int row;
		int col;
		public Coord(int r, int c) {
			this.row = r;
			this.col = c;
		}
		
		public boolean equals(Object obj) {
			if(obj instanceof Coord) {
				Coord c = (Coord)obj;
				if(c.row == this.row && c.col == this.col) return true;
			}
			return false;
		}
		
		public int hashCode() {
			return (String.valueOf(row)+String.valueOf(col)).hashCode();
		}
	}
	
	static class Cluster{
		int row;
		int col;
		int dir;
		int count;
		
		public Cluster(int r, int c, int cnt, int d) {
			this.row = r;
			this.col = c;
			this.count = cnt;
			
			switch(d) {
				case 1: this.dir = 0; break;
				case 2:	this.dir = 2; break;
				case 3: this.dir = 3; break;
				case 4: this.dir = 1; break;
			}
		}
	}
	
	public static final int[] dx = {0, 1, 0, -1};
	public static final int[] dy = {-1, 0, 1, 0};
	
	public static int[][] aaa;
	
	public static LinkedList<Cluster> clist;
	public static int TC, N, M, K;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		int row, col, cnt, dir;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		TC = Integer.parseInt(br.readLine());
		for(int tc=1; tc<=TC; tc++) {
			
			clist = new LinkedList<Cluster>();
			
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			
			N = Integer.parseInt(st.nextToken()); 
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			
			for(int i=0; i<K; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				
				row = Integer.parseInt(st.nextToken());
				col = Integer.parseInt(st.nextToken());
				cnt = Integer.parseInt(st.nextToken());
				dir = Integer.parseInt(st.nextToken());
				
				clist.add(new Cluster(row, col, cnt, dir));
				
			}
			
			System.out.println("#" + tc + " " + solve());
			
		}

	}
	
	public static int solve() {
		
		int res = 0;
		int nrow, ncol;
		
		Cluster clr;
		
		ArrayList<Cluster> rem;

		Iterator<Cluster> it_clr;
		
		Cluster[][] map;
		int[][] map_max;
		
		for(int i=0; i<M; i++) {
			
			rem = new ArrayList<Cluster>();
			map = new Cluster[N][N];
			map_max = new int[N][N];			

			it_clr = clist.iterator();
			while(it_clr.hasNext()) {
				clr = it_clr.next();

				nrow = clr.row + dy[clr.dir];
				ncol = clr.col + dx[clr.dir];
				
				// 값 갱신
				clr.row = nrow;
				clr.col = ncol;
				
				if(idx_wall(nrow, ncol)) {
					clr.count /= 2;
					clr.dir = (clr.dir+2)%4;
				}
				
				// 비교 및 등록
				if(map[nrow][ncol] == null) {
					map_max[nrow][ncol] = clr.count;
					map[nrow][ncol] = clr;
				}
				else {
					
					if(clr.count > map_max[nrow][ncol]) {
						map_max[nrow][ncol] = clr.count;
						clr.count += map[nrow][ncol].count;
						rem.add(map[nrow][ncol]);
						map[nrow][ncol] = clr;
					}
					else {
						map[nrow][ncol].count += clr.count;
						rem.add(clr);
					}
					
				}
				
			}
			
			clist.removeAll(rem);
			
		}

		
		
		
		
		// check all count
		it_clr = clist.iterator();
		while(it_clr.hasNext()) {
			res += it_clr.next().count;
		}
		
		return res;
	}
	
	public static int solve1() {
		
		int res = 0;
		int nrow, ncol;
		
		Cluster clr, max, tmp;
		Coord crd;
		
		HashMap<Coord, ArrayList<Cluster>> map = new HashMap<Coord, ArrayList<Cluster>>();
		
		ArrayList<Cluster> arr;
		
		Iterator<Coord> it_crd;
		Iterator<Cluster> it_clr, it_arr;

		for(int i=0; i<M; i++) {

			it_clr = clist.iterator();
			while(it_clr.hasNext()) {
				clr = it_clr.next();
				
				nrow = clr.row + dy[clr.dir];
				ncol = clr.col + dx[clr.dir];
				
				crd = new Coord(nrow, ncol);
				
				if(idx_wall(nrow, ncol)) {
					clr.count /= 2;
					clr.dir = (clr.dir+2)%4;
				}
				
				clr.row = nrow;
				clr.col = ncol;

				if(!map.containsKey(crd)) {
					map.put(crd, new ArrayList<Cluster>());
				}
				arr = map.get(crd);
				
				arr.add(clr);
			}
			
			it_crd = map.keySet().iterator();
			while(it_crd.hasNext()) {
				crd = it_crd.next();
				
				arr = map.get(crd);
				if(arr.size() > 1) {
					max = arr.get(0);
					
					it_arr = arr.iterator();
					while(it_arr.hasNext()) {
						tmp = it_arr.next();
						if(tmp.count > max.count) max = tmp;
					}
					
					it_arr = arr.iterator();
					while(it_arr.hasNext()) {
						tmp = it_arr.next();
						if(tmp != max) {
							max.count += tmp.count;
							clist.remove(tmp);
						}
					}
				}
				
				arr.clear();
			}
			
		}		
		
		// check all count
		it_clr = clist.iterator();
		while(it_clr.hasNext()) {
			res += it_clr.next().count;
		}
		
		return res;
	}

	
	public static boolean idx_wall(int row, int col) {
		if(row == 0 || row == N-1 || col == 0 || col == N-1) return true;
		else return false;
	}
}
