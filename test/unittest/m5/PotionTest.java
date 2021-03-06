package unittest.m5;

import gameobjects.Player;
import gameobjects.ProjectileLauncher.ProjectileLauncher;
import gameobjects.potions.AttackPotion;
import gameobjects.potions.HealthPotion;
import javafx.stage.Stage;
import main.Main;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import static org.junit.Assert.assertEquals;

public class PotionTest extends ApplicationTest {
    private Main game;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        game = new Main();
        game.start(stage);
    }

    /**
     * Tests the health potion.
     *
     * Reduces the players health and then allowing him to take the potion.
     *
     * If the player's health increases by 25, the test passes.
     */
    @Test
    public void testHealthPotion() {
        clickOn("Start Game");
        clickOn("#nameInput").write("name");
        clickOn("1: easy");
        clickOn("1: weapon");
        clickOn("Go to room");

        Player player = game.getPlayer();

        HealthPotion hp = new HealthPotion();
        player.setHealth(50);
        hp.consume(player);
        assertEquals(75, player.getHealth());
    }

    /**
     * Tests the health potion by allowing him to take the potion at full health.
     * If the player's health does not change, the test passes.
     */
    @Test
    public void testHealthPotion2() {
        clickOn("Start Game");
        clickOn("#nameInput").write("name");
        clickOn("1: easy");
        clickOn("1: weapon");
        clickOn("Go to room");

        Player player = game.getPlayer();

        HealthPotion hp = new HealthPotion();
        hp.consume(player);
        assertEquals(100, player.getHealth());
    }


    /**
     * Tests the health potion by allowing him to take the potion at full health.
     * If the player's health does not change, the test passes.
     */
    @Test
    public void testHealthPotion3() {
        clickOn("Start Game");
        clickOn("#nameInput").write("name");
        clickOn("1: easy");
        clickOn("1: weapon");
        clickOn("Go to room");

        Player player = game.getPlayer();

        player.setHealth(90);
        HealthPotion hp = new HealthPotion();
        hp.consume(player);
        assertEquals(100, player.getHealth());
    }

    /**
     * Tests the attack potion by checking the damage that the currently equipped weapon would do,
     * and then giving the player the potion.
     *
     * If the weapon does double damage after using the potion, the test passes.
     */
    @Test
    public void testAttackPotion() {
        clickOn("Start Game");
        clickOn("#nameInput").write("name");
        clickOn("1: easy");
        clickOn("1: weapon");
        clickOn("Go to room");

        Player player = game.getPlayer();

        AttackPotion ap = new AttackPotion();
        ProjectileLauncher weapon = player.getHoldingWeapon();
        int originalDamage = weapon.getDamage();
        ap.consume(player);
        assertEquals(originalDamage * 2, weapon.getDamage());
    }
}
