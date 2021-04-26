package unittest.m5;

import gamemap.Room;
import gameobjects.PotionChest;
import gameobjects.Player;
import gameobjects.ProjectileLauncher.ProjectileLauncher;
import gameobjects.ProjectileLauncher.ProjectileLauncherA;
import gameobjects.monsters.BuzzMonster;
import gameobjects.monsters.Monster;
import gameobjects.potions.AttackPotion;
import gameobjects.potions.BulletPotion;
import gameobjects.potions.HealthPotion;
import gameobjects.potions.Potion;
import gameobjects.tiles.ExitTile;
import javafx.application.Platform;
import javafx.stage.Stage;
import main.GameStage;
import main.Main;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import screens.EndSceneController;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StatsTest extends ApplicationTest {

    private GameStage game;
    private Player player;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Room r = new Room(20, 20, 999);
        player = new Player("unittest", r, 3 * Main.TILE_WIDTH, 3 * Main.TILE_HEIGHT, 0);

        game = new GameStage(player, r);
        game.start(new Stage());

        ProjectileLauncher weapon = ProjectileLauncherA.getInstance(player);
        player.getWeaponList().add(weapon);
    }

    @Test
    public void testBulletsShot() {
        player.getWeaponList().get(0).shoot(game.getRoom(), game.getPane(), game.getCamera());
        player.getWeaponList().get(0).shoot(game.getRoom(), game.getPane(), game.getCamera());
        player.getWeaponList().get(0).shoot(game.getRoom(), game.getPane(), game.getCamera());
        player.getWeaponList().get(0).shoot(game.getRoom(), game.getPane(), game.getCamera());
        player.getWeaponList().get(0).shoot(game.getRoom(), game.getPane(), game.getCamera());

        assertEquals(player.getBulletShot(), 5);

    }

    @Test
    public void testConsumePotion() {
        Potion potion1 = new AttackPotion();
        Potion potion2 = new HealthPotion();
        Potion potion3 = new BulletPotion();
        potion1.consume(player);
        potion2.consume(player);
        potion3.consume(player);

        assertEquals(player.getPotionsConsumed(), 3);
    }


}
