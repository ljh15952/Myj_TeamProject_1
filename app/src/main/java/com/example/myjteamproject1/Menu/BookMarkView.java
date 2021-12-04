package com.example.myjteamproject1.Menu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.myjteamproject1.PathView.Station;

import java.util.ArrayList;

public class BookMarkView extends View {
    static ArrayList<Station> list;
    int x = 0;
    int touch_x, touch_y, ty;

    Paint p_r;
    Paint p_l;
    Paint p_t;
    Paint p_r_c;

    static int y = 0;
    static int choose = -1;
    public BookMarkView(Context context, AttributeSet attr) {
        super(context, attr);
        this.setBackgroundColor(Color.GRAY);
        choose = -1;

        list = new ArrayList<>();
        Station s = new Station(101, 201, 301);
        list.add(s);
        s = new Station(201, 301, 0);
        list.add(s);
        s = new Station(504, 702, 102);
        list.add(s);
        s = new Station(101, 201, 301);
        list.add(s);
        s = new Station(201, 301, 0);
        list.add(s);
        s = new Station(504, 702, 102);
        list.add(s);
        s = new Station(101, 201, 301);
        list.add(s);
        s = new Station(201, 301, 0);
        list.add(s);
        s = new Station(504, 702, 102);
        list.add(s);

        p_r = new Paint();
        p_r.setColor(Color.DKGRAY);
        p_l = new Paint();
        p_l.setColor(Color.GRAY);
        p_t = new Paint();
        p_t.setColor(Color.WHITE);
        p_t.setTextSize(80);
        p_r_c = new Paint();
        p_r_c.setColor(Color.BLACK);
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
                if(list.size() > 6) {
                    y += ty - temp;
                }
                return true;
        }
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        int i = 0;
        int width = 1030, height = 260;
        int gap = 100, y_gap = 45, x_gap = 180;
        int count = 0;
        for(Station s : list){
            if(count == choose)
                canvas.drawCircle(x + 120, y+120+i, 100, p_r_c);
            else
                canvas.drawCircle(x + 120, y+120+i, 100, p_r);
            canvas.drawText(Integer.toString(count+1), x + 90, y+150+i, p_t);
            canvas.drawRect(x+ 200, y + i, width, y +height + i, p_l);
            canvas.drawRect(x + 200, y + 10 + i, width-15, y + height-20+ i, p_r);
            if(s.getTrans().equals("0"))
                canvas.drawText(s.getName() + " -> " + s.getArrive(), x + gap + x_gap, y+ gap+i + y_gap, p_t);
            else
                canvas.drawText(s.getName() + " -> " + s.getTrans() + " -> " + s.getArrive(), x + gap + x_gap, y+ gap+i+y_gap, p_t);
            if (touch_x >= x + 70 && touch_x <= x + 170 && touch_y >= y + 70 +i && touch_y <= y +170+i) {
                choose = count;
            }
            count++;
            i += height - 5;
        }
        if (y > 30)
            y -= 30;
        if (y + height - 20 + i < 1790)
            y += 30;
        invalidate();
    }
}
