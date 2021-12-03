package com.example.myjteamproject1.MainView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myjteamproject1.PathFinder.PathFinder;
import com.example.myjtest.R;

public class MainActivity extends AppCompatActivity {
    //PathFinder 미리 로딩
    //TO DO
    //예외처리 ->
    //출발역 도착역 같은 역 하거나->이중화
    //출발역 도착역 둘 중 하나라도 선택을 안했거나 -> 넘어가지 않음 -> 이중화
    //경로 보여주는 화면에서 type에 따라 보여주는거 다르게->이중화

    //경로 즐겨찾기->이중화

    //회화서비스->조다빈

    //야간모드->양민석

    //최종보고서 -> 허건 윤현수 12월6일까지

    public static PathFinder p1;
    public static PathFinder p2;
    public static PathFinder p3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.tv);

        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(3000);
        tv.startAnimation(in);

        //타입별 데이터 미리 로딩
        p1 = new PathFinder(0, MainActivity.this);
        p2 = new PathFinder(1, MainActivity.this);
        p3 = new PathFinder(2, MainActivity.this);

        in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(MainActivity.this, pathViewActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}