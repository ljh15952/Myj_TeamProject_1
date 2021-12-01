package com.example.myjteamproject1.MainView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.myjteamproject1.PathView.PathActivity;
import com.example.myjtest.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class pathViewActivity extends Activity {
    SubsamplingScaleImageView imageView;
    GestureDetector gestureDetector = null;

    private Stations clickedStaion;
    private Button btn_1;
    private Button btn_2;
    private Button btn_3;

    private TextView tv_1;
    private TextView tv_2;
    private ArrayList<Stations> list;

    LoadingDialog loadingDialog;

    private String startStation;
    private String endStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_view);
        imageView = findViewById(R.id.imageView);
        imageView.setImage(ImageSource.asset("map.png"));

        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        tv_1 = findViewById(R.id.tv_1);
        tv_2 = findViewById(R.id.tv_2);
        btn_3 = findViewById(R.id.btn_3);

        list = new ArrayList<>();
        setAllStationInfo();

        loadingDialog = new LoadingDialog(this);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loadingDialog.show();

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStation = clickedStaion.name + "";
                tv_1.setText("출발역: " + clickedStaion.name);
                btn_1.setVisibility(View.INVISIBLE);
                btn_2.setVisibility(View.INVISIBLE);
            }
        });

        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //꼭!!!!!!!
                //출발역 도착역 설정 안했으면 안넘어가게!!
                Intent intent = new Intent(getApplicationContext(), PathActivity.class);
                intent.putExtra("startStation", startStation);
                intent.putExtra("endStation", endStation);
                startActivity(intent);
            }
        });

        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endStation = clickedStaion.name + "";
                tv_2.setText("도착역: " + clickedStaion.name);
                btn_1.setVisibility(View.INVISIBLE);
                btn_2.setVisibility(View.INVISIBLE);
            }
        });

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() { // gesture 디텍팅으로 지하철 위치 읽기
            @Override
            public boolean onSingleTapUp(MotionEvent ev) {
                if (imageView.isReady()) {
                    PointF sCoord = imageView.viewToSourceCoord(ev.getX(), ev.getY());
                    int x_cor = (int) sCoord.x;
                    int y_cor = (int) sCoord.y;
                    for (Stations st : list) {
                        if (st.isClicked(x_cor, y_cor)) {
                            clickedStaion = st;
                            btn_1.setVisibility(View.VISIBLE);
                            btn_2.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), st.name + " 클릭!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return super.onSingleTapUp(ev);
            }
        });
    }

    public void setAllStationInfo() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        int name = jsonObject.getInt("nameData");
                        int x1 = jsonObject.getInt("X1");
                        int y1 = jsonObject.getInt("Y1");
                        int x2 = jsonObject.getInt("X2");
                        int y2 = jsonObject.getInt("Y2");
                        Stations st = new Stations(name, x1, y1, x2, y2);
                        list.add(st);
                        if (list.size() > 50)
                            loadingDialog.dismiss();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "값 가져오기 실패", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        };
        RequestQueue queue = Volley.newRequestQueue(pathViewActivity.this);
        for (int i = 101; i <= 123; i++) {
            DataRequest Request = new DataRequest(i + "", responseListener);
            queue.add(Request);
        }
        for (int i = 201; i <= 217; i++) {
            DataRequest Request = new DataRequest(i + "", responseListener);
            queue.add(Request);
        }
        for (int i = 301; i <= 308; i++) {
            DataRequest Request = new DataRequest(i + "", responseListener);
            queue.add(Request);
        }
        for (int i = 401; i <= 417; i++) {
            DataRequest Request = new DataRequest(i + "", responseListener);
            queue.add(Request);
        }
        for (int i = 501; i <= 507; i++) {
            DataRequest Request = new DataRequest(i + "", responseListener);
            queue.add(Request);
        }
        for (int i = 701; i <= 707; i++) {
            DataRequest Request = new DataRequest(i + "", responseListener);
            queue.add(Request);
        }
        for (int i = 801; i <= 806; i++) {
            DataRequest Request = new DataRequest(i + "", responseListener);
            queue.add(Request);
        }
        for (int i = 901; i <= 904; i++) {
            DataRequest Request = new DataRequest(i + "", responseListener);
            queue.add(Request);
        }
        for (int i = 601; i <= 622; i++) {
            DataRequest Request = new DataRequest(i + "", responseListener);
            queue.add(Request);
        }
    }
}