package unittest.m6;

import gamemap.Room;
import gameobjects.Player;
import gameobjects.monsters.Monster;
import gameobjects.potions.EndGameAttackPotion;
import javafx.stage.Stage;
import main.GameStage;
import main.Main;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BossTest extends ApplicationTest {

    private Player player;
    private Room room;
    private GameStage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        room = new Room(20, 20, 999);
        player = new Player("unittest", room, 2 * Main.TILE_WIDTH, 2 * Main.TILE_HEIGHT, 0);
        stage = new GameStage(player, room);
        stage.start(new Stage());
    }

    @Test
    public void testBossDrop() {
        Monster boss = room.getMonsters().get(0);
        assertTrue(boss.getCarryReward().containsKey(new EndGameAttackPotion()));
    }

    @Test
    public void testBossStats() {
        Monster boss = room.getMonsters().get(0);
        assertEquals(3500, boss.getHealth());
        assertEquals(350, boss.getDamagePerHit());
    }
}
