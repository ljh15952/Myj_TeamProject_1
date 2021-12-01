package com.example.myjteamproject1.PathView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.myjteamproject1.MainView.LoadingDialog;
import com.example.myjteamproject1.PathFinder.PathFinder;
import com.example.myjteamproject1.PathFinder.Tuple;
import com.example.myjtest.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class PathActivity extends AppCompatActivity {
    Button button1, button2, button3, done, set;

    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.path_activity);

        loadingDialog = new LoadingDialog(this);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
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
//                    Log.d("end", s.getArrive());
//                    Log.d("time", s.getTime() + "");
//                    Log.d("dis", s.getDistance() + "");
//                    Log.d("cost", s.getCost() + "");
                }
            }
        }, 1000);


        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.btn_3);
        button3 = (Button) findViewById(R.id.button3);
        done = (Button) findViewById(R.id.done);
        set = (Button) findViewById(R.id.setting);

        button1.setBackgroundColor(Color.GRAY);
        button2.setBackgroundColor(Color.BLACK);
        button3.setBackgroundColor(Color.BLACK);
        done.setBackgroundColor(Color.BLACK);
        set.setBackgroundColor(Color.BLACK);

        button3.setText("확대");
    }

    public void pressButton1(View view) {
        button1.setBackgroundColor(Color.GRAY);
        button2.setBackgroundColor(Color.BLACK);

        PathView.type = 0;
        PathView.ny = 200;
        setButton3(view);
    }

    public void pressButton2(View view) {
        button1.setBackgroundColor(Color.BLACK);
        button2.setBackgroundColor(Color.GRAY);

        PathView.type = 1;
        PathView.ny = 200;
        setButton3(view);
    }

    public void pressButton3(View view) {
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

}
