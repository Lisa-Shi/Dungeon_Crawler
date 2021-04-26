package screens;

import gamemap.ChallengeRoom;
import gamemap.Room;
import gameobjects.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import main.Main;

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

    /**
     * Update the money, health and progress
     * @param player
     * @param room
     */
    public void update(Player player, Room room) {
        //Calculates health percentage
        double healthVal = (double) player.getHealth() / player.getMaxHealth();
        health.setProgress(healthVal);

        //Sets money
        moneyLabel.setText(player.getMoney() + "");

        //Calculates room until boss room
        //does not include challenge room when counting
        if (!(room instanceof ChallengeRoom)) {
            double progressVal = ((double) room.getRoomId()) / Main.ROOMS_UNTIL_BOSS;
            progress.setProgress(progressVal);
        }
    }

    /**
     * Sets the money value for info bar
     * @param moneyVal that player has
     */
    public void setMoney(double moneyVal) {
        moneyLabel.setText(moneyVal + "");
    }

    @FXML
    public void initialize() {
        moneyLabel.setText(moneyVal + "");

        //sets full health
        health.setProgress(1);

        //sets starting room
        progress.setProgress(0);
    }
}
