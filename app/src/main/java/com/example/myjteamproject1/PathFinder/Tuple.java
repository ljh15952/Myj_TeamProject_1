package com.example.myjteamproject1.PathFinder;

public class Tuple {
    private int x;
    private int y;
    private int z;

    public Tuple(int first, int second, int third) {
        x = first;
        y = second;
        z = third;
    }

    public int get_first() {
        return x;
    }

    public int get_second() {
        return y;
    }

    public int get_third() {
        return z;
    }
}
