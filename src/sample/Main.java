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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    // Variables
    private Stage mainWindow;

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
        mainWindow = primaryStage;
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        mainWindow.setTitle("Dungeon Crawler");
        //primaryStage.setScene(new Scene(root, GAME_WIDTH, GAME_HEIGHT,
                //false, SceneAntialiasing.DISABLED));
        goWelcome();
    }

    /**
     * Sets the stage to welcome scene
     */
    private void goWelcome() {
        //Creates the welcome screen
        WelcomeScreen welcome = new WelcomeScreen();

        BorderPane welcomeLayout = welcome.welcomeLayout();
        mainWindow.setScene(new Scene(welcomeLayout, GAME_WIDTH, GAME_HEIGHT));
        mainWindow.show();

        //Sets button to move to config scene from welcome
        welcome.startButton.setOnAction(e -> {
            goConfigScreen();
        });
    }

    /**
     * Sets the stage to configuration scene
     */
    private void goConfigScreen() {
        //Creates the configuration screen and layout
        ConfigurationScreen configScene = new ConfigurationScreen(GAME_WIDTH, GAME_HEIGHT);

        //display scene
        Scene scene = configScene.getScene();
        mainWindow.setScene(scene);
        mainWindow.show();

        //Button for moving
        Button goRoom = configScene.getGoRoom();
        Button goBack = configScene.getGoBack();

        //Button action for moving to Room
        goRoom.setOnAction(event -> {
            //initialize player
            player = configScene.createChar();
            if (player != null && !player.isLegal()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if (player.getName().equals("")) {
                    alert.setContentText("please enter a name");
                } else if(player.getDifficulty() == -1){
                    alert.setContentText("please select difficulty");
                }
                alert.showAndWait();
            } else {
                goToRoom();
            }
        });
    }

    /**
     * Sets the stage to room
     */
    private void goToRoom() {
        mainWindow.setScene(new Scene(new Pane(), GAME_WIDTH, GAME_HEIGHT, false, SceneAntialiasing.DISABLED));
        GameStage r = new GameStage();
        try {
            r.start(mainWindow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
