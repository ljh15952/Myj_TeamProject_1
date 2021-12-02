package com.example.myjteamproject1.Menu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myjtest.R;

public class MenusActivity extends AppCompatActivity {
    Button setting, transfer, bookmark, done;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.menu_activity);

        setting = (Button) findViewById(R.id.setting);
        transfer = (Button) findViewById(R.id.transfer);
        bookmark = (Button) findViewById(R.id.bookmark);
        done = (Button) findViewById(R.id.done);

        setting.setBackgroundColor(Color.DKGRAY);
        transfer.setBackgroundColor(Color.DKGRAY);
        bookmark.setBackgroundColor(Color.DKGRAY);
        done.setBackgroundColor(Color.DKGRAY);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TransferActivity.class);
                startActivity(intent);
            }
        });

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BookMarkActivity.class);
                startActivity(intent);
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
