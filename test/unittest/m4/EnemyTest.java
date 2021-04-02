package unittest.m4;

import gamemap.Room;
import gameobjects.Player;
import gameobjects.monsters.SlimeMonster;
import gameobjects.physics.Vector2D;
import gameobjects.tiles.ExitTile;
import javafx.stage.Stage;
import main.GameStage;
import main.Main;
import main.Weapon;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.LinkedList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EnemyTest extends ApplicationTest {

    private GameStage game;
    private Player player;
    private SlimeMonster monster;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Room r = new Room(20, 20);
        player = new Player("unittest", new Weapon("unittest", "unittest",
                1, 5), r, 3 * Main.TILE_WIDTH, 3 * Main.TILE_HEIGHT, 0);

        game = new GameStage(player, r);
        game.start(new Stage());
        game.getRoom().clear();

        monster = new SlimeMonster(game.getRoom(), 50, 50);
        game.getRoom().add(monster);
    }

    @Test
    public void testSlimeInRange() {
        int playerHealthBack = player.getHealth();
        monster.getPhysics().setPosition(player.getPhysics().getPosition());
        monster.attack(game.getRoom(), game.getPane(), player);

        assertTrue(player.getHealth() < playerHealthBack);
    }

    @Test
    public void testSlimeOutOfRange() {
        int playerHealthBack = player.getHealth();
        Vector2D addVector = (new Vector2D(1,1)).relen(100);

        monster.getPhysics().setPosition(player.getPhysics().getPosition().add(addVector));
        monster.attack(game.getRoom(), game.getPane(), player);

        assertTrue(player.getHealth() == playerHealthBack);
    }

}
