package com.example.pathfinderfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {
    private static boolean greenExist = false;
    private static boolean redExist = false;
    private static int START_ROW = -1;
    private static int START_COL = -1;
    public static int clicks = 0;
    public List<List<Rectangle>> rectList = new ArrayList<>();
    public List<Pair> finalRoad = new ArrayList<>();
    public GridPane grid = new GridPane();

    @Override
    public void start(Stage stage) throws IOException {
        HBox hbox = new HBox(20);
        Button btnStart = new Button();
        btnStart.setPrefSize(120, 25);
        btnStart.setText("Start Traversal");
        btnStart.setOnAction(actionEvent -> startTraversal());
        hbox.getChildren().addAll(grid, btnStart);
        createRectangles();
        rectList.get(0).get(0).setStyle("-fx-border-width: 5px");
        AddEventListenerFunction();
        createMatrix();

        Scene scene1 = new Scene(hbox, 900, 760);
        stage.setTitle("PathFinder");
        stage.setScene(scene1);
        stage.show();
    }

    private void startTraversal() {
        System.out.println("/////////////////////////////////////////////////////////////////////");
        List<List<Character>> grid = createMatrix();
        boolean[][] visited = new boolean[18][18];
        String road = (BFS_Grid.shortestPath(grid, visited, START_ROW, START_COL));
        List<Pair> fnlRoad = getFinalRoadIndexes(road);
        System.out.println(fnlRoad);
        System.out.println(BFS_Grid.shortestPath(grid, visited, START_ROW, START_COL));

        Thread t = new Thread() {
            int i = 0;
            int len = BFS_Grid.travelList.size();
            Runnable updater = new Runnable() {
                @Override
                public void run() {
                    colorCellsYellow(i);
                    i++;
                }
            };

            @Override
            public void run() {
                while (i != len) {
                    if(i == len - 1) break;
                    Platform.runLater(updater);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                Thread o = new Thread(){
                    int j = 0;
                    final int len2 = finalRoad.size();
                    Runnable updater2 = new Runnable() {
                        @Override
                        public void run() {
                            colorCellsBlue(j);
                            j++;
                        }
                    };
                    public void run() {
                        while (j != len2) {
                            Platform.runLater(updater2);

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                };

                o.start();
            };

        };
        t.start(); // start thread t.

    }
    private List<Pair> getFinalRoadIndexes(String put){
        // Path coloring
        List<String> myList = new ArrayList<>(Arrays.asList(put.split("\\|")));
        List<String> indexes = new ArrayList<>();
        List<Integer> ind = new ArrayList<>();
        indexes.addAll(myList);
        for(String str: indexes){
            System.out.println(str);
            String [] help = str.split(" ");
            int x = Integer.parseInt(help[0]);
            int y = Integer.parseInt(help[1]);
            ind.add(x); ind.add(y);

        }
        for(int i = 0;i < ind.size();i+=2){
            finalRoad.add(new Pair(ind.get(i),ind.get(i+1),0,""));
        }

        return finalRoad;

    }
    private void colorCellsBlue(int index){
        // Final road coloring
        int i = finalRoad.get(index).first;
        int j = finalRoad.get(index).second;
        rectList.get(i).get(j).setFill(Color.BLUE);
    }
    private void colorCellsYellow(int index) {
        // Traversal coloring
        int i = BFS_Grid.travelList.get(index).first;
        int j = BFS_Grid.travelList.get(index).second;
        rectList.get(i).get(j).setFill(Color.YELLOW);
    }

    private List<List<Character>> createMatrix() {
        List<List<Character>> matrixGrid = new ArrayList<>();
        List<Character> ls = null;
        for (int i = 0; i < 18; i++) {
            ls = new ArrayList<>();
            for (int j = 0; j < 18; j++) {
                // W -> Water | L -> Land | S -> Start | E -> End
                if (rectList.get(i).get(j).getFill() == Color.LIGHTBLUE) {
                    ls.add('W');
                }
                if (rectList.get(i).get(j).getFill() == Color.PINK) {
                    ls.add('L');
                }
                if (rectList.get(i).get(j).getFill() == Color.RED) {
                    ls.add('S');
                    START_ROW = i;
                    START_COL = j;
                }
                if (rectList.get(i).get(j).getFill() == Color.GREEN) {
                    ls.add('E');
                }
            }
            matrixGrid.add(ls);
        }
        // Print matrix in console.
        for (List<Character> lis : matrixGrid) {
            System.out.println(lis);
        }
        return matrixGrid;
    }

    private void AddEventListenerFunction() {
        // Adds eventListener on all rectangles
        for (List<Rectangle> rectangles : rectList) {
            for (Rectangle rect : rectangles) {
                rect.setOnMousePressed(mouseEvent -> {
                    if (mouseEvent.isPrimaryButtonDown()) {
                        // For Obstacles
                        if (rect.getFill() == Color.LIGHTBLUE)
                            rect.setFill(Color.PINK);
                        else
                            rect.setFill(Color.LIGHTBLUE);
                    }
                    if (mouseEvent.isSecondaryButtonDown()) {
                        // For Start
                        if (rect.getFill() == Color.LIGHTBLUE && !redExist) {
                            rect.setFill(Color.RED);
                            redExist = true;
                        } else if (rect.getFill() == Color.RED) {
                            rect.setFill(Color.LIGHTBLUE);
                            redExist = false;
                        }
                    }
                    if (mouseEvent.isMiddleButtonDown()) {
                        // For End
                        if (rect.getFill() == Color.LIGHTBLUE && !greenExist) {
                            rect.setFill(Color.GREEN);
                            greenExist = true;
                        } else if (rect.getFill() == Color.GREEN) {
                            rect.setFill(Color.LIGHTBLUE);
                            greenExist = false;
                        }
                    }

                });
            }
        }


    }

    private void createRectangles() {
        for (int i = 0; i < 18; i++) {
            List<Rectangle> pom = new ArrayList<>();
            for (int j = 0; j < 18; j++) {
                Rectangle toAddRect = new Rectangle();
                toAddRect.setHeight(40);
                toAddRect.setWidth(40);
                toAddRect.setFill(Color.LIGHTBLUE);
                toAddRect.setStyle("-fx-border-radius: 80px;");
                GridPane.setRowIndex(toAddRect, i);
                GridPane.setColumnIndex(toAddRect, j);
                pom.add(toAddRect);
                grid.setVgap(3);
                grid.setHgap(3);
                grid.getChildren().addAll(toAddRect);
            }
            rectList.add(pom);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}