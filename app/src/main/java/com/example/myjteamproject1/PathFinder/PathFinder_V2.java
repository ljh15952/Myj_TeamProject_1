//package com.example.myjteamproject1.PathFinder;
//
//import java.util.Scanner;
//import java.util.PriorityQueue;
//import java.util.Stack;
//import java.util.ArrayList;
//
///*
//      <코드의 전체적인 구조>
// --------------------------------
//   1.최단경로 알고리즘 (출발역 -> ~ 모든역)
// --------------------------------
//   2.최단경로 알고리즘 (경유역 -> ~ 모든역) }
// --------------------------------  }
//   3.거쳐온 경로 출력 (출발역 -> 경유역)   }
// -----------------------------    }--------------- 경유역을 입력했을 때에만 작동
//   4.거쳐온 경로 출력 (경유역 -> 도착역)   }
// ----------------------------     }
//   5.최단시간 출력 (출발->경유->도착)     }
// ------------------------------
//   6.거쳐온 경로 출력 (출발역 -> 도착역)
// -----------------------------    }----------------- 경유역을 입력하지 않고 출발, 도착역만 입력했을 떄에만 작동
//   7.최단시간 출력 (출발 -> 도착)
// -----------------------------
// */
//
//public class PathFinder_V2 {
//    // 텍스트 파일에 있는 역의 정보를 복사하고 붙여넣기 한다. 이후 시작역과 도착역을 입력한다.
//
//
//    public static void main(String[] args) {
//        final int MAX_V = 2000;
//        final int INF = 100000000;
//        ArrayList<Tuple>[] adj = new ArrayList[MAX_V]; // 3가지의 int형 정보를 담는 ArrayList를 가리키는 배열 adj
//
//        boolean via_check = false;
//        int choice;
//        int VIA = 0; // 경유여부 확인, 출발 경유 도착 선택을 위한 변수, 경유역 번호
//        boolean K_check = false; // K랑 N 둘다 true이면 출발역과 도착역을 지정한 상태가 되서 최단경로를 출력한다. 경유역을 꼭 선택하지 않고 출발 도착만 지정해도 결과가 나오게 하기위해 넣음
//        boolean N_check = false;
//        int K = 0;
//        int N = 0; // 시작역, 도착역1
//        int u, v, w, l; // u역에서 v역 까지 가는데 걸리는 시간 w, l은 임의로 넣은 몇 호선 정보
//
//        for (int i = 0; i < 3; i++) {
//            if (K_check && N_check)
//                break;
//
//            System.out.println("무엇을 입력하시겠습니까? 1.출발역 2.경유역 3.도착역");
//            choice = scan.nextInt();
//            switch (choice) {
//                case 1:
//                    K = scan.nextInt();
//                    K_check = true;
//                    K--;
//                    break; // 출발역 입력 여부 확인, 출발역-1
//                case 2:
//                    VIA = scan.nextInt();
//                    via_check = true;
//                    VIA--;
//                    break; // 경유역 입력 여부 확인, 경유역-1
//                case 3:
//                    N = scan.nextInt();
//                    N_check = true;
//                    N--;
//                    break; // 도착역 입력 여부 확인, 도착역-1
//                default:
//                    System.out.println("다시 입력하세요");
//                    i--;
//                    break; // i - 1을 해서 한번더 반복할 수 있게함
//            }
//
//        }
//
//
//        // 1.
//        int[] dist = new int[MAX_V]; // 시작역에서 각 역까지의 거리에 대한 정보를 저장
//        for (int i = 0; i < MAX_V; i++)
//            dist[i] = INF; // 초기값은 모든역의 거리를 무한하게 설정
//
//        pair[] prev = new pair[MAX_V]; // 어떤 역들을 거쳐서 왔는지 저장하기 위한 배열 prev
//
//        for (int i = 0; i < MAX_V; i++) {
//            prev[i] = new pair(0, 0);
//            prev[i].x = -1;
//        }
//        boolean[] visited = new boolean[MAX_V];
//        for (int i = 0; i < MAX_V; i++)
//            visited[i] = false;
//
//
//        PriorityQueue<pair> PQ = new PriorityQueue<>(); // 우선순위 큐 PQ, PQ에는 (시작역에서 지정역 까지의 거리, 지정역 번호) 가 저장된다.
//
//        // 다익스트라 알고리즘
//
//        dist[K] = 0;
//        PQ.add(new pair(0, K));
//
//        while (!PQ.isEmpty()) {
//            int curr;
//            do {
//                curr = PQ.peek().y;
//                PQ.remove();
//            } while (!PQ.isEmpty() && visited[curr]); // 이미 방문한 정점이면 한번 더 꺼낸다
//
//            if (visited[curr]) break;
//
//            visited[curr] = true;
//
//
//            for (tuple p : adj[curr]) {
//
//                int next = p.get_first(), d = p.get_second();
//                //거리가 갱신될 경우 PQ에 새로 넣음
//                if (dist[next] > dist[curr] + d) {
//                    dist[next] = dist[curr] + d;
//                    prev[next].x = curr;
//                    prev[next].y = p.get_third();
//                    PQ.add(new pair(dist[next], next));
//                }
//            }
//        }
//
//
//        // 2.
//        if (via_check) { // 경유역을 입력했을 때에만 작동
//            int[] via_dist = new int[MAX_V]; // 시작역에서 각 역까지의 거리에 대한 정보를 저장
//            for (int i = 0; i < MAX_V; i++)
//                via_dist[i] = INF; // 초기값은 모든역의 거리를 무한하게 설정
//
//            pair[] via_prev = new pair[MAX_V]; // 어떤 역들을 거쳐서 왔는지 저장하기 위한 배열 prev
//
//            for (int i = 0; i < MAX_V; i++) {
//                via_prev[i] = new pair(0, 0);
//                via_prev[i].x = -1;
//            }
//            boolean[] via_visited = new boolean[MAX_V];
//            for (int i = 0; i < MAX_V; i++)
//                via_visited[i] = false;
//
//
//            PriorityQueue<pair> via_PQ = new PriorityQueue<>(); // 우선순위 큐 PQ, PQ에는 (시작역에서 지정역 까지의 거리, 지정역 번호) 가 저장된다.
//
//            // 다익스트라 알고리즘
//
//            via_dist[VIA] = 0;
//            via_PQ.add(new pair(0, VIA));
//
//            while (!via_PQ.isEmpty()) {
//                int curr;
//                do {
//                    curr = via_PQ.peek().y;
//                    via_PQ.remove();
//                } while (!via_PQ.isEmpty() && via_visited[curr]); // 이미 방문한 정점이면 한번 더 꺼낸다
//
//                if (via_visited[curr]) break;
//
//                via_visited[curr] = true;
//
//
//                for (tuple p : adj[curr]) {
//
//                    int next = p.get_first(), d = p.get_second();
//                    //거리가 갱신될 경우 PQ에 새로 넣음
//                    if (via_dist[next] > via_dist[curr] + d) {
//                        via_dist[next] = via_dist[curr] + d;
//                        via_prev[next].x = curr;
//                        via_prev[next].y = p.get_third();
//                        via_PQ.add(new pair(via_dist[next], next));
//                    }
//                }
//            }
//
//            // 3.
//            // 어떠한 경로를 거쳐왔는지 출력
//            int curr = VIA;
//            Stack<pair> st = new Stack<>(); // 스택을 이용함
//
//            while (prev[curr].x != -1) { // prev 이용
//                st.push(new pair(curr + 1, prev[curr].y));
//                curr = prev[curr].x;
//            }
//
//            st.push(new pair(K + 1, prev[K].y));
//
//            int prev_line = st.peek().y;
//
//            while (!st.empty()) {
//                if (prev_line != st.peek().y) { // 호선 정보가 달라지면 환승을 알린다.
//                    System.out.print("\n" + st.peek().y + "호선 ");
//                    prev_line = st.peek().y;
//                }
//                System.out.print(st.peek().x + " ");
//                st.pop();
//            }
//
//
//            // 4.
//            // 어떠한 경로를 거쳐왔는지 출력
//            int via_curr = N;
//            Stack<pair> via_st = new Stack<>(); // 스택을 이용함
//
//            while (via_prev[via_curr].x != -1) { // prev 이용
//                via_st.push(new pair(via_curr + 1, via_prev[via_curr].y));
//                via_curr = via_prev[via_curr].x;
//            }
//
//            via_st.push(new pair(VIA + 1, via_prev[VIA].y));
//
//            int via_prev_line = via_st.peek().y;
//
//            while (!via_st.empty()) {
//                if (via_prev_line != via_st.peek().y) { // 호선 정보가 달라지면 환승을 알린다.
//                    System.out.print("\n" + via_st.peek().y + "호선 ");
//                    via_prev_line = via_st.peek().y;
//                }
//                System.out.print(via_st.peek().x + " ");
//                via_st.pop();
//            }
//
//            // 5.
//            System.out.println("\n최단시간 " + (dist[VIA] + via_dist[N]));
//        }
//
//
//        if (!via_check) { //경유역을 지정하지 않았을 때에만 작동
//            // 6.
//            // 어떠한 경로를 거쳐왔는지 출력
//            int curr = N;
//            Stack<pair> st = new Stack<>(); // 스택을 이용함
//
//            while (prev[curr].x != -1) { // prev 이용
//                st.push(new pair(curr + 1, prev[curr].y));
//                curr = prev[curr].x;
//            }
//
//            st.push(new pair(K + 1, prev[K].y));
//
//            int prev_line = st.peek().y;
//
//            while (!st.empty()) {
//                if (prev_line != st.peek().y) { // 호선 정보가 달라지면 환승을 알린다.
//                    System.out.print("\n" + st.peek().y + "호선 ");
//                    prev_line = st.peek().y;
//                }
//                System.out.print(st.peek().x + " ");
//                st.pop();
//            }
//        }
//
//        //	7.
//        if (!via_check)
//            System.out.println("\n최단시간 " + dist[N]);
//
//
//    }
//
//    // class pair(int, int) 2가지의 int형 정보를 저장한다, Comparble은 우선순위 큐에서 서로의 크기를 비교해 정렬을 하기 위함이다.
//    static class pair implements Comparable<pair> {
//        int x;
//        int y;
//
//        pair(int x, int y) {
//            this.x = x; // 첫번째 인자
//            this.y = y; // 두번째 인자
//        }
//
//        @Override
//        public int compareTo(pair o) {
//            return this.x <= o.x ? -1 : 1; // 첫번째 인자 끼리 비교
//
//
//        }
//    }
//}
//}
