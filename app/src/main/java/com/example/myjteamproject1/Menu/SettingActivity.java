package com.example.myjteamproject1.Menu;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.example.myjtest.R;

public class SettingActivity extends AppCompatActivity {
    Button done;
    Switch darkswitch;
    @Override
    protected void onCreate(Bundle savedInstance) {

        //야간 모드 확인
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            //다크모드가 체크돼있을때 다크모드로 바꾸기
            setTheme(R.style.Theme_Dark);
        }else{
            setTheme(R.style.Theme_Light);
        }
        super.onCreate(savedInstance);
        setContentView(R.layout.setting_activity);

        done = (Button) findViewById(R.id.done);
        done.setBackgroundColor(Color.DKGRAY);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        darkswitch = findViewById(R.id.darkswitch);

        SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);
        darkswitch.setChecked(sharedPreferences.getBoolean("value",true));
        darkswitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(darkswitch.isChecked()){
                    //스위치가 체그일때 다크모드 실행
                    SharedPreferences.Editor editor =
                            getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else{
                    //스위치가 체크안돼있을때
                    SharedPreferences.Editor editor =
                            getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.apply();
                    darkswitch.setChecked(false);
                }
            }
        });

    }


}
