package swea;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class SWEA_4013 {
	
	static int TC;
	static int K;

	static ArrayList<LinkedList<Integer>> M;
	
	static final int LEFT = 6;
	static final int RIGHT = 2;
	
	static final int CLOCKWISE = 1;
	static final int COUNTERCLOCK = -1;
	
	static final int[] score = new int[] {1, 2, 4, 8};

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		int nM, d, prev;
		int[] dir;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		TC = Integer.parseInt(br.readLine());
		
		for(int cnt=1; cnt<=TC; cnt++) {
			
			M = new ArrayList<LinkedList<Integer>>();
			K = Integer.parseInt(br.readLine());
			
			for(int i=0; i<4; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine(), " ");
				LinkedList<Integer> list = new LinkedList<Integer>();
				
				while(st.hasMoreTokens()) {
					list.add(Integer.parseInt(st.nextToken()));
				}
				
				M.add(list);
			}
			
			while(K-- > 0) {

				
				StringTokenizer st = new StringTokenizer(br.readLine(), " ");
				nM = Integer.parseInt(st.nextToken()) - 1;
				d = Integer.parseInt(st.nextToken());
				dir = new int[] {0, 0, 0, 0};
				
				dir[nM] = d;
				prev = M.get(nM).get(LEFT);
				
				//left
				for(int i=nM-1; i>=0; i--) {
					if(M.get(i).get(RIGHT) != prev) {
						dir[i] = dir[i+1] > 0 ? COUNTERCLOCK : CLOCKWISE;
						prev = M.get(i).get(LEFT);
					}
					else {
						break;
					}
				}
				

				prev = M.get(nM).get(RIGHT);
				//right
				for(int i=nM+1; i<4; i++) {
					if(M.get(i).get(LEFT) != prev) {
						dir[i] = dir[i-1] > 0 ? COUNTERCLOCK : CLOCKWISE;
						prev = M.get(i).get(RIGHT);
					}
					else {
						break;
					}
				}
				
				for(int i=0; i<4; i++) {
					LinkedList<Integer> l = M.get(i);
					if(dir[i] == CLOCKWISE) {
						l.add(0, l.removeLast());
					}
					else if(dir[i] == COUNTERCLOCK) {
						l.add(l.removeFirst());
					}
				}
				
				
			}
			
			int res = 0;
			for(int i=0; i<4; i++) {
				if(M.get(i).get(0) > 0) res += score[i];
			}
			
			System.out.println("#"+cnt+" "+res);
			
		}
		
		
		
	}

}
