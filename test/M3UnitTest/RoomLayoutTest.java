package M3UnitTest;

import org.testfx.framework.junit.ApplicationTest;
import sample.GameStage;
import sample.*;
import javafx.stage.Stage;
import org.junit.Test;
import sample.Player;
import sample.Weapon;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RoomLayoutTest extends ApplicationTest {
    private GameStage game;
    private Player player;

    /**
     * Builds the game stage.
     *
     * Checks that the design is as intended.
     *
     * Makes sure that there are no tiles outside bounds.
     */
    @Test
    public void testGameStageRoom() {
        player = new Player("test", new Weapon("test", "test", 1, 5), 0, 0, 0);
        game = new GameStage(player);
        game.start(new Stage());

        Room r = game.getRoom();
        int id = r.getRoomId();

        LinkedList<Physical> p = r.getPhysicals();

        String layout = "design" + id % 4;
        assertEquals(layout, r.getLayout());

        for (Physical phys : p) {
            if (phys instanceof WallTile) {
                WallTile w = (WallTile) phys;
                assertTrue(w.getInitialX() > -2);
                assertTrue(w.getInitialY() > -2);
            }
        }

    }

    /**
     * Tests that the layout design changes.
     *
     * Makes sure that the layout is specific to room.
     */
    @Test
    public void testDesignID() {
        for (int i = 1; i < 30; i++) {
            Room r = new Room(20, 20, i);
            int id = r.getRoomId();

            String layout = RoomLayout.design(r);
            assertEquals("design" + (id % 4), layout);

            LinkedList<Physical> p = r.getPhysicals();
            for (Physical phys : p) {
                if (phys instanceof WallTile) {
                    WallTile w = (WallTile) phys;
                    assertTrue(w.getInitialX() > -2);
                    assertTrue(w.getInitialY() > -2);
                }
            }
        }
    }

    /**
     * Tests that the wall tiles fit within border.
     *
     * Sets the room dimension to have rectangular coordinates
     */
    @Test
    public void testDimensionWall() {
        for (int i = 0; i <= 80; i += 10) {
            Room r = new Room(20, 20 + i, 2);
            RoomLayout.design(r);
            LinkedList<Physical> p = r.getPhysicals();
            for (Physical phys : p) {
                Object obj = (Object) phys;
                if (obj instanceof WallTile) {
                    WallTile w = (WallTile) phys;
                    assertTrue(w.getInitialX() > -2);
                    assertTrue(w.getInitialY() > -2);
                }
            }
        }
    }
}

