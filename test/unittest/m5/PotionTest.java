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
