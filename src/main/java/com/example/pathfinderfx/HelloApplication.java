package com.example.pathfinderfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    List<Rectangle> rectList = new ArrayList<>();
    GridPane grid = new GridPane();
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene1 = new Scene(grid,760,760);
        createRectangles();
        stage.setTitle("PathFinder");
        stage.setScene(scene1);
        stage.show();
    }
    private void createRectangles(){
        for(int i = 0;i < 18;i++){
            for(int j = 0;j < 18;j++){
                Rectangle toAddRect = new Rectangle();
                toAddRect.setHeight(40); toAddRect.setWidth(40);
                toAddRect.setFill(Color.RED);
                GridPane.setRowIndex(toAddRect, i);
                GridPane.setColumnIndex(toAddRect, j);
                grid.setVgap(2); grid.setHgap(2);
                grid.getChildren().addAll(toAddRect);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}