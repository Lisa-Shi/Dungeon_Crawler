package unittest.m5;

import gamemap.Room;
import gameobjects.Player;
import gameobjects.ProjectileLauncher.ProjectileLauncher;
import gameobjects.ProjectileLauncher.ProjectileLauncherA;
import javafx.stage.Stage;
import main.GameStage;
import main.Main;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import static org.junit.Assert.assertEquals;

public class PotionTest extends ApplicationTest {

    private GameStage game;
    private Player player;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Room r = new Room(20, 20);
        player = new Player("unittest", r, 3 * Main.TILE_WIDTH, 3 * Main.TILE_HEIGHT, 0);
        game = new GameStage(player, r);
        game.start(new Stage());
        game.getRoom().clear();
    }

    @Test
    public void testHealthPotion() {
        player.setHealth(50);
        //player.consumePotion();
        assertEquals(75, player.getHealth());
    }

    @Test
    public void testAttackPotion() {
        ProjectileLauncher weapon = player.getHoldingWeapon();
        int originalDamage = weapon.getDamage();
        //player.consumePotion();
        assertEquals(originalDamage * 2, weapon.getDamage());
    }
}
