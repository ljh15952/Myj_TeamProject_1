package com.example.myjteamproject1.Menu;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myjtest.R;

public class BookMarkActivity extends AppCompatActivity {
    Button done;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.bookmark_activity);

        done = (Button) findViewById(R.id.done);
        done.setBackgroundColor(Color.DKGRAY);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
