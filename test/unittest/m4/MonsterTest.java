package unittest.m4;

import gamemap.GameMap;
import gamemap.Room;
import gameobjects.Player;
import gameobjects.monsters.Monster;
import gameobjects.physics.Vector2D;
import gameobjects.tiles.ExitTile;
import javafx.stage.Stage;
import main.GameStage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

public class MonsterTest  extends ApplicationTest {
    private GameStage game;
    private Player player;
    private Stage stage;
    private GameMap map;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Room r = new Room(20, 20);
        //player = new Player("unittest", new Weapon("unittest", "unittest", 1, 5), r, 0, 0, 0);
        player = new Player("unittest", r, 0, 0, 0);
        player.setHealth(1000000);
        game = new GameStage(player, r);
        map = game.getMap();
        game.start(new Stage());
    }
    @Test
    public void monsterMove() {
        Room room = game.getRoom();
        ExitTile exit = room.getExits().get(0);
        player.getPhysics().setPosition(exit.getPhysics().getPosition());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Monster monster = game.getRoom().getMonsters().get(0);
        Vector2D old = monster.getPhysics().getPosition();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Vector2D newP = monster.getPhysics().getPosition();
        assertFalse(newP.subtract(old).equals(new Vector2D(0, 0)));
    }
    @Test
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
