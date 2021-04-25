package screens;

import gamemap.ChallengeRoom;
import gamemap.Room;
import gameobjects.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

public class InfoBarController {
    @FXML
    private Label moneyLabel;
    @FXML
    private ProgressBar health;
    @FXML
    private ProgressBar progress;

    private static int moneyVal = 0;

    public InfoBarController() {
    }

    public void update(Player player, Room room) {
        double healthVal = (double) player.getHealth() / player.getMaxHealth();
        health.setProgress(healthVal);

        int moneyVal = player.getMoney();
        moneyLabel.setText(moneyVal + "");

        if (!(room instanceof ChallengeRoom)) {
            double progressVal = room.getRoomId() / 7.0;
            progress.setProgress(progressVal);
        }
    }

//    public static void updateHealth(double healthVal) {
//        health.setProgress(healthVal);
//    }
//
    public void setMoney(double moneyVal) {
        moneyLabel.setText(moneyVal + "");
    }
//
//    public static void updateProgress(double progressVal) {
//        progress.setProgress(progressVal);
//    }

    @FXML
    public void initialize() {
        moneyLabel.setText(moneyVal + "100");
        health.setProgress(1);
        progress.setProgress(0);
    }
}
