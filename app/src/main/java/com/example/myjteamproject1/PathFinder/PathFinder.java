package com.example.myjteamproject1.PathFinder;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.myjteamproject1.MainView.LoadingDialog;
import com.example.myjteamproject1.PathView.PathActivity;
import com.example.myjteamproject1.PathView.StationRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

public class PathFinder extends AppCompatActivity {

    final int MAX_V = 2000;
    final int INF = 100000000;
    static ArrayList<Tuple>[] adj = new ArrayList[2000];
    static boolean b = false;

    public PathFinder(int start, int end, int type, Context context) {
        /*
        type = 0 시간순
        type = 1 거리순
        type = 2 비용순
        ->enum으로 변경
         */
        if (b == false) {
            setUpStation(0, context);
            b = !b;
        }
        else{
            int K = start;
            int N = end; //시작역 , 도착역 입력
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
                    Log.d("1",st.peek().y+"호선 ");
                    prev_line = st.peek().y;
                }
                //System.out.print(st.peek().x + " ");
                Log.d("2",st.peek().x + " ");
                st.pop();
            }
            // System.out.println("\n최단시간 " + dist[N]);
            Log.d("3","최단시간 " + dist[N]);
        }
    }

    public void setUpStation(int type, Context context) {
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


