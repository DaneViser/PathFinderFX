module com.example.pathfinderfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pathfinderfx to javafx.fxml;
    exports com.example.pathfinderfx;
}