package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    // Variables
    public static double DEFAULT_FORCE = 1;
    public static double DEFAULT_FRICTIONAL_FORCE = 0.20D;
    public static double DEFAULT_CONTROL_PLAYER_FORCE = 0.45D;
    public static double MAX_PLAYER_SPEED = 7D;

    public static final Image PLAYER_IMAGE = new Image(Player.class.getResource("testimg.png").toExternalForm());

    public final int WIDTH = 600;
    public final int HEIGHT = 800;

    private Player player;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));


        primaryStage.setTitle("Dungeon Crawler");

        //Creates the configuration screen
        ConfigurationScreen configScreen = new ConfigurationScreen();

        //config layout
        BorderPane config = configScreen.configLayout();

        //button for moving to next scene
        Button goRoom = new Button("Go to room");
        config.setBottom(goRoom);

        //shows config
        primaryStage.setScene(new Scene(config, WIDTH, HEIGHT));
        primaryStage.show();

        //button action for moving
        goRoom.setOnAction(event -> {
            player = configScreen.createChar();
            primaryStage.setScene(new Scene(root, WIDTH, HEIGHT, false, SceneAntialiasing.DISABLED));

            Room r = new Room();
            try {
                r.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
