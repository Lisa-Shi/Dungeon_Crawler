package unittest.m5;

import gamemap.Room;
import gameobjects.PotionChest;
import gameobjects.Player;
import gameobjects.potions.AttackPotion;
import gameobjects.potions.Potion;
import javafx.application.Platform;
import javafx.stage.Stage;
import main.GameStage;
import main.Main;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ChestTest extends ApplicationTest {

    private GameStage game;
    private Player player;
    private PotionChest chest;
    private Potion potion;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Room r = new Room(20, 20);
        player = new Player("unittest", r, 3 * Main.TILE_WIDTH, 3 * Main.TILE_HEIGHT, 0);

        game = new GameStage(player, r);
        game.start(new Stage());
        game.getRoom().clear();

        potion = new AttackPotion();

        HashMap<Potion, Integer> inv = new HashMap<>();
        inv.put(potion, 2);

        chest = new PotionChest(100, inv, r, 0, 0);
    }

    @Test
    public void testTakeMoney() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                player.setMoney(100);
                chest.open(player, game.getPane());
                assertEquals(200, player.getMoney());
            }
        });
    }

    @Test
    public void testLoseItem() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                player.getInventory().clear();
                chest.open(player, game.getPane());
                chest.loseItem(potion);
                assertEquals((Object) 1, chest.getInventory().get(potion));
                chest.loseItem(potion);
                assertNull(chest.getInventory().get(potion));
            }
        });
    }


}
