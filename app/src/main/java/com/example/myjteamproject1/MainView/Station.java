package com.example.myjteamproject1.MainView;

public class Station {
    private int x1, y1, x2, y2;
    public int name;

    public Station(int name, int x1, int y1, int x2, int y2) {
        this.name = name;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public boolean isClicked(int x, int y) {
        if ((x > x1) && (x < x2) && (y > y1) && (y < y2))
            return true;
        return false;
    }

    public int getName() {
        return name;
    }
}

