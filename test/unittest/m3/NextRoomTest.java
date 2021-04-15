package unittest.m3;

import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import sample.*;

import java.util.List;

import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;

public class NextRoomTest extends ApplicationTest {
    private GameStage game;
    private Player player;
    private GameMap map;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Room r = new Room(20, 20);
        player = new Player("unittest", new Weapon("unittest", "unittest", 1, 5), r, 0, 0, 0);
        game = new GameStage(player, r);
        map = game.getMap();
        game.start(new Stage());

    }
    @Test
    public void fourExitInStartTest() {
        Room start = game.getRoom();
        List<ExitTile> exits = start.getExits();
        Assert.assertEquals(4, exits.size());
    }
    @Test
    public void nextRoomTest() {
        Room start = game.getRoom();
        ExitTile exit = start.getExits().get(0);
        player.getPhysics().setPosition(exit.getPhysics().getPosition());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNotEquals(start, game.getRoom());
    }
    @Test
    public void finishGameTest() {
        Room start = game.getRoom();
        GameMap.enterRoom(new Room(1, 1, 999));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clickOn("finish");
        verifyThat("finish", NodeMatchers.isNotNull());
    }
}