package com.example.pathfinderfx;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    private static boolean greenExist = false;
    private static boolean redExist = false;
    public static int clicks = 0;
    public List<Rectangle> rectList = new ArrayList<>();
    public List<List<Character>> matrixGrid = new ArrayList<>();
    public GridPane grid = new GridPane();

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene1 = new Scene(grid,760,760);
        createRectangles();
        AddEventListenerFunction();
        createMatrix();
        stage.setTitle("PathFinder");
        stage.setScene(scene1);
        stage.show();
    }

    private void createMatrix(){
        List<Character> ls = null;
        for(int i = 0;i < 18;i++){
            ls = new ArrayList<>();
            for(int j = 0;j < 18;j++){
                // W -> Water | L -> Land | S -> Start | E -> End
                if(rectList.get(i + j).getFill() == Color.LIGHTBLUE){
                    ls.add('W');
                }
                else if(rectList.get(i + j).getFill() == Color.LIGHTBLUE){
                    ls.add('L');
                }
                else if(rectList.get(i + j).getFill() == Color.RED){
                    ls.add('S');
                }
                else if(rectList.get(i + j).getFill() == Color.GREEN){
                    ls.add('E');
                }
            }
            matrixGrid.add(ls);
        }
        // Print matrix in console.
        for(List<Character> lis: matrixGrid){
            System.out.println(lis);
        }
    }
    private void AddEventListenerFunction(){
        // Adds eventListener on all rectangles
        for(Rectangle rect: rectList){
            rect.setOnMousePressed(mouseEvent -> {
                if(mouseEvent.isPrimaryButtonDown()){
                    // For Obstacles
                    if(rect.getFill() == Color.LIGHTBLUE)
                        rect.setFill(Color.PINK);
                    else
                        rect.setFill(Color.LIGHTBLUE);
                }
                if(mouseEvent.isSecondaryButtonDown()) {
                    // For Start
                    if(rect.getFill() == Color.LIGHTBLUE && !redExist){
                        rect.setFill(Color.RED);
                        redExist = true;
                    }

                    else if(rect.getFill() == Color.RED){
                        rect.setFill(Color.LIGHTBLUE);
                        redExist = false;
                    }
                }
                if(mouseEvent.isMiddleButtonDown()) {
                    // For End
                    if(rect.getFill() == Color.LIGHTBLUE && !greenExist){
                        rect.setFill(Color.GREEN);
                        greenExist = true;
                    }

                    else if(rect.getFill() == Color.GREEN){
                        rect.setFill(Color.LIGHTBLUE);
                        greenExist = false;
                    }
                }

            });
        }


        }

    private void createRectangles(){
        for(int i = 0;i < 18;i++){
            for(int j = 0;j < 18;j++){
                Rectangle toAddRect = new Rectangle();
                toAddRect.setHeight(40); toAddRect.setWidth(40);
                toAddRect.setFill(Color.LIGHTBLUE);
                GridPane.setRowIndex(toAddRect, i);
                GridPane.setColumnIndex(toAddRect, j);
                rectList.add(toAddRect);
                grid.setVgap(2); grid.setHgap(2);
                grid.getChildren().addAll(toAddRect);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}