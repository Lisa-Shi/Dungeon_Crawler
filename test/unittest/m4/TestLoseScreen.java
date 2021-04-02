package unittest.m4;

import gamemap.Room;
import gameobjects.Player;
import gameobjects.monsters.Monster;
import gameobjects.physics.Vector2D;
import gameobjects.tiles.ExitTile;
import javafx.stage.Stage;
import main.GameStage;
import main.Main;
import main.Weapon;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;

/**
 * Unit test for checking that player behaves properly once health = 0
 */
public class TestLoseScreen extends ApplicationTest {
    private GameStage game;
    private Player player1;
    private Player player2;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Room r = new Room(20, 20);
        player1 = new Player("unittest", new Weapon("unittest", "unittest", 1, 5), r, 0, 0, 0);
        game = new GameStage(player1, r);
        this.stage = new Stage();
        game.start(stage);
    }

    /**
     * Tests that game stops once player is defeated by monsters.
     */
    @Test(timeout = 5000)
    public void testLoseInGame() {
        Room start = game.getRoom();
        ExitTile exit = start.getExits().get(0);
        player1.getPhysics().setPosition(exit.getPhysics().getPosition());

        player1.setDamageAmt(100);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNotEquals(start, game.getRoom());
        Vector2D playerPos = player1.getPhysics().getPosition();
        Vector2D displace = player1.getDirection().multiply(Main.TILE_WIDTH);

        for (Monster m : game.getRoom().getMonsters()) {
            m.getPhysics().setPosition(playerPos.add(displace));
        }

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (player1.getHealth() <= 0) {
            verifyThat("restart", NodeMatchers.isNotNull());
            verifyThat("exit", NodeMatchers.isNotNull());
        }

    }

    /**
     * Tests that losing screen functions properly
     */
    @Test
    public void testLoseScreenRestart() {
        player1.setHealth(0);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clickOn("restart");
    }

}
