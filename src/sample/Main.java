package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    // Variables
    public static double DEFAULT_FORCE = 1;
    public static double DEFAULT_FRICTIONAL_FORCE = 0.15D;
    public static double DEFAULT_CONTROL_PLAYER_FORCE = 0.45D;
    public static double MAX_PLAYER_SPEED = 10;

    public static final Image PLAYER_IMAGE = new Image(Player.class.getResource("testimg.png").toExternalForm());

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
