package unittest.m4;

import gamemap.Room;
import gameobjects.tiles.ExitTile;
import gameobjects.Player;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import main.*;
import main.Main;

import java.util.LinkedList;

import static org.junit.Assert.*;

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
    }

    @Test
    public void testExitsClosed() {

        LinkedList<ExitTile> exits = stage.getRoom().getExits();
        int cntr = 0;
        int exitNum = exits.size() - 1;

        for (int i = 0; i < exits.size() - 1; i++) {
            if (exits.get(i).getCollisionBox() != null
                    && exits.get(i).getCollisionBox().isSolid()) {
                cntr++;
            }
        }
        assertEquals(cntr, exitNum);
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
