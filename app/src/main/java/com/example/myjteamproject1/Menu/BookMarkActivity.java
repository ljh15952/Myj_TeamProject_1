package com.example.myjteamproject1.Menu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myjteamproject1.PathView.PathActivity;
import com.example.myjteamproject1.PathView.Station;
import com.example.myjtest.R;

import java.util.ArrayList;

public class BookMarkActivity extends AppCompatActivity {
    Button done, go;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.bookmark_activity);

        done = (Button) findViewById(R.id.done);
        done.setBackgroundColor(Color.DKGRAY);
        go = (Button) findViewById(R.id.goPath);
        go.setBackgroundColor(Color.DKGRAY);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("choose", Integer.toString(BookMarkView.choose));
                if(BookMarkView.choose != -1){
                    Intent intent = new Intent(getApplicationContext(), PathActivity.class);
                    intent.putExtra("startStation", BookMarkView.arr.get(BookMarkView.choose).getName());
                    intent.putExtra("endStation", BookMarkView.arr.get(BookMarkView.choose).getArrive());
                    intent.putExtra("transferStation", BookMarkView.arr.get(BookMarkView.choose).getTrans());
                    startActivity(intent);
                }
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
