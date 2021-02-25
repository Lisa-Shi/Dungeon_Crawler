package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));


        primaryStage.setTitle("Dungeon Crawler");
        primaryStage.setScene(new Scene(root, 600, 800, false, SceneAntialiasing.DISABLED));


        //primaryStage.show();

        Room r = new Room();
        r.start(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
