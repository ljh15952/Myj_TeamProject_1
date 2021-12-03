package com.example.myjteamproject1.Menu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class TransferView extends View {

    public TransferView(Context context, AttributeSet attr) {
        super(context, attr);
        this.setBackgroundColor(Color.GRAY);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int tx = (int) event.getX();
        int ty = (int) event.getY();
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
    }
}
