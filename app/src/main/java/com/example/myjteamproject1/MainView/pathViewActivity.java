package com.example.myjteamproject1.MainView;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.myjteamproject1.Menu.MenusActivity;
import com.example.myjteamproject1.PathView.PathActivity;
import com.example.myjtest.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class pathViewActivity extends AppCompatActivity {
    SubsamplingScaleImageView imageView;
    GestureDetector gestureDetector = null;

    private Stations clickedStaion;
    private Button btn_1;
    private Button btn_2;
    private Button btn_3;

    private Button setting;
    private Button goPath_button;
    private Button reset_button;

    private TextView tv_s;
    private TextView tv_e;
    private TextView tv_f;

    private TextView tv_c;
    private ArrayList<Stations> list;

    LoadingDialog loadingDialog;
    private String startStation = null;
    private String endStation = null;
    private String transferStation = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_view);
        imageView = findViewById(R.id.imageView);
        imageView.setImage(ImageSource.asset("map.png"));

        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);

        setting = findViewById(R.id.setting);
        tv_s = (TextView) findViewById(R.id.tv_start);
        tv_e = (TextView) findViewById(R.id.tv_end);
        tv_f = (TextView) findViewById(R.id.tv_transfer);

        tv_c = findViewById(R.id.tv_choice);
        goPath_button = findViewById(R.id.goPath);
        reset_button = findViewById(R.id.btn_reset);

        btn_1.setBackgroundColor(Color.DKGRAY);
        btn_2.setBackgroundColor(Color.DKGRAY);
        setting.setBackgroundColor(Color.DKGRAY);
        goPath_button.setBackgroundColor(Color.DKGRAY);

        list = new ArrayList<>();
        setAllStationInfo();

        loadingDialog = new LoadingDialog(this);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loadingDialog.show();


        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startStation = null;
                String endStation = null;
                String transferStation = null;
                tv_s.setText("");
                tv_e.setText("");
                tv_f.setText("");
            }
        });

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
                tv_s.setText(clickedStaion.name + " 번");
                btn_1.setVisibility(View.INVISIBLE);
                btn_2.setVisibility(View.INVISIBLE);
                btn_3.setVisibility(View.INVISIBLE);
                tv_c.setVisibility(View.INVISIBLE);
            }
        });

        goPath_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startStation == null || endStation == null) {
                    Toast.makeText(getApplicationContext(), "출발역과 도착역 모두 선택해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), PathActivity.class);
                intent.putExtra("startStation", startStation);
                intent.putExtra("endStation", endStation);
                intent.putExtra("transferStation", transferStation);
                startActivity(intent);
            }
        });

        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endStation = clickedStaion.name + "";
                tv_e.setText(clickedStaion.name + " 번");
                btn_1.setVisibility(View.INVISIBLE);
                btn_2.setVisibility(View.INVISIBLE);
                btn_3.setVisibility(View.INVISIBLE);
                tv_c.setVisibility(View.INVISIBLE);
            }
        });

        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferStation = clickedStaion.name + "";
                tv_f.setText(clickedStaion.name + " 번");
                btn_1.setVisibility(View.INVISIBLE);
                btn_2.setVisibility(View.INVISIBLE);
                btn_3.setVisibility(View.INVISIBLE);
                tv_c.setVisibility(View.INVISIBLE);
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
                            btn_3.setVisibility(View.VISIBLE);
                            tv_c.setText(st.name + "번 역을 클릭하셨습니다.");
                            tv_c.setVisibility(View.VISIBLE);
                            //Toast.makeText(getApplicationContext(), st.name + " 클릭!!", Toast.LENGTH_SHORT).show();
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
                    tv_c.setText("값 가져오기를 실패하였습니다.");
                    tv_c.setVisibility(View.VISIBLE);
                    //Toast.makeText(getApplicationContext(), "값 가져오기 실패", Toast.LENGTH_SHORT).show();
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

    public void onMenu(View view) {
        Intent intent = new Intent(getApplicationContext(), MenusActivity.class);
        startActivity(intent);
    }
}