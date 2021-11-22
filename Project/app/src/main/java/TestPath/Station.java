package TestPath;

public class Station {
    private String station_name;
    private String cost;
    private String time;
    private String distance;
    private int line;

    public Station(String name, String c, String t, String d, int l){
        station_name = name;
        cost = c;
        time = t;
        distance = d;
        line = l;
    }

    public  String getName(){
        return station_name;
    }

    public int getTime(){
        return Integer.parseInt(time);
    }

    public int getCost(){
        return Integer.parseInt(cost);
    }

    public int getLine(){
        return line;
    }
}
