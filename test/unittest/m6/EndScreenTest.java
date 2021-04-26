package unittest.m6;

import gamemap.ChallengeRoom;
import gamemap.GameMap;
import gamemap.Room;
import gameobjects.Player;
import gameobjects.ProjectileLauncher.Projectile;
import gameobjects.ProjectileLauncher.ProjectileLauncherA;
import gameobjects.ProjectileLauncher.ProjectileLauncherC;
import gameobjects.ProjectileLauncher.ProjectileLauncherD;
import gameobjects.monsters.BuzzMonster;
import gameobjects.monsters.Monster;
import gameobjects.physics.Vector2D;
import gameobjects.physics.collisions.Collideable;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import main.*;

import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;

public class EndScreenTest extends ApplicationTest {
    private GameStage game;
    private Player player;
    private GameMap map;
    private Stage stage;
    private Room room;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Room r = new Room(20, 20);
        this.room = r;
        player = new Player("unittest", r, 0, 0, 0);
        //player1 = new Player("unittest", new Weapon("unittest", "unittest", 1, 5), r, 0, 0, 0);
        game = new GameStage(player, r);
        this.stage = new Stage();
        game.start(stage);
        map = game.getMap();


    }

    /**
     * Tests the statistic based on player's fields.
     */
    @Test
    public void statTest() {
        int n = 10;
        player.obtainNewWeapon(ProjectileLauncherC.getInstance(player));
        //player.obtainNewWeapon(ProjectileLauncherD.getInstance(player));
        player.equipWeapon(ProjectileLauncherC.getInstance(player));

        GameMap.enterRoom(new Room(20, 20, 2));
        press(KeyCode.S);
        try {
            Thread.sleep(20);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        release(KeyCode.S);

        double x = player.getPhysics().getPosition().getX();
        double y = player.getPhysics().getPosition().getY();

        room.generateMonsters();
        int countMStart = room.getMonsters().size();

        for (Monster m : room.getMonsters()) {
            m.getPhysics().setPosition(new Vector2D(x, y + 2));
        }

        for (int i = 0; i < n; i++) {
            press(KeyCode.ENTER);
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            release(KeyCode.ENTER);
        }


        int countMEnd = room.getMonsters().size();

        assertEquals(n, player.getBulletShot());
        assertEquals(0, player.getChallengeRooms());
        assertEquals(countMStart - countMEnd, player.getMonsterKilled());

        GameMap.enterRoom(new ChallengeRoom(50, 50));
        assertEquals(0, player.getChallengeRooms());

    }

    /**
     * Checks that the ending screen is loaded
     */
    @Test
    public void finishGameTest() {
        //game.start(new Stage());
        Room room = new Room(1, 1, 999);
        GameMap.enterRoom(room);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        player.addMonsterKilled();
        //room.getMonsters().clear();
        assertEquals(0, room.getMonsters().size());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertNotNull(stage.getScene());

    }

}