package screens;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Main;


public class EndSceneController {
    private static Button replay;
    private static Button exit;
    private static String isWinner;
    private static String message;
    private static Image imageLoad;



    public static void loadButton(Button restartButton, Button exitButton) {
        replay = restartButton;
        exit = exitButton;
    }
    @FXML
    private Label monster;
    @FXML
    private Label challenge;
    @FXML
    private Label rooms;
    @FXML
    private Label potion;
    @FXML
    private Label bullets;
    @FXML
    private Button replayButtonStyle;
    @FXML
    private Button exitButtonStyle;
    @FXML
    private Label win;
    @FXML
    private Label winMessage;
    @FXML
    private ImageView imageDisplay;

    private static int[] stats = new int[5];
    public static void loadStats(boolean win, int monsterS, int challengeS, int roomsS, int potionS, int bulletsS) {
        //could probably design this better for sure
        stats[0] = monsterS;
        stats[1] = challengeS;
        stats[2] = roomsS;
        stats[3] = potionS;
        stats[4] = bulletsS;
        if (win) {
            isWinner = "VICTORY";
            message = "You have escaped the dungeon!";
            imageLoad = createImage("stars.png");
        } else {
            isWinner = "DEFEAT";
            message = "The monsters won this time...";
            imageLoad = createImage("monstersAll.png");
        }
        message = message.toUpperCase();
    }

    private static Image createImage(String file) {
        try {
            Image image = new Image(Main.class.
                    getResource("../screens/" + file).
                    toExternalForm());
            return image;
        } catch (Exception e) {
            System.out.println("Restart Image failed to print");
            return null;
        }
    }


    @FXML
    public void initialize() {
        monster.setText(stats[0] + "");
        challenge.setText(stats[1] + "");
        rooms.setText(stats[2] + "");
        potion.setText(stats[3] + "");
        bullets.setText(stats[4] + "");
        win.setText(isWinner);
        winMessage.setText(message);
        imageDisplay.setImage(imageLoad);

    }

    @FXML
    void handleExit(ActionEvent event) {
        exit.fire();
    }

    @FXML
    void handleReplay(ActionEvent event) {
        replay.fire();
    }

}

