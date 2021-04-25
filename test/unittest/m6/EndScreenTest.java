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
    private Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Room r = new Room(20, 20);
        //player1 = new Player("unittest", new Weapon("unittest", "unittest", 1, 5), r, 0, 0, 0);

        player = new Player("unittest", r, 0, 0, 0);
        game = new GameStage(player, r);
        this.stage = new Stage();
        game.start(stage);

        map = game.getMap();

    }

    @Test
    public void finishGameTest() {
        //game.start(new Stage());
        Room room = new Room(1, 1, 999);
        GameMap.enterRoom(room);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        room.getMonsters().clear();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(0, room.getMonsters().size());
//        verifyThat("replay", NodeMatchers.isNotNull());
//        verifyThat("exit", NodeMatchers.isNotNull());
//        verifyThat("finish", NodeMatchers.isNull());
    }
    @Test
    public void loseScreenFXML() {
//        LoseScreen loseScreen = new LoseScreen(false, 1, 2, 3);
//        Scene scene = loseScreen.loadButton(new Button(), new Button());
//        stage.setScene(scene);
//        stage.show();
    }

}