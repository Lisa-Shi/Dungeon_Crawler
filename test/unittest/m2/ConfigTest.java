package unittest.m2;

import org.testfx.assertions.api.Assertions;
import sample.Main;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import sample.Player;


import static org.testfx.api.FxAssert.verifyThat;

public class ConfigTest extends ApplicationTest {
    private Main game;

    @Override
    public void start(Stage primaryStage) throws Exception {
        game = new Main();
        game.start(primaryStage);
        //clickOn("Start Game");
    }
    @Test
    public void testNoName() {
        clickOn("Start Game");
        clickOn("1: easy");
        clickOn("Go to room");
        verifyThat("please enter a name", NodeMatchers.isNotNull());
    }
    @Test
    public void testNoDiff() {
        clickOn("Start Game");
        //need to setID to FX object in the screen classes
        clickOn("#nameInput").write("name");
        clickOn("Go to room");

        verifyThat("please select difficulty", NodeMatchers.isNotNull());
    }
    @Test
    public void testNoWeapon() {
        clickOn("Start Game");
        //Should still allow players to play even without weapon selection
        clickOn("#nameInput").write("Hello");
        clickOn("2: medium");
        clickOn("Go to room");

        Assertions.assertThat(game.getPlayer().getWeaponList().isEmpty());
    }
    @Test
    public void testValid() {
        clickOn("Start Game");
        //need to setID to FX object in the screen classes
        clickOn("#nameInput").write("name");
        clickOn("1: easy");
        clickOn("Go to room");
        Player temp = new Player("name", null, 0, 0, 1);
        Assertions.assertThat(game.getPlayer())
                .isEqualToComparingOnlyGivenFields(temp, "name", "difficulty");
    }

    /*
    @BeforeEach
    public void runAppToTests() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Main::new);
        FxToolkit.showStage();
        WaitForAsyncUtils.waitForFxEvents(100);
    }

    @AfterEach
    public void stopApp() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

     */
}
