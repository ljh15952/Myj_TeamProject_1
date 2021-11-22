package TestPath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import java.util.ArrayList;

public class PathView extends View {
    int tx, ty = 200;
    int radius = 100;
    Paint p;
    Paint p1;
    static boolean move = true;
    DisplayMetrics display;
    int textSize;

    ArrayList<Station>[] list;
    ArrayList<ArrayList<String>> result;

    static int type;
    static int screen;

    public PathView(Context context, AttributeSet attr){
        super(context);
        p = new Paint();
        p1 = new Paint();
        p.setColor(Color.WHITE);
        p1.setColor(Color.BLACK);
        textSize = radius/2;
        p1.setTextSize(textSize);
        this.setBackgroundColor(Color.GRAY);
        type = 0;
        screen = 1;
    }

    public void setPathWay(ArrayList<Station>[] station_list){
        list = station_list;
    }

    public boolean onTouchEvent(MotionEvent event){

        if(move){
            tx = (int) event.getX();
            ty = (int) event.getY();
        }
        return true;
    }

    @Override
    public void onDraw(Canvas canvas){
        ArrayList<Station> now_list;
        //now_list = list[type];
    }

    public void getResult(ArrayList<Station> station, int num){
        int time, trans, cost;
        time = trans = cost = 0;
        int nowLine = 0, lastLine = -1;
        for(Station s : station) {
            //get time result
            time += s.getTime();

            //get trans result
            if(lastLine == -1)
                nowLine = lastLine = s.getLine();
            else
                nowLine = s.getLine();
            if(nowLine != lastLine) trans++;

            //get cost result
            cost += s.getCost();
        }
        result.get(num).add(Integer.toString(time));
        result.get(num).add(Integer.toString(trans));
        result.get(num).add(Integer.toString(cost));
    }

    /*
    @Override
    public void onDraw(Canvas canvas){
        int nx = radius*2, ny = ty;
        String str = "101";
        int length;
        for(int i = 0; i< 5; i++){
            length = str.length() * 33;
            canvas.drawCircle(nx, ny, radius, p);
            canvas.drawText(str,nx - length/2 + 10, ny + 20, p1);
            ny += radius*3;
        }
        invalidate();
    }
     */
}
