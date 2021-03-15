import org.testfx.framework.junit.ApplicationTest;
import sample.GameStage;
import sample.Main;
import javafx.stage.Stage;
import org.junit.Test;
import sample.Player;
import sample.Vector2D;
import sample.Weapon;

import static org.junit.Assert.assertEquals;

public class PlayerPhysicsTest extends ApplicationTest {
    private GameStage game;
    private Player player;

    @Override
    public void start(Stage primaryStage) throws Exception {
        player = new Player("test", new Weapon("test", "test", 1, 5), 0, 0, 0);
        game = new GameStage(player);
        game.start(new Stage());
    }
    @Test
    public void testPushLeft() {

        player.getPhysics().pushLeft(Main.DEFAULT_CONTROL_PLAYER_FORCE);
        game.update();

        assertEquals(-Main.DEFAULT_CONTROL_PLAYER_FORCE,
                player.getPhysics().getVelocity().getX(), 0.001);
        assertEquals(0, player.getPhysics().getVelocity().getY(), 0.001);
        assertEquals(0, player.getPhysics().getAcceleration().getX(), 0.001);
        assertEquals(0, player.getPhysics().getAcceleration().getY(), 0.001);
    }
    @Test
    public void testPushRight() {

        player.getPhysics().pushRight(Main.DEFAULT_CONTROL_PLAYER_FORCE);
        game.update();

        assertEquals(Main.DEFAULT_CONTROL_PLAYER_FORCE,
                player.getPhysics().getVelocity().getX(), 0.001);
        assertEquals(0, player.getPhysics().getVelocity().getY(), 0.001);
        assertEquals(0, player.getPhysics().getAcceleration().getX(), 0.001);
        assertEquals(0, player.getPhysics().getAcceleration().getY(), 0.001);
    }
    @Test
    public void testPushUp() {
        player.getPhysics().pushUp(Main.DEFAULT_CONTROL_PLAYER_FORCE);
        game.update();

        assertEquals(0, player.getPhysics().getVelocity().getX(), 0.001);
        assertEquals(-Main.DEFAULT_CONTROL_PLAYER_FORCE,
                player.getPhysics().getVelocity().getY(), 0.001);
        assertEquals(0, player.getPhysics().getAcceleration().getX(), 0.001);
        assertEquals(0, player.getPhysics().getAcceleration().getY(), 0.001);
    }
    @Test
    public void testPushDown() {
        player.getPhysics().pushDown(Main.DEFAULT_CONTROL_PLAYER_FORCE);
        game.update();

        assertEquals(0, player.getPhysics().getVelocity().getX(), 0.001);
        assertEquals(Main.DEFAULT_CONTROL_PLAYER_FORCE,
                player.getPhysics().getVelocity().getY(), 0.001);
        assertEquals(0, player.getPhysics().getAcceleration().getX(), 0.001);
        assertEquals(0, player.getPhysics().getAcceleration().getY(), 0.001);
    }
    @Test
    public void testMaxVelocity() {
        player.getPhysics().setAcceleration(new Vector2D(1000, 1000));
        for (int i = 0; i < 100; i++) {
            game.update();
        }

        assertEquals(player.getPhysics().getVelocity().len(), Main.MAX_PLAYER_SPEED, 0.01);
    }
}
