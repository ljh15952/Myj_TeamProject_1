package com.example.myjteamproject1.PathView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myjteamproject1.MainView.LoadingDialog;
import com.example.myjteamproject1.Menu.MenusActivity;
import com.example.myjteamproject1.PathFinder.PathFinder;
import com.example.myjtest.R;

import java.util.ArrayList;

public class PathActivity extends AppCompatActivity {
    Button time, cost, distance, button3, done, set;
    Button menu;

    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        loadingDialog = new LoadingDialog(this);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.show();

        Intent intent = getIntent();
        int start = Integer.parseInt(intent.getStringExtra("startStation"));
        int end = Integer.parseInt(intent.getStringExtra("endStation"));
        PathFinder p = new PathFinder(start, end, 0, PathActivity.this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismiss();

                p.setDataSorted();
                ArrayList<Station> st = p.getStationArr();

                Log.d("SIZE", st.size() + "");
                for (Station s : st) {
                    Log.d("info", s.getName() + " " + s.getArrive());
                    Log.d("time", s.getTime() + "");
                    Log.d("dis", s.getDistance() + "");
                    Log.d("cost", s.getCost() + "");
                }

                PathView.test = st;

                setContentView(R.layout.path_activity);

                time = (Button) findViewById(R.id.time_btn);
                cost = (Button) findViewById(R.id.cost_btn);
                distance = (Button) findViewById(R.id.distance_btn);
                button3 = (Button) findViewById(R.id.button3);
                done = (Button) findViewById(R.id.done);
                menu = (Button) findViewById(R.id.menu);

                time.setBackgroundColor(Color.GRAY);
                cost.setBackgroundColor(Color.BLACK);
                distance.setBackgroundColor(Color.BLACK);
                button3.setBackgroundColor(Color.BLACK);
                done.setBackgroundColor(Color.BLACK);
                menu.setBackgroundColor(Color.BLACK);

                button3.setText("확대");

            }
        }, 1000);
        //st배열 배열사용해서 View로 넘겨주면 댐..
    }

    public void pressButton1(View view) {
        time.setBackgroundColor(Color.GRAY);
        cost.setBackgroundColor(Color.BLACK);
        distance.setBackgroundColor(Color.BLACK);

        PathView.type = 0;
        PathView.ny = 200;
        setButton3(view);
    }

    public void pressButton2(View view) {
        time.setBackgroundColor(Color.BLACK);
        cost.setBackgroundColor(Color.BLACK);
        distance.setBackgroundColor(Color.GRAY);

        PathView.type = 1;
        PathView.ny = 200;
        setButton3(view);
    }

    public void pressButton3(View view) {
        time.setBackgroundColor(Color.BLACK);
        cost.setBackgroundColor(Color.GRAY);
        distance.setBackgroundColor(Color.BLACK);

        PathView.type = 1;
        PathView.ny = 200;
        setButton3(view);
    }

    public void pressButton4(View view) {
        if (PathView.screen == 0) {
            PathView.screen = 1;
            button3.setBackgroundColor(Color.DKGRAY);
            button3.setText("축소");
            PathView.move = true;
        } else {
            PathView.screen = 0;
            button3.setBackgroundColor(Color.BLACK);
            button3.setText("확대");
            PathView.move = false;
        }
        PathView.ny = 200;
    }

    public void setButton3(View view) {
        PathView.screen = 0;
        button3.setBackgroundColor(Color.BLACK);
        button3.setText("확대");
        PathView.move = false;
    }

    public void done(View view) {
        finish();
    }

    public void onMenu(View view){
        Intent intent = new Intent(getApplicationContext(), MenusActivity.class);
        startActivity(intent);
    }
}
