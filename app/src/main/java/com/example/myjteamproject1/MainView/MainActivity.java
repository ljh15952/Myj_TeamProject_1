package com.example.myjteamproject1.MainView;

import androidx.appcompat.app.AppCompatActivity;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.tv);

        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(3000);
        tv.startAnimation(in);

        PathFinder p = new PathFinder(0,0,0,MainActivity.this);

        in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(MainActivity.this,pathViewActivity.class);
                startActivity(intent);
                finish();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}