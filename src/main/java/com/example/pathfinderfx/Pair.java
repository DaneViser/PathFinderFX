package com.example.pathfinderfx;

import java.util.List;
import java.util.Objects;

public class Pair {
    // path for bfs
    int first, second, path;
    String put;

    public Pair(int first, int second, int path, String put) {
        this.first = first;
        this.second = second;
        this.path = path;
        this.put = put;

    }
}