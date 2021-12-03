package com.example.myjteamproject1.Menu;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myjtest.R;

import java.util.ArrayList;

public class TransferActivity extends AppCompatActivity {
    Button done, papago, google;
    Button english, spanish, thai, russian;
    ArrayList<Button> language;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.transfer_activity);

        done = (Button) findViewById(R.id.done);
        papago = (Button) findViewById(R.id.papago);
        google = (Button) findViewById(R.id.google);
        english = (Button) findViewById(R.id.english);
        spanish = (Button) findViewById(R.id.spanish);
        thai = (Button) findViewById(R.id.thai);
        russian = (Button) findViewById(R.id.russian);

        done.setBackgroundColor(Color.DKGRAY);
        papago.setBackgroundColor(Color.DKGRAY);
        google.setBackgroundColor(Color.DKGRAY);
        english.setBackgroundColor(Color.GRAY);
        spanish.setBackgroundColor(Color.DKGRAY);
        thai.setBackgroundColor(Color.DKGRAY);
        russian.setBackgroundColor(Color.DKGRAY);

        language = new ArrayList<>();
        language.add(english);
        language.add(thai);
        language.add(spanish);
        language.add(russian);

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonColor(0);
            }
        });

        thai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonColor(1);
            }
        });

        spanish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonColor(2);
            }
        });

        russian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonColor(3);
            }
        });

        papago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent papago_U = new Intent(Intent.ACTION_VIEW, Uri.parse("https://papago.naver.com/"));
                startActivity(papago_U);
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent google_U = new Intent(Intent.ACTION_VIEW, Uri.parse("https://translate.google.com/"));
                startActivity(google_U);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setButtonColor(int type){
        int i = 0;
        for(Button btn : language){
            if(i == type)
                btn.setBackgroundColor(Color.GRAY);
            else
                btn.setBackgroundColor(Color.DKGRAY);
            i++;
        }
    }
}
