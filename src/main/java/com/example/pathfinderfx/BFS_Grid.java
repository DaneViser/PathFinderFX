package com.example.pathfinderfx;

import java.util.*;

public class BFS_Grid {
    static final int ROW = 18; // Max rows
    static final int COL = 18; // Max cols
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, 1, 0, -1};
    public static List<Pair> travelList = new ArrayList<>();

    public static boolean isValid(List<List<Character>> grid, boolean[][] visi, int row, int col) {
        if (row < 0 || col < 0 || row >= ROW || col >= COL)
            return false;
        if (visi[row][col])
            return false;
        return grid.get(row).get(col) != 'L';
    }

    public static int shortestPath(List<List<Character>> grid, boolean[][] visited, Integer row, Integer col) {

        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(row, col, 0));
        visited[row][col] = true;

        while (!queue.isEmpty()) {
            Pair cell = queue.peek();
            int x = cell.first;
            int y = cell.second;

            queue.remove();

            if (grid.get(x).get(y) == 'E') return cell.path;
            for (int i = 0; i < 4; i++) {
                int adjx = x + dRow[i];
                int adjy = y + dCol[i];
                if (isValid(grid, visited, adjx, adjy)) {
                    Pair help = new Pair(adjx, adjy, cell.path + 1);
                    travelList.add(new Pair(adjx, adjy, 0));
                    visited[adjx][adjy] = true;
                    queue.add(help);
                    if (grid.get(adjx).get(adjy) == 'E') return cell.path + 1;
                }
            }
        }

        return -1; // returns -1 if there is no path
    }


}
