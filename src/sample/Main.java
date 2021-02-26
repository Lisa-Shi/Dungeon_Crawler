package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
<<<<<<< Updated upstream
import javafx.stage.Stage;

public class Main extends Application {
=======
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    // Variables
    public static int GAME_WIDTH = 800;
    public static int GAME_HEIGHT = 500;

    public static double DEFAULT_FORCE = 1;
    public static double DEFAULT_FRICTIONAL_FORCE = 0.20D;
    public static double DEFAULT_CAMERA_SLOWDOWN_FACTOR = 7; // similar to a frictional force
    public static double DEFAULT_CONTROL_PLAYER_FORCE = 0.45D;
    public static double MAX_PLAYER_SPEED = 7D;

    public static final Image PLAYER_IMAGE = new Image(Player.class.getResource("testimg.png").toExternalForm());

    private Player player;
>>>>>>> Stashed changes

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));


        primaryStage.setTitle("Dungeon Crawler");
        primaryStage.setScene(new Scene(root, 600, 800));


<<<<<<< Updated upstream
        //primaryStage.show();

        Room r = new Room();
        r.start(primaryStage);
=======
        //button action for moving
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
>>>>>>> Stashed changes
    }


    public static void main(String[] args) {
        launch(args);
    }
}
