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
import java.util.Scanner;

public class PathFinder extends AppCompatActivity {

    static ArrayList<Tuple>[] adj = new ArrayList[1000];
    static boolean b = false;
    LoadingDialog loadingDialog;

    public PathFinder(int start, int end, int type, Context context) {
        /*
        type = 0 시간순
        type = 1 거리순
        type = 2 비용순
        ->enum으로 변경
         */
        if (b == false) {
            setUpStation(0, context);
            loadingDialog = new LoadingDialog(context);
            loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            loadingDialog.show();
            b = !b;
        }

//        Log.d("ASDADS",adj[100].get(0).get_first()+"");
        //Log.d("ASDADS",adj[101].get(0).get_second()+"");
        //Log.d("ASDADS",adj[101].get(0).get_third()+"");

    }

    public void setUpStation(int type, Context context) {
        for (int i = 0; i < 1000; i++)
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
                        //하드코딩
                        if (startData == 621 && endData == 211)
                            loadingDialog.dismiss();
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


