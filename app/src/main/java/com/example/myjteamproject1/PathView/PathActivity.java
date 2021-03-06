package com.example.myjteamproject1.PathView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myjteamproject1.MainView.LoadingDialog;
import com.example.myjteamproject1.MainView.MainActivity;
import com.example.myjteamproject1.Menu.BookMarkView;
import com.example.myjteamproject1.Menu.MenusActivity;
import com.example.myjteamproject1.PathFinder.PathFinder;
import com.example.myjtest.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class PathActivity extends AppCompatActivity {
    Button time, cost, distance, button3, done, book;

    LoadingDialog loadingDialog;

    int start, end, transfer;

    boolean is_book = false;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        loadingDialog = new LoadingDialog(this);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.show();

        Intent intent = getIntent();
        start = Integer.parseInt(intent.getStringExtra("startStation"));
        end = Integer.parseInt(intent.getStringExtra("endStation"));
        String t = intent.getStringExtra("transferStation");
        if (t != null)
            transfer = Integer.parseInt(intent.getStringExtra("transferStation"));
        else
            transfer = 0;
        PathView.is_trans = transfer;

        //PathFinder p = new PathFinder(start, end, 0, PathActivity.this);
        //PathFinder p2 = new PathFinder(start, end, 1, PathActivity.this);

        PathFinder p1 = MainActivity.p1;
        p1.Algorithm(start, end, transfer);

        PathFinder p2 = MainActivity.p2;
        p2.Algorithm(start, end, transfer);

        PathFinder p3 = MainActivity.p3;
        p3.Algorithm(start, end, transfer);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismiss();
                p1.setDataSorted();
                p2.setDataSorted();
                p3.setDataSorted();

                ArrayList<Station> st1 = p1.getStationArr();
                ArrayList<Station> st2 = p2.getStationArr();
                ArrayList<Station> st3 = p3.getStationArr();

//                Log.d("SIZE", st1.size() + "");
//                for (Station s : st1) {
//                    Log.d("info", s.getName() + " " + s.getArrive() + " " + s.getLine());
//                }

                PathView.timeStations = st1;
                PathView.costStations = st2;
                PathView.distanceStations = st3;

                setContentView(R.layout.path_activity);

                time = (Button) findViewById(R.id.time_btn);
                cost = (Button) findViewById(R.id.cost_btn);
                distance = (Button) findViewById(R.id.distance_btn);
                button3 = (Button) findViewById(R.id.button3);
                done = (Button) findViewById(R.id.done);
                book = (Button) findViewById(R.id.bookmark);

                time.setBackgroundColor(Color.GRAY);
                cost.setBackgroundColor(Color.DKGRAY);
                distance.setBackgroundColor(Color.DKGRAY);
                button3.setBackgroundColor(Color.BLACK);
                done.setBackgroundColor(Color.DKGRAY);

                button3.setText("??????");

                Scanner scan = null;
                File readFrom = new File(getApplicationContext().getFilesDir(), "Bookmark.txt");
                try {
                    scan = new Scanner(readFrom);
                    while (scan.hasNext()) {
                        int u = Integer.parseInt(scan.next());
                        int v = Integer.parseInt(scan.next());
                        int c = Integer.parseInt(scan.next());
                        if(u == start && v == end && c == transfer)
                        {
                            book.setBackgroundColor(Color.BLUE);
                            is_book = true;
                            break;
                        }
                    }
                    if(is_book == false)
                        book.setBackgroundColor(Color.DKGRAY);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 3000);
    }

    public void pressButton1(View view) {
        time.setBackgroundColor(Color.GRAY);
        cost.setBackgroundColor(Color.DKGRAY);
        distance.setBackgroundColor(Color.DKGRAY);

        PathView.type = 0;
        PathView.ny = 200;
        setButton3(view);
    }

    public void pressButton2(View view) {
        time.setBackgroundColor(Color.DKGRAY);
        cost.setBackgroundColor(Color.DKGRAY);
        distance.setBackgroundColor(Color.GRAY);

        PathView.type = 1;
        PathView.ny = 200;
        setButton3(view);
    }

    public void pressButton3(View view) {
        time.setBackgroundColor(Color.DKGRAY);
        cost.setBackgroundColor(Color.GRAY);
        distance.setBackgroundColor(Color.DKGRAY);

        PathView.type = 2;
        PathView.ny = 200;
        setButton3(view);
    }

    public void pressButton4(View view) {
        if (PathView.screen == 0) {
            PathView.screen = 1;
            button3.setBackgroundColor(Color.DKGRAY);
            button3.setText("??????");
            PathView.move = true;
        } else {
            PathView.screen = 0;
            button3.setBackgroundColor(Color.BLACK);
            button3.setText("??????");
            PathView.move = false;
        }
        PathView.ny = 200;
    }

    public void pressButton5(View view) {
        is_book = false;
        Scanner scan = null;
        File readFrom = new File(getApplicationContext().getFilesDir(), "Bookmark.txt");
        String str = "";
        try {
            scan = new Scanner(readFrom);
            while (scan.hasNext()) {
                int u = Integer.parseInt(scan.next());
                int v = Integer.parseInt(scan.next());
                int c = Integer.parseInt(scan.next());
                if(u == start && v == end && c == transfer)
                {
                    is_book = true;
                    continue;
                }
                str += u + " " + v + " " + c + "\n";
            }
            if(is_book == true){
                File path = getApplicationContext().getFilesDir();
                try {
                    new FileOutputStream(new File(path, "Bookmark.txt")).close();
                    FileOutputStream w = new FileOutputStream(new File(path, "Bookmark.txt"),true);
                    w.write(str.getBytes());
                    w.close();
                    book.setBackgroundColor(Color.DKGRAY);
                    Toast.makeText(PathActivity.this, "???????????? ??????!", Toast.LENGTH_SHORT).show();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream w = new FileOutputStream(new File(path, "Bookmark.txt"),true);
            String s = start + " " + end + " " + transfer + "\n";
            w.write(s.getBytes());
            w.close();
            Toast.makeText(PathActivity.this, "???????????? ?????? ??????!", Toast.LENGTH_SHORT).show();
            book.setBackgroundColor(Color.BLUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setButton3(View view) {
        PathView.screen = 0;
        button3.setBackgroundColor(Color.BLACK);
        button3.setText("??????");
        PathView.move = false;
    }

    public void done(View view) {
        finish();
    }

    public void onMenu(View view) {
        Intent intent = new Intent(getApplicationContext(), MenusActivity.class);
        startActivity(intent);
    }
}
