package unittest.m5;

import gamemap.Room;
import gameobjects.Player;
import gameobjects.ProjectileLauncher.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.Main;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

/**
 * Tests the Projectile Launcher.
 *
 * Checks that there are different weapons.
 *
 * Checks that player can't shoot self.
 */
public class ProjectileLauncherTest extends ApplicationTest {
    private Main game;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        game = new Main();
        game.start(stage);
    }

    /**
     * Checks different weapons are able to be obtained and equipped.
     */
    @Test
    public void testDifferentProjectiles() {
        clickOn("Start Game");
        clickOn("#nameInput").write("name");
        clickOn("1: easy");
        clickOn("1: weapon");
        clickOn("Go to room");

        press(KeyCode.S);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        release(KeyCode.S);

        Player p = game.getPlayer();
        ProjectileLauncher weapon = p.getHoldingWeapon();
        ProjectileLauncher expectedW1 = ProjectileLauncherA.getInstance(p);
        assertEquals(expectedW1, weapon);

        moveAndShoot();

        ProjectileLauncher expectedW2 = ProjectileLauncherB.getInstance(p);
        p.obtainNewWeapon(expectedW2);
        p.equipWeapon(expectedW2);
        weapon = p.getHoldingWeapon();
        assertEquals(expectedW2, weapon);

        moveAndShoot();

        ProjectileLauncher expectedW3 = ProjectileLauncherC.getInstance(p);
        p.obtainNewWeapon(expectedW3);
        p.equipWeapon(expectedW3);
        weapon = p.getHoldingWeapon();
        assertEquals(expectedW3, weapon);

        moveAndShoot();

    }

    /**
     * Checks that player can not shoot self.
     */
    @Test
    public void testShootSelf() {
        clickOn("Start Game");
        clickOn("#nameInput").write("name");
        clickOn("1: easy");
        clickOn("1: weapon");
        clickOn("Go to room");
        Player p = game.getPlayer();

        moveAndShoot();

        press(KeyCode.S);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        release(KeyCode.S);

        assertEquals(100, p.getHealth());
    }

    private void moveAndShoot() {
        press(KeyCode.S);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        release(KeyCode.S);

        for (int i = 0; i < 5; i++) {
            press(KeyCode.ENTER);
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            release(KeyCode.ENTER);
        }
    }

}
