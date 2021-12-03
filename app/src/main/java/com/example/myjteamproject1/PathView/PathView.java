package com.example.myjteamproject1.PathView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import androidx.constraintlayout.widget.ConstraintSet;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PathView extends View {
    int tx, ty = 200;
    int radius = 100;
    boolean time_full = false;
    Paint p_sub_info;
    Paint p_main_info;
    Paint p_info;
    Paint p_station;
    Paint p_station_trans;
    Paint p_text_s;
    Paint p_trans;

    static boolean move = false;
    DisplayMetrics display;
    int textSize;

    ArrayList<ArrayList<Station>> list;
    ArrayList<ArrayList<String>> result;

    static ArrayList<Station> timeStations;
    static ArrayList<Station> costStations;
    static ArrayList<Station> distanceStations;

    static int type;
    static int screen;
    static int ny;

    public PathView(Context context, AttributeSet attr) {
        super(context);
        textSize = radius / 2;
        p_main_info = new Paint();
        p_main_info.setColor(Color.BLACK);
        p_main_info.setTextSize(textSize);
        p_sub_info = new Paint();
        p_sub_info.setColor(Color.BLACK);
        p_sub_info.setTextSize(textSize);
        p_info = new Paint();
        p_info.setColor(Color.DKGRAY);
        p_info.setTextSize(textSize);
        p_station = new Paint();
        p_station.setColor(Color.LTGRAY);
        p_text_s = new Paint();
        p_text_s.setColor(Color.DKGRAY);
        p_text_s.setTextSize(textSize * 3 / 4);
        p_station_trans = new Paint();
        p_station_trans.setColor(Color.BLACK);
        p_trans = new Paint();
        p_trans.setColor(Color.WHITE);
        p_trans.setTextSize(textSize * 4 / 5);

        this.setBackgroundColor(Color.GRAY);
        type = 0;
        screen = 0;
        result = new ArrayList<>();
        ny = ty;

        ArrayList<ArrayList<Station>> station = new ArrayList<ArrayList<Station>>();
        ArrayList<Station> t = new ArrayList<>();
        ArrayList<Station> c = new ArrayList<>();
        ArrayList<Station> d = new ArrayList<>();

//        s.add(new Station("102", "30", "10", "50", 1));
//        s.add(new Station("101", "10", "20", "40", 2));
//        s.add(new Station("201", "20", "5", "20", 2));
//        s.add(new Station("202", "5", "20", "5", 3));
//        s.add(new Station("304", "0", "0", "0", 0));

        for (Station st : timeStations) {
            t.add(new Station(st.getName(), st.getCost() + "", st.getTime() + "", st.getDistance() + "", Integer.parseInt(st.getName()) / 100 ));
        }
        t.add(new Station(timeStations.get(timeStations.size() - 1).getArrive(), "0", "0", "0", 0));

        for (Station st : costStations) {
            c.add(new Station(st.getName(), st.getCost() + "", st.getTime() + "", st.getDistance() + "", Integer.parseInt(st.getName()) / 100 ));
        }
        c.add(new Station(costStations.get(costStations.size() - 1).getArrive(), "0", "0", "0", 0));

        for (Station st : distanceStations) {
            d.add(new Station(st.getName(), st.getCost() + "", st.getTime() + "", st.getDistance() + "", Integer.parseInt(st.getName()) / 100 ));
        }
        d.add(new Station(distanceStations.get(distanceStations.size() - 1).getArrive(), "0", "0", "0", 0));

       // t.add(new Station("101", "30", "10", "50", 1));
        //t.add(new Station("102", "0", "0", "0", 0));
        station.add(t);
        station.add(c);
        station.add(d);
        this.setPathWay(station);

    }

    public void setPathWay(ArrayList<ArrayList<Station>> station_list) {
        list = station_list;
        int num = 0;
        for (ArrayList<Station> station : list) {
            ArrayList<String> temp = new ArrayList<>();
            result.add(temp);
            this.getResult(station, num);
            num++;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {

        int temp = ty;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                tx = (int) event.getX();
                ty = (int) event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                temp = ty;
                ty = (int) event.getY();
                if (move == true)
                    ny += ty - temp;
                return true;
        }
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        ArrayList<Station> now_list;
        now_list = list.get(type);
        int nx = radius * 2;
        int num = 0, sub = 1;
        String unit = "", info = "";
        String major_info = "";
        String sub_info = "";

        //draw text about information
        for (String str : result.get(type)) {
            switch (num) {
                case 0:
                    info = "소요시간";
                    unit = "초/";
                    break;
                case 1:
                    info = "환승역";
                    if (str.equals("0")) {
                        unit = "없음/";
                        str = "";
                    } else
                        unit = "개/";
                    break;
                case 2:
                    info = "소요 비용";
                    unit = "원/";
                    break;
            }
            if(num == 0){
                if(time_full == true){
                    int time = Integer.parseInt(str);
                    int min = time/100;
                    int sec = time%100;
                    if(sec == 0) {
                        unit = "/";
                        str = Integer.toString(min) + "분";
                    }else
                        str = Integer.toString(min) + "분" + Integer.toString(sec);
                }
            }
            if (num == type) {
                canvas.drawText(info, nx - 50 - 40, ny - 100, p_info);
                canvas.drawText(str + unit, nx - 50 - 40, ny, p_main_info);
            } else {
                int gap = 0;
                if (num == 0)
                    gap = 160;
                else
                    gap = 200;
                canvas.drawText(info, nx + gap * sub - 40, ny - 100, p_info);
                int gap_s = 40;
                if(type == 0)
                    gap_s = 40;
                else if(sub == 1)
                    gap_s = 70;
                else
                    gap_s = 30;
                canvas.drawText(str + unit, nx + gap * sub - gap_s, ny, p_sub_info);
                sub++;
            }
            num++;
        }

        int length = now_list.size() - 1;
        //draw stations
        if (screen == 0) {
            canvas.drawCircle(nx, ny + 200, radius, p_station);
            canvas.drawRect(nx - 20, ny + 200, nx + 20, ny + 200 + radius * 3, p_station);
            canvas.drawCircle(nx, ny + 200 + radius * 3, radius, p_station);
            canvas.drawText(now_list.get(0).getName(), nx + radius * 2, ny + 200 + 20, p_sub_info);
            canvas.drawText(now_list.get(length).getName(), nx + radius * 2, ny + 200 + radius * 3 + 20, p_sub_info);
            canvas.drawText(length - 1 + "개 역", nx + radius * 2, ny + 200 + radius * 3 / 2 + 20, p_text_s);
        } else {
            int count = 0;
            if (length < 4)
                move = false;
            for (Station station : now_list) {
                if (count != length) {
                    if (count != 0 && station.getLine() != now_list.get(count - 1).getLine()) {
                        canvas.drawRect(nx - 20, ny + 200 + radius * 3 * count, nx + 20, ny + 200 + radius * 3 * (count + 1), p_station);
                        canvas.drawCircle(nx, ny + 200 + radius * 3 * count, radius, p_station_trans);
                        canvas.drawText("환승역", nx - 50, ny + 200 + radius * 3 * count + 10, p_trans);
                    } else {
                        canvas.drawCircle(nx, ny + 200 + radius * 3 * count, radius, p_station);
                        canvas.drawRect(nx - 20, ny + 200 + radius * 3 * count, nx + 20, ny + 200 + radius * 3 * (count + 1), p_station);
                    }
                    canvas.drawText(station.getTime() + "초 /", nx + radius * 2, ny + 200 + radius * 3 * count + radius * 3 / 2 + 20, p_text_s);
                    canvas.drawText(station.getCost() + "원 ", nx + radius * 2 + radius + 20, ny + 200 + radius * 3 * count + radius * 3 / 2 + 20, p_text_s);
                } else
                    canvas.drawCircle(nx, ny + 200 + radius * 3 * count, radius, p_station);

                canvas.drawText(station.getName(), nx + radius * 2, ny + 200 + radius * 3 * count + 20, p_sub_info);
                if (count == 0) {
                    canvas.drawText(": 출발역", nx + radius * 3, ny + 200 + radius * 3 * count + 20, p_text_s);
                    if (ny > 200 && move == true)
                        ny -= 30;
                } else if (count == length) {
                    canvas.drawText(": 도착역", nx + radius * 3, ny + 200 + radius * 3 * count + 20, p_text_s);
                    if (ny + 200 + radius * 3 * count + 20 < 1100 && move == true)
                        ny += 50;
                }
                count++;
            }
        }
        invalidate();
    }

    public void getResult(ArrayList<Station> station, int num) {
        int time, trans, cost;
        time = trans = cost = 0;
        int nowLine = 0, lastLine = -1;
        for (Station s : station) {
            //get time result
            time += s.getTime();

            //get trans result
            if (lastLine == -1)
                nowLine = lastLine = s.getLine();
            else {
                lastLine = nowLine;
                nowLine = s.getLine();
            }

            if (nowLine != lastLine && nowLine != 0) trans++;

            //get cost result
            cost += s.getCost();
        }
        if(time >= 60){
            int min = time/60;
            int sec = time%60;
            time = min*100 + sec;
            time_full = true;
        }
        else
            time_full = false;
        result.get(num).add(Integer.toString(time));
        result.get(num).add(Integer.toString(trans));
        result.get(num).add(Integer.toString(cost));
    }
}
