package com.example.myjteamproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;

public class SearchActivity extends AppCompatActivity {
    static AutoCompleteTextView txtViewStationName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        txtViewStationName = findViewById(R.id.searchText);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,STATION);
        txtViewStationName.setAdapter(adapter);
        txtViewStationName.setText("");

        ImageButton button = findViewById(R.id.backButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                finish();
            }
        });




        }
    static final String[] STATION = new String[] {"101","102","103","104","105","106","107","108",
            "109", "110","111","112","113","114","115","116","117","118","119","120","121","122",
            "123","201","202","203","204","205","206","207","208","209","210","211","212","213",
            "214","215","216","217","301","302","303","304","305","306","307","308","401","402",
            "403","404","405","406","407","408","409","410","411","412","413","414","415","416",
            "417","501","502","503","504","505","506","507","601","602","603","604","605","606",
            "607","608","609","610","611","612","613","614","615","616","617","618","619","620",
            "621","622","701","702","703","704","705","706","707","801","802","803","804","805",
            "806","901","902","903","904"};
    }

