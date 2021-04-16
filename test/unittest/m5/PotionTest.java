package unittest.m5;

import gamemap.Room;
import gameobjects.Player;
import gameobjects.potions.AttackPotion;
import gameobjects.potions.HealthPotion;
import gameobjects.potions.Potion;
import javafx.stage.Stage;
import main.GameStage;
import main.Main;
import main.Weapon;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import static org.junit.Assert.assertEquals;

public class PotionTest extends ApplicationTest {

    private GameStage game;
    private Player player;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Room r = new Room(20, 20);
        player = new Player("unittest", new Weapon("unittest", "unittest",
                1, 5), r, 3 * Main.TILE_WIDTH, 3 * Main.TILE_HEIGHT, 0);
        game = new GameStage(player, r);
        game.start(new Stage());
        game.getRoom().clear();
    }

    @Test
    public void testHealthPotion() {
        HealthPotion hp = new HealthPotion();
        player.setHealth(50);
        hp.consume(player);
        assertEquals(75, player.getHealth());
    }

    @Test
    public void testAttackPotion() {
        AttackPotion ap = new AttackPotion();
        Weapon weapon = player.getWeaponList().get(player.getHoldingWeapon());
        int originalDamage = weapon.getDamage();
        ap.consume(player);
        assertEquals(originalDamage * 2, weapon.getDamage());
    }
}
