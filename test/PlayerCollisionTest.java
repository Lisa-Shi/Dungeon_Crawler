import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import sample.GameStage;
import sample.Main;
import sample.Player;
import sample.Vector2D;
import sample.WallTile;
import sample.Weapon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerCollisionTest extends ApplicationTest {
    private GameStage game;
    private Player player;

    @Override
    public void start(Stage primaryStage) throws Exception {
        player = new Player("test", new Weapon("test", "test",
                1, 5), 2 * Main.TILE_WIDTH, 2 * Main.TILE_HEIGHT, 0);
        game = new GameStage(player);
        game.start(new Stage());
        game.getRoom().clear();
    }
    @Test
    public void testZeroVelocityAfterDirectCollision() {
        game.getRoom().add(new WallTile(game.getRoom(), 1, 2));

        for (int i = 0; i < 10000; i++) {
            player.getPhysics().pushLeft(Main.DEFAULT_CONTROL_PLAYER_FORCE);
            game.update();
        }

        assertEquals(0, player.getPhysics().getVelocity().len(), 0.01);
    }
    @Test
    public void testSlidesAfterPartialCollision() {

        for (int c = 0; c < game.getRoom().getHeight(); c++) {
            game.getRoom().add(new WallTile(game.getRoom(), 0, c));
        }

        player.getPhysics().pushLeft(10);
        player.getPhysics().pushDown(10);

        for (int i = 0; i < 500; i++) {
            game.update();
        }

        assertEquals(0, player.getPhysics().getVelocity().getX(), 0.5D);

        Vector2D playerPos = player.getPhysics().getPosition();
        double playerXPos = playerPos.getX();
        double playerYPos = playerPos.getY();

        assertEquals(playerXPos, Main.TILE_WIDTH, 15D);
        assertTrue(playerYPos > 2 * Main.TILE_HEIGHT);
    }
}
