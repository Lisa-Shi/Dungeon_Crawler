package unittest.m6;

import gamemap.GameMap;
import gamemap.Room;
import gameobjects.Player;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import main.*;

import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;

public class EndScreenTest extends ApplicationTest {
    private GameStage game;
    private Player player;
    private GameMap map;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Room r = new Room(20, 20);
        player = new Player("unittest", r, 0, 0, 0);
        game = new GameStage(player, r);
        map = game.getMap();
        game.start(new Stage());
    }

    @Test
    public void finishGameTest() {
        Room room = new Room(1, 1, 999);
        GameMap.enterRoom(room);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        room.getMonsters().clear();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(0, room.getMonsters().size());
        verifyThat("restart", NodeMatchers.isNotNull());
        verifyThat("exit", NodeMatchers.isNotNull());
        verifyThat("finish", NodeMatchers.isNull());
    }
}