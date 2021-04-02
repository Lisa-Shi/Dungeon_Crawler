package unittest.m4;

import gamemap.Room;
import gameobjects.Player;
import gameobjects.tiles.ExitTile;
import javafx.stage.Stage;
import main.GameStage;
import main.Weapon;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertTrue;

public class MonsterTest  extends ApplicationTest {
    private GameStage game;
    private Player player;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Room r = new Room(20, 20);
        player = new Player("unittest", new Weapon("unittest", "unittest", 1, 5), r, 0, 0, 0);
        game = new GameStage(player, r);
        this.stage = new Stage();
        game.start(stage);
    }
    @Test(timeout = 5000)
    public void noMonsterAtStart() {
        Room room = game.getRoom();
        assertTrue(room.getMonsters().size() == 0);
    }
    @Test(timeout = 5000)
    public void atLeastOneMonster() {
        Room room = game.getRoom();
        ExitTile exit = room.getExits().get(0);
        player.getPhysics().setPosition(exit.getPhysics().getPosition());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        room = game.getRoom();
        assertTrue((room.getMonsters().size() > 0));
    }
}
