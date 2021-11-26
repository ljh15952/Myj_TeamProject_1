package Path_Finder_Package;

import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.ArrayList;

public class pathfinder {

	// 텍스트 파일에 있는 역의 정보를 복사하고 붙여넣기 한다. 이후 시작역과 도착역을 입력한다.

	public static void main(String[] args) {
		final int MAX_V = 2000;
		final int INF = 100000000;
		int K, N; // 시작역, 도착역
		int u, v, w, l; // u역에서 v역 까지 가는데 걸리는 시간 w, l은 임의로 넣은 몇 호선 정보
		Scanner scan = new Scanner(System.in);


		ArrayList<tuple>[] adj = new ArrayList[MAX_V]; // 3가지의 int형 정보를 담는 ArrayList를 가리키는 배열 adj

		for (int i = 0; i < MAX_V; i++)
			adj[i] = new ArrayList<>();

		for (int i = 0; i < 139; i++) {
			u = scan.nextInt(); v = scan.nextInt(); w = scan.nextInt(); l = scan.nextInt(); // u, v, w, l 총 139가지의 노선들 입력

			adj[u-1].add(new tuple(v-1, w, l)); // adj[출발역].add(new tuple(도착역-1, 시간, 호선)
			adj[v-1].add(new tuple(u-1, w, l)); // 이대로만 하면 지하철이 한쪽 방향으로 밖에 못 가므로 반대 방향 노선도 만들어줌
		}

		K = scan.nextInt(); N = scan.nextInt(); //시작역 , 도착역 입력
		K--; N--;

		int[] dist = new int[MAX_V]; // 시작역에서 각 역까지의 거리에 대한 정보를 저장
		for (int i = 0; i < MAX_V; i++)
			dist[i] = INF; // 초기값은 모든역의 거리를 무한하게 설정

		pair[] prev = new pair[MAX_V]; // 어떤 역들을 거쳐서 왔는지 저장하기 위한 배열 prev

		for (int i = 0; i < MAX_V; i++) {
			prev[i] = new pair(0, 0);
			prev[i].x = -1;
		}
		boolean[] visited = new boolean[MAX_V];
		for (int i = 0; i < MAX_V; i++)
			visited[i] = false;


		PriorityQueue<pair> PQ = new PriorityQueue<>(); // 우선순위 큐 PQ, PQ에는 (시작역에서 지정역 까지의 거리, 지정역 번호) 가 저장된다.

		// 다익스트라 알고리즘

		dist[K] = 0;
		PQ.add(new pair(0 ,K));

		while (!PQ.isEmpty()) {
			int curr;
			do {
				curr = PQ.peek().y;
				PQ.remove();
			} while(!PQ.isEmpty() && visited[curr]); // 이미 방문한 정점이면 한번 더 꺼낸다

			if (visited[curr]) break;

			visited[curr] = true;


			for (tuple p : adj[curr]) {

				int next = p.get_first(), d = p.get_second();
				//거리가 갱신될 경우 PQ에 새로 넣음
				if (dist[next] > dist[curr] + d) {
					dist[next] = dist[curr] + d;
					prev[next].x = curr;
					prev[next].y = p.get_third();
					PQ.add(new pair(dist[next], next));
				}
			}
		}


		// 어떠한 경로를 거쳐왔는지 출력
		int curr = N;
		Stack<pair> st = new Stack<>(); // 스택을 이용함

		while (prev[curr].x != -1) { // prev 이용
			st.push(new pair(curr + 1, prev[curr].y));
			curr = prev[curr].x;
		}

		st.push(new pair(K + 1, prev[K].y));

		int prev_line = st.peek().y;

		while(!st.empty()) {
			if (prev_line != st.peek().y) { // 호선 정보가 달라지면 환승을 알린다.
				System.out.print("\n" + st.peek().y + "호선 ");
				prev_line = st.peek().y;
			}
			System.out.print(st.peek().x + " ");
			st.pop();
		}

		System.out.println("\n최단시간 " + dist[N]);

	}

	// class pair(int, int) 2가지의 int형 정보를 저장한다, Comparble은 우선순위 큐에서 서로의 크기를 비교해 정렬을 하기 위함이다.
	static class pair implements Comparable<pair> {
		int x;
		int y;

		pair(int x, int y) {
			this.x = x; // 첫번째 인자
			this.y = y; // 두번째 인자
		}

		@Override
		public int compareTo(pair o) {
			return this.x <= o.x ? -1: 1; // 첫번째 인자 끼리 비교


		}
	}
}
 /* 이해를 도와줄만한 자료들
   https://www.youtube.com/watch?v=tZu4x5825LI
   https://m.blog.naver.com/kks227/220796029558 */
