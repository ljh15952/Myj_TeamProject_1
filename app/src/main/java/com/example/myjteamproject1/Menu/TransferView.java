package com.example.myjteamproject1.Menu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class TransferView extends View {
    int ty = 250;
    int x = 100;
    static int type = TransferData.ENG;
    static int choice = -1;
    int touch_x = -1, touch_y = -1;

    Paint text_p;
    Paint text_sub;
    Paint num_p;
    Paint exit;
    Paint choice_text;
    Paint choice_sub;

    static int y;
    public TransferView(Context context, AttributeSet attr) {
        super(context, attr);
        this.setBackgroundColor(Color.GRAY);

        int text_size = 60;
        text_p = new Paint();
        text_p.setColor(Color.BLACK);
        text_p.setTextSize(text_size);
        num_p = new Paint();
        num_p.setColor(Color.LTGRAY);
        num_p.setTextSize(text_size - 20);
        text_sub = new Paint();
        text_sub.setColor(Color.DKGRAY);
        text_sub.setTextSize(text_size - 10);
        exit = new Paint();
        exit.setColor(Color.WHITE);
        exit.setTextSize(text_size);

        y = ty;
    }

    public boolean onTouchEvent(MotionEvent event) {
        int temp = ty;
        touch_x = (int)event.getX();
        touch_y = (int)event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ty = (int) event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                temp = ty;
                ty = (int) event.getY();
                if(choice == -1) {
                    y += ty - temp;
                }
                return true;
        }
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        String[][] context = TransferData.trans;

        int i = 0;
        if(choice == -1) {
            for (String[] str : context) {
                int gap = 200 * i * 2;
                canvas.drawCircle(x - 62, y + gap - 13, 32, text_p);
                canvas.drawText(Integer.toString(i + 1) + ")", x - 80, y + gap, num_p);
                canvas.drawText(str[type], x, y + gap, text_p);
                canvas.drawText(str[TransferData.KOR], x, y + 100 + gap, text_sub);
                if (touch_x >= x - 93 && touch_x <= x - 30 && touch_y >= y + gap - 45 && touch_y <= y + gap + 19) {
                    Log.d("choice", Integer.toString(i));
                    choice = i;
                    break;
                }
                i++;
            }
            if (y > 137)
                y -= 30;
            if (y + 100 + 200 * i * 2 < 1700)
                y += 30;
        }else{
            String[] str = context[choice];
            canvas.drawRect(900, 60, 1040, 160, text_sub);
            canvas.drawText("EXIT", 910, 130,exit);
            canvas.drawText(str[type], 40, 580, text_p);
            canvas.drawText(str[TransferData.KOR], 40, 720, text_sub);
            if(touch_x >= 900 && touch_x <= 1040 && touch_y >= 60 && touch_y <=160)
                choice = -1;
        }
        invalidate();
    }
}
