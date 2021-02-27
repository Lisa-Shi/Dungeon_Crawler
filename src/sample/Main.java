package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    // Variables
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 800;

    public static final double DEFAULT_FORCE = 1;
    public static final double DEFAULT_FRICTIONAL_FORCE = 0.20D;
    public static final double DEFAULT_CAMERA_SLOWDOWN_FACTOR = 7; // similar to a frictional force
    public static final double DEFAULT_CONTROL_PLAYER_FORCE = 0.45D;
    public static final double MAX_PLAYER_SPEED = 7D;

    public static final int PLAYER_WIDTH = 40;
    public static final int PLAYER_HEIGHT = 40;

    public static final int TILE_WIDTH = 64;
    public static final int TILE_HEIGHT = 64;

    public static final Image PLAYER_IMAGE =
            new Image(Player.class.getResource("testimg.png").toExternalForm());

    private Player player;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));


        primaryStage.setTitle("Dungeon Crawler");
        primaryStage.setScene(new Scene(root, GAME_WIDTH, GAME_HEIGHT,
                false, SceneAntialiasing.DISABLED));

        //Creates the welcome screen
        WelcomeScreen welcome = new WelcomeScreen();

        BorderPane welcomeLayout = welcome.welcomeLayout();

        primaryStage.setScene(new Scene(welcomeLayout, GAME_WIDTH, GAME_HEIGHT));
        primaryStage.show();

        //Creates the configuration screen and layout
        ConfigurationScreen configScreen = new ConfigurationScreen();
        BorderPane config = configScreen.configLayout();

        //Button for moving to next scene
        Button goRoom = new Button("Go to room");
        config.setBottom(goRoom);

        //Creates config scene
        Scene configScene = new Scene(config, GAME_WIDTH, GAME_HEIGHT);

        //Sets button to move to config scene from welcome
        welcome.startButton.setOnAction(e -> {
            primaryStage.setScene(configScene);
        });

        //Button action for moving
        goRoom.setOnAction(event -> {
            player = configScreen.createChar();
            if(player != null && !player.isLegal()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if(player.getName().equals("")) {
                    alert.setContentText("please enter a name");
                }else if(player.getDifficulty() == -1){
                    alert.setContentText("please select difficulty");
                }
                alert.showAndWait();
            }else {
                primaryStage.setScene(new Scene(root, GAME_WIDTH, GAME_HEIGHT, false, SceneAntialiasing.DISABLED));

                GameStage r = new GameStage();

                try {
                    r.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
