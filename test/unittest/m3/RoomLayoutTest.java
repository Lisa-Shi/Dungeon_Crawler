package unittest.m3;

import gamemap.Room;
import gamemap.RoomLayout;
import gameobjects.tiles.WallTile;
import org.testfx.framework.junit.ApplicationTest;
import gameobjects.physics.collisions.Physical;
import main.GameStage;
import javafx.stage.Stage;
import org.junit.Test;
import gameobjects.Player;
import main.Weapon;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RoomLayoutTest extends ApplicationTest {
    private GameStage game;
    private Player player;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Room r = new Room(20, 20);
        player = new Player("unittest", new Weapon("unittest", "unittest", 1, 5), r, 0, 0, 0);
        game = new GameStage(player, r);
        game.start(new Stage());
    }

    /**
     * Builds the game stage.
     *
     * Checks that the design is as intended.
     *
     * Makes sure that there are no tiles outside bounds.
     */
    @Test
    public void testGameStageRoom() {
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

