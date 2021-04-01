package unittest.m4;

import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import sample.*;

import java.util.LinkedList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayerExitTest extends ApplicationTest {

    private GameStage stage;
    private Player player;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Room r = new Room(20, 20);
        player = new Player("unittest", new Weapon("unittest", "unittest",
                1, 5), r, 2 * Main.TILE_WIDTH, 2 * Main.TILE_HEIGHT, 0);
        stage = new GameStage(player, r);
        stage.start(new Stage());
        stage.getRoom().clear();
    }

    @Test
    public void testExitsClosed() {

        LinkedList<ExitTile> exits = stage.getRoom().getExits();

        for (int i = 0; i < exits.size() - 1; i++) {
            assertTrue(exits.get(i).getCollisionBox().isSolid());
        }
    }

    @Test
    public void testExitsOpen() {

        stage.getRoom().clear();
        LinkedList<ExitTile> exits = stage.getRoom().getExits();

        for (int i = 0; i < exits.size() - 1; i++) {
            assertFalse(exits.get(i).getCollisionBox().isSolid());
        }
    }
}
