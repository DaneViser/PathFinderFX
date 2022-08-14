package com.example.pathfinderfx;

import java.util.*;

public class BFS_Grid {
    static final int ROW = 18; // Max rows
    static final int COL = 18; // Max cols
    static int[] dRow = {-1, 0, 1, 0};
    static int[] dCol = {0, 1, 0, -1};

    public static boolean isValid(List<List<Character>> grid, boolean[][] visi, int row, int col) {
        if (row < 0 || col < 0 || row >= ROW || col >= COL)
            return false;
        if (visi[row][col])
            return false;
        return grid.get(row).get(col) != 'L';
    }

    public static int shortestPath(List<List<Character>> grid, boolean[][] visited, Integer row, Integer col) {

        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(row, col,0));
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
                    visited[adjx][adjy] = true;

                    queue.add(help);
                }


            }

        }


        return -1; // returns -1 if there is no path
    }


//    private static void createGrid() {
//        List<Character> ls = null;
//        for (int i = 0; i < 7; i++) {
//            ls = new ArrayList<>();
//            for (int j = 0; j < 7; j++) {
//                // W -> Water | L -> Land | S -> Start | E -> End
//                if (i == 0 && j == 0) {
//                    ls.add('S');
//                    continue;
//                }
//                if (i == 6 && j == 6) {
//                    ls.add('E');
//                    continue;
//                }
//                if ((i == 5 && j == 6)) {
//                    ls.add('L');
//                    continue;
//                }
//                ls.add('W');
//            }
//            matrixGrid.add(ls);
//        }
//
//        // Print matrix in console.
//        for (List<Character> lis : matrixGrid) {
//            System.out.println(lis);
//        }
//
//    }


}
