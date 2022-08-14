package com.example.pathfinderfx;

import javafx.application.Application;
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
import java.util.List;

public class Main extends Application {
    private static boolean greenExist = false;
    private static boolean redExist = false;
    private static int START_ROW = -1;
    private static int START_COL = -1;
    public static int clicks = 0;
    public List<List<Rectangle>> rectList = new ArrayList<>();
    public GridPane grid = new GridPane();

    @Override
    public void start(Stage stage) throws IOException {
        HBox hbox = new HBox(20);
        Button btnStart = new Button();
        btnStart.setPrefSize(120,25);
        btnStart.setText("Start Traversal");
        btnStart.setOnAction(actionEvent -> startTraversal());
        hbox.getChildren().addAll(grid, btnStart);
        createRectangles();
        AddEventListenerFunction();
        createMatrix();

        Scene scene1 = new Scene(hbox,900,760);
        stage.setTitle("PathFinder");
        stage.setScene(scene1);
        stage.show();
    }
    private void startTraversal(){
        System.out.println("/////////////////////////////////////////////////////////////////////");
        List<List<Character>> grid = createMatrix();
        boolean [][]visited = new boolean[18][18];
        System.out.println(BFS_Grid.shortestPath(grid,visited,START_ROW, START_COL));

    }

    private List<List<Character>> createMatrix(){
        List<List<Character>> matrixGrid = new ArrayList<>();
        List<Character> ls = null;
        for(int i = 0;i < 18;i++){
            ls = new ArrayList<>();
            for(int j = 0;j < 18;j++){
                // W -> Water | L -> Land | S -> Start | E -> End
                if(rectList.get(i).get(j).getFill() == Color.LIGHTBLUE){
                    ls.add('W');
                }
                if(rectList.get(i).get(j).getFill() == Color.PINK){
                    ls.add('L');
                }
                if(rectList.get(i).get(j).getFill() == Color.RED){
                    ls.add('S');
                    START_ROW = i;
                    START_COL = j;
                }
                if(rectList.get(i).get(j).getFill() == Color.GREEN){
                    ls.add('E');
                }
            }
            matrixGrid.add(ls);
        }
        // Print matrix in console.
        for(List<Character> lis: matrixGrid){
            System.out.println(lis);
        }
        return matrixGrid;
    }
    private void AddEventListenerFunction(){
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

    private void createRectangles(){
        for(int i = 0;i < 18;i++){
            List<Rectangle> pom = new ArrayList<>();
            for(int j = 0;j < 18;j++){
                Rectangle toAddRect = new Rectangle();
                toAddRect.setHeight(40); toAddRect.setWidth(40);
                toAddRect.setFill(Color.LIGHTBLUE);
                GridPane.setRowIndex(toAddRect, i);
                GridPane.setColumnIndex(toAddRect, j);
                pom.add(toAddRect);
                grid.setVgap(2); grid.setHgap(2);
                grid.getChildren().addAll(toAddRect);
            }
            rectList.add(pom);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}