package unittest.m6;

import gamemap.ChallengeRoom;
import gamemap.GameMap;
import gamemap.Room;
import gameobjects.Player;
import gameobjects.ProjectileLauncher.ProjectileLauncherD;
import gameobjects.npc.NPC;
import gameobjects.physics.Vector2D;
import gameobjects.potions.Potion;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.GameStage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;


public class ChallengeRoomTest  extends ApplicationTest {
    private GameStage game;
    private Player player;
    private GameMap map;
    private NPC npc;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Room r = new ChallengeRoom(20, 20);
        player = new Player("unittest", r, 0, 0, 0);
        game = new GameStage(player, r);
        game.start(new Stage());
    }

    @Test
    public void declineChallengeTest(){
        ChallengeRoom room = (ChallengeRoom)game.getRoom();
        npc = room.getNpc();
        player.getPhysics().setPosition(npc.getPhysics().getPosition().subtract(new Vector2D(40, 0)));
        press(KeyCode.D).release(KeyCode.D);
        press(KeyCode.E).release(KeyCode.E);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clickOn("#background");
        clickOn("No");
        assertTrue(room.getMonsters().size() == 0);
    }
    @Test
    public void acceptChallengeTest(){
        ChallengeRoom room = (ChallengeRoom)game.getRoom();
        npc = room.getNpc();
        player.getPhysics().setPosition(npc.getPhysics().getPosition().subtract(new Vector2D(40, 0)));
        press(KeyCode.D).release(KeyCode.D);
        press(KeyCode.E).release(KeyCode.E);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clickOn("#background");
        clickOn("Yes");
        assertTrue(room.getMonsters().size() > 0);
    }
    @Test
    public void defectChallengeTest(){
        ChallengeRoom room = (ChallengeRoom)game.getRoom();
        npc = room.getNpc();
        player.getPhysics().setPosition(npc.getPhysics().getPosition().subtract(new Vector2D(40, 0)));
        player.getInventory().clear();
        press(KeyCode.D).release(KeyCode.D);
        press(KeyCode.E).release(KeyCode.E);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clickOn("#background");
        clickOn("Yes");
        int num_item = player.getWeaponList().size();
        press(KeyCode.TAB).release(KeyCode.TAB);
        press(KeyCode.E).release(KeyCode.E);
        clickOn("#background");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(room.isPrizeCollected());
    }

}
