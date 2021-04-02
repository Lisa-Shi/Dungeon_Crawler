package unittest.m4;

import gamemap.Room;
import gameobjects.Player;
import gameobjects.Projectile;
import gameobjects.monsters.Monster;
import gameobjects.physics.collisions.Collideable;
import gameobjects.tiles.ExitTile;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.GameStage;
import main.Main;
import main.Weapon;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;

public class testPlayerShoot extends ApplicationTest {
    private GameStage game;
    private Room room;
    private Player player1;
    private Player player2;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Room r = new Room(20, 20);
        this.room = r;
        player1 = new Player("unittest", new Weapon("unittest", "unittest", 1, 5), r, 0, 0, 0);
        game = new GameStage(player1, r);
        this.stage = new Stage();
        game.start(stage);

    }

    @Test
    public void testShootProjectile() {
        int n = 5;
        press(KeyCode.S);
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        release(KeyCode.S);

        for (int i = 0; i < n; i++) {
            press(KeyCode.ENTER);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            release(KeyCode.ENTER);
        }
        int countP = 0;
        for (Collideable c : room.getCollideables()) {
            if (c instanceof Projectile) {
                countP++;
            }
        }
        assertTrue(countP > 0);
    }
    /**
     * Tests that game stops once player is defeated by monsters.
     */
    @Test(timeout = 3000)
    public void testKillMonster() {
        //Going to a room with monsters
        Room start = game.getRoom();
        ExitTile exit = start.getExits().get(0);
        player1.getPhysics().setPosition(exit.getPhysics().getPosition());

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int cumulativeHealth = 0;
        for (Monster m : game.getRoom().getMonsters()) {
            cumulativeHealth += m.getHealth();
        }

        //shooting at the monsters until one dies
        KeyCode[] code = new KeyCode[4];
        code[0] = KeyCode.W;
        code[1] = KeyCode.A;
        code[2] = KeyCode.S;
        code[3] = KeyCode.D;
        int i = 0;
        boolean oneDead = false;
        while (!oneDead && i < 6) {
            int health = 0;
            for (Monster m : game.getRoom().getMonsters()) {
                health += m.getHealth();
                oneDead = m.isDead();

            }
            assertTrue(health <= cumulativeHealth);
            if (health == 0) {
                break;
            }
            press(KeyCode.ENTER);
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            release(KeyCode.ENTER);
            press(code[i % 4]);
            release(code[i % 4]);
            i++;
            player1.setHealth(Main.PLAYER_STARTING_HEALTH);
        }

    }
}
