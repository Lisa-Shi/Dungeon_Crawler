package unittest.m5;

import gamemap.Room;
import gameobjects.Player;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.GameStage;
import main.Main;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

public class InventoryTest extends ApplicationTest {
    private GameStage stage;
    private Player player;
    private Room r;

    @Override
    public void start(Stage primaryStage) throws Exception {
        r = new Room(20, 20);
        player = new Player("unittest", r, 2 * Main.TILE_WIDTH, 2 * Main.TILE_HEIGHT, 0);
        stage = new GameStage(player, r);
        stage.start(new Stage());
    }
    @Test
    public void consumePotionTest() {
        press(KeyCode.Q).release(KeyCode.Q);
        player.setHealth(player.getMaxHealth() - 10);
        int oldAmount = player.getHealth();
        clickOn("#HealthPotion");
        assertTrue(oldAmount != player.getHealth());
    }
}