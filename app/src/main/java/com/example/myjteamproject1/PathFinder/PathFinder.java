package com.example.myjteamproject1.PathFinder;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.myjteamproject1.PathView.Station;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class PathFinder extends AppCompatActivity {

    final int MAX_V = 2000;
    final int INF = 100000000;
    ArrayList<Tuple>[] adj = new ArrayList[2000];

    private Queue<Integer> pathArr;
    private Queue<Integer> pathArrClone;
    private ArrayList<Station> returnStations;
    private Context context;

    public PathFinder(int type, Context context) {
        /*
        type = 0 시간순
        type = 1 거리순
        type = 2 비용순
        ->enum으로 변경
         */
        setUpStation(type, context);
        this.context = context;
    }

    public void Algorithm(int start, int end, int transfer) {
        returnStations = new ArrayList<>();
        pathArr = new LinkedList<>();
        int K = start;
        int N = end; //시작역 , 도착역 입력
        int VIA = 0;
        boolean via_check = false;
        if (transfer != 0) {
            VIA = transfer;
            VIA--;
            via_check = true;
        }
        K--;
        N--;

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
        PQ.add(new pair(0, K));

        while (!PQ.isEmpty()) {
            int curr;
            do {
                curr = PQ.peek().y;
                PQ.remove();
            } while (!PQ.isEmpty() && visited[curr]); // 이미 방문한 정점이면 한번 더 꺼낸다

            if (visited[curr]) break;

            visited[curr] = true;


            for (Tuple p : adj[curr]) {

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
        if (via_check) {
            int[] via_dist = new int[MAX_V]; // 시작역에서 각 역까지의 거리에 대한 정보를 저장
            for (int i = 0; i < MAX_V; i++)
                via_dist[i] = INF; // 초기값은 모든역의 거리를 무한하게 설정

            pair[] via_prev = new pair[MAX_V]; // 어떤 역들을 거쳐서 왔는지 저장하기 위한 배열 prev

            for (int i = 0; i < MAX_V; i++) {
                via_prev[i] = new pair(0, 0);
                via_prev[i].x = -1;
            }
            boolean[] via_visited = new boolean[MAX_V];
            for (int i = 0; i < MAX_V; i++)
                via_visited[i] = false;

            PriorityQueue<pair> via_PQ = new PriorityQueue<>(); // 우선순위 큐 PQ, PQ에는 (시작역에서 지정역 까지의 거리, 지정역 번호) 가 저장된다.

            // 다익스트라 알고리즘

            via_dist[VIA] = 0;
            via_PQ.add(new pair(0, VIA));

            while (!via_PQ.isEmpty()) {
                int curr;
                do {
                    curr = via_PQ.peek().y;
                    via_PQ.remove();
                } while (!via_PQ.isEmpty() && via_visited[curr]); // 이미 방문한 정점이면 한번 더 꺼낸다

                if (via_visited[curr]) break;

                via_visited[curr] = true;


                for (Tuple p : adj[curr]) {

                    int next = p.get_first(), d = p.get_second();
                    //거리가 갱신될 경우 PQ에 새로 넣음
                    if (via_dist[next] > via_dist[curr] + d) {
                        via_dist[next] = via_dist[curr] + d;
                        via_prev[next].x = curr;
                        via_prev[next].y = p.get_third();
                        via_PQ.add(new pair(via_dist[next], next));
                    }
                }
            }

            // 3.
            // 어떠한 경로를 거쳐왔는지 출력
            int curr = VIA;
            Stack<pair> st = new Stack<>(); // 스택을 이용함

            while (prev[curr].x != -1) { // prev 이용
                st.push(new pair(curr + 1, prev[curr].y));
                curr = prev[curr].x;
            }

            st.push(new pair(K + 1, prev[K].y));

            int prev_line = st.peek().y;

            while (!st.empty()) {
                if (prev_line != st.peek().y) { // 호선 정보가 달라지면 환승을 알린다.
                    //  System.out.print("\n" + st.peek().y + "호선 ");
                   // Log.d("첫번째Log" , st.peek().y + "");
                    pathArr.add(st.peek().x);
                    prev_line = st.peek().y;
                }
                // System.out.print(st.peek().x + " ");
               // Log.d("첫번째 Logg" , st.peek().x + "");
                pathArr.add(st.peek().x);
                st.pop();
            }

            // 4.
            // 어떠한 경로를 거쳐왔는지 출력
            int via_curr = N;
            Stack<pair> via_st = new Stack<>(); // 스택을 이용함

            while (via_prev[via_curr].x != -1) { // prev 이용
                via_st.push(new pair(via_curr + 1, via_prev[via_curr].y));
                via_curr = via_prev[via_curr].x;
            }

            via_st.push(new pair(VIA + 1, via_prev[VIA].y));

            int via_prev_line = via_st.peek().y;

            //한칸 제거 중화가 추가
            via_st.pop();

            while (!via_st.empty()) {
                if (via_prev_line != via_st.peek().y) { // 호선 정보가 달라지면 환승을 알린다.
                    // System.out.print("\n" + via_st.peek().y + "호선 ");
                    //Log.d("두번째로그" , via_st.peek().y + "");
                    via_prev_line = via_st.peek().y;
                    pathArr.add(via_st.peek().y);
                }
                // System.out.print(via_st.peek().x + " ");
                //Log.d("두번째로그그" , via_st.peek().x + "");
                pathArr.add(via_st.peek().x);
                via_st.pop();
            }

            // 5.
            System.out.println("\n최단시간 " + (dist[VIA] + via_dist[N]));
        } else {
            // 어떠한 경로를 거쳐왔는지 출력
            int curr = N;
            Stack<pair> st = new Stack<>(); // 스택을 이용함

            while (prev[curr].x != -1) { // prev 이용
                st.push(new pair(curr + 1, prev[curr].y));
                curr = prev[curr].x;
            }

            st.push(new pair(K + 1, prev[K].y));

            int prev_line = st.peek().y;

            while (!st.empty()) {
                if (prev_line != st.peek().y) { // 호선 정보가 달라지면 환승을 알린다.
                    //System.out.print("\n" + st.peek().y + "호선 ");
                    //Log.d("1", st.peek().y + "호선 ");
                    prev_line = st.peek().y;
                    pathArr.add(st.peek().y);
                }
                //System.out.print(st.peek().x + " ");
                //Log.d("2", st.peek().x + " ");
                pathArr.add(st.peek().x);
                st.pop();
            }
            // System.out.println("\n최단시간 " + dist[N]);
            // Log.d("3", "최단시간 " + dist[N]);
        }


        pathArrClone = new LinkedList<>(pathArr);
        while (!pathArr.isEmpty()) {
            if (pathArr.size() < 2)
                break;
            int s = pathArr.poll();
            int e = pathArr.peek();
            setStationListInfo(s, e, context, false);
        }
    }

    public void setDataSorted() {
        //순서 이상한 데이터 swap..
        int i = 0;
        while (!pathArrClone.isEmpty()) {
            if (pathArrClone.size() < 2)
                break;
            int s = pathArrClone.poll();
            int e = pathArrClone.peek();
            //  Log.d("SORT", s + " " + e);
            if (!returnStations.get(i).getName().equals(Integer.toString(s)) && !returnStations.get(i).getArrive().equals(Integer.toString(e))) {
                int j = 0;
                for (Station ST : returnStations) {
                    if (ST.getName().equals(Integer.toString(s)) && ST.getArrive().equals(Integer.toString(e))) {
                        Station temp = ST;
                        ST = returnStations.get(i);
                        returnStations.set(i, temp);
                        returnStations.set(j, ST);
                        break;
                    }
                    j++;
                }
            }
            i++;
        }
    }

    public ArrayList<Station> getStationArr() {
        return returnStations;
    }

    private void setUpStation(int type, Context context) {
        for (int i = 0; i < MAX_V; i++)
            adj[i] = new ArrayList<>();
        getAllStationDataToTuple(type, context);
    }

    private void getAllStationDataToTuple(int type, Context context) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    int startData = jsonObject.getInt("startData");
                    int endData = jsonObject.getInt("endData");
                    int data; //가중치?
                    if (success) {
                        switch (type) {
                            case 0:
                                data = jsonObject.getInt("timeData");
                                break;
                            case 1:
                                data = jsonObject.getInt("distanceData");
                                break;
                            case 2:
                                data = jsonObject.getInt("costData");
                                break;
                            default:
                                data = 0;
                                break;
                        }
                        adj[startData - 1].add(new Tuple(endData - 1, data, 0)); // adj[출발역].add(new tuple(도착역-1, 시간, 호선)
                        adj[endData - 1].add(new Tuple(startData - 1, data, 0)); // 이대로만 하면 지하철이 한쪽 방향으로 밖에 못 가므로 반대 방향 노선도 만들어줌
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Scanner scan = null;
        InputStream is = null;
        RequestQueue queue = Volley.newRequestQueue(context);
        try {
            is = context.getAssets().open("TextFile.txt");
            scan = new Scanner(is);
            while (scan.hasNext()) {
                String u = scan.next();
                String v = scan.next();
                StationRequest loginRequest = new StationRequest(u, v, responseListener);
                queue.add(loginRequest);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setStationListInfo(int start, int end, Context context, Boolean isReverse) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        int startData, endData;
                        if (isReverse) {
                            startData = jsonObject.getInt("endData");
                            endData = jsonObject.getInt("startData");
                        } else {
                            startData = jsonObject.getInt("startData");
                            endData = jsonObject.getInt("endData");
                        }
                        int timeData = jsonObject.getInt("timeData");
                        int DistanceData = jsonObject.getInt("distanceData");
                        int costData = jsonObject.getInt("costData");
                        Station st = new Station(startData, endData, costData, timeData, DistanceData, 1);
                        returnStations.add(st);
                    } else {
                        //역방향..
                        setStationListInfo(end, start, context, true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        StationRequest loginRequest = new StationRequest(start + "", end + "", responseListener);
        queue.add(loginRequest);
    }

    static class pair implements Comparable<pair> {
        int x;
        int y;

        pair(int x, int y) {
            this.x = x; // 첫번째 인자
            this.y = y; // 두번째 인자
        }

        @Override
        public int compareTo(pair o) {
            return this.x <= o.x ? -1 : 1; // 첫번째 인자 끼리 비교
        }
    }
}


