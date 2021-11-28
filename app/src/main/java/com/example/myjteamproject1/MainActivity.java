package com.example.myjteamproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_SEARCH = 101;
    public static final int REQUEST_CODE_MENU = 102;
    ImageButton menuButton,searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        menuButton  =  findViewById(R.id.backButton);
        //searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivityForResult(intent,REQUEST_CODE_SEARCH);
            }
        });
        menuButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivityForResult(intent,REQUEST_CODE_MENU);
            }
        });

        //안녕하세요 윤현수입니다. 열심히 하겠습니다
    }
}
