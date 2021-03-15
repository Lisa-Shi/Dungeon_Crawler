package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {
    // Variables
    private Stage mainWindow;

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 800;

    public static final double DEFAULT_FORCE = 1;
    public static final double DEFAULT_FRICTIONAL_FORCE = 0.18D;
    public static final double DEFAULT_CAMERA_SLOWDOWN_FACTOR = 7; // similar to a frictional force
    public static final double DEFAULT_CONTROL_PLAYER_FORCE = 0.45D;
    public static final double MAX_PLAYER_SPEED = 13D;

    public static final double PLAYER_WIDTH = 40;
    public static final double PLAYER_HEIGHT = 40;

    public static final int TILE_WIDTH = 64;
    public static final int TILE_HEIGHT = 64;

    public static final int DEFAULT_COLLISION_PRECISION = 8;

    public static final PolygonWireframe TILE_WIREFRAME =
            new RectangleWireframe(Main.PLAYER_WIDTH, Main.PLAYER_HEIGHT);

    public static final Image PLAYER_IMAGE =
            new Image(Player.class.getResource("testimg.png").toExternalForm());
    public static final Image MONSTER_IMAGE =
            new Image(Player.class.getResource("monsterIMG.jpg").toExternalForm());

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
     *Sets the stage to welcome scene
     */
    private void goWelcome() {
        //Creates the welcome screen
        WelcomeScreen welcome = new WelcomeScreen();

        BorderPane welcomeLayout = welcome.welcomeLayout();
        Scene scene = new Scene(welcomeLayout, GAME_WIDTH, GAME_HEIGHT);
        scene.getStylesheets().add("stylesheet.css");
        mainWindow.setScene(scene);

        mainWindow.show();

        //Sets button to move to config scene from welcome
        welcome.getStartButton().setOnAction(e -> goConfigScreen());
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

        goBack.setOnAction(e -> {
            goWelcome();
        });

        //Button action for moving to Room
        goRoom.setOnAction(event -> {
            //initialize player
            player = configScene.createChar();
            if (player != null && !player.isLegal()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if (player.getName().equals("")) {
                    alert.setContentText("please enter a name");
                } else if (player.getName().trim().equals("")) {
                    alert.setContentText("invalid name");
                } else if (player.getDifficulty() == -1) {
                    alert.setContentText("please select difficulty");
                }
                alert.showAndWait();
            } else {
                goToRoom(player);
            }
        });
    }

    /**
     * Sets the stage to room
     *
     * @param player character that user will be controlling in the game
     */
    private void goToRoom(Player player) {
        mainWindow.setScene(new Scene(new Pane(),
                GAME_WIDTH, GAME_HEIGHT, false, SceneAntialiasing.DISABLED));
        GameStage r = new GameStage(player);
        try {
            r.start(mainWindow);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Button exit = r.getExitButton();
        exit.setOnAction(event -> {
            goCongrat();
        });
    }
    private void goCongrat() {
        CongratScreen welcome = new CongratScreen();

        BorderPane finishLayout = welcome.finishLayout();
        Scene scene = new Scene(finishLayout, GAME_WIDTH, GAME_HEIGHT);
        scene.getStylesheets().add("stylesheet.css");
        mainWindow.setScene(scene);

        mainWindow.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Returns player. (for testing purposes only)
     * @return player
     */
    public Player getPlayer() {
        return player;
    }
}