package com.example.myjteamproject1.PathView;

public class Station {
    private String station_name;
    private String arrival_station;
    private String cost;
    private String time;
    private String distance;
    private int line;

    private String trans_station;

    public Station(String name, String c, String t, String d, int l) {
        station_name = name;
        cost = c;
        time = t;
        distance = d;
        line = l;
    }

    public Station(int name, int arrival_station, int c, int t, int d, int l) {
        station_name = name + "";
        this.arrival_station = arrival_station + "";
        cost = c + "";
        time = t + "";
        distance = d + "";
        line = l;
    }

    public Station(int name, int arrival, int trans){
        station_name = name + "";
        arrival_station = arrival + "";
        trans_station = trans + "";
    }

    public String getTrans(){return trans_station;}

    public String getName() {
        return station_name;
    }

    public String getArrive() {
        return arrival_station;
    }

    public int getTime() {
        return Integer.parseInt(time);
    }

    public int getCost() {
        return Integer.parseInt(cost);
    }

    public int getLine() {
        return line;
    }

    public int getDistance() {
        return Integer.parseInt(distance);
    }
}
