import javafx.scene.input.KeyCode;
import org.testfx.assertions.api.Assertions;
import sample.Main;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import sample.Player;
import java.awt.*;
import java.util.ArrayList;

import static org.testfx.api.FxAssert.verifyThat;

public class ConfigTest extends ApplicationTest {
    private Main game;
    @Override
    public void start(Stage primaryStage) throws Exception{
        game = new Main();
        game.start(primaryStage);
        clickOn("Start Game");
    }
    @Test
    public void testNoName() {
        clickOn("1: easy");
        clickOn("Go to room");
        verifyThat("please enter a name", NodeMatchers.isNotNull());
    }
    @Test
    public void testNoDiff() {
        //need to setID to FX object in the screen classes
        clickOn("#nameInput").write("name");
        clickOn("Go to room");

        verifyThat("please select difficulty", NodeMatchers.isNotNull());
    }
    @Test
    public void testValid() {
        //need to setID to FX object in the screen classes
        clickOn("#nameInput").write("name");
        clickOn("1: easy");
        clickOn("Go to room");
        Player temp = new Player("name", null, 1);
        Assertions.assertThat(game.getPlayer()).isEqualToComparingOnlyGivenFields(temp, "name", "difficulty");
    }
}
