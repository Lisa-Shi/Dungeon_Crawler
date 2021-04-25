package unittest.m5;

import gamemap.Room;
import gameobjects.Player;
import gameobjects.ProjectileLauncher.*;
import gameobjects.physics.Vector2D;
import gameobjects.physics.collisions.Physical;
import gameobjects.potions.AttackPotion;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.GameStage;
import main.Main;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

public class NPCTtest extends ApplicationTest {
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
    public void buyFromNPC() {
        int oldAmount = player.getInventory().get(new AttackPotion());
        player.getPhysics().setPosition(((Physical) r.getOpenables().get(0)).getPhysics()
                .getPosition().subtract(new Vector2D(64, 0)));
        press(KeyCode.D).release(KeyCode.D);
        press(KeyCode.E).release(KeyCode.E);
        release(KeyCode.D);
        clickOn("#AttackPotion");
        assertTrue(oldAmount + 1 == player.getInventory().get(new AttackPotion()));
    }
}
