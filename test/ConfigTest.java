import sample.ConfigurationScreen;
import sample.Main;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import java.awt.*;

import static org.testfx.api.FxAssert.verifyThat;

public class ConfigTest extends ApplicationTest {
    private Main game;
    @Override
    public void start(Stage primaryStage) throws Exception{
        game = new Main();
        game.start();
    }
    @Test
    public void testNoName() {
        clickOn("1: easy");
        clickOn("Go to Room");
        verifyThat("please enter a name", NodeMatchers.isNotNull());
    }
    @Test
    public void testNoDiff() {
        //need to setID to FX object in the screen classes
        clickOn("#nameInput").write("name");
        clickOn("Go to Room");
        verifyThat("please select difficulty", NodeMatchers.isNotNull());
    }
    @Test
    public void testValid() {
        //need to setID to FX object in the screen classes
        clickOn("#nameInput").write("name");
        clickOn("1: easy");
        clickOn("Go to Room");
        Player temp = new Player("name", None, 1);
        ArrayList<String> list = new ArrayList<>();
        list = {"name", "difficulty"};
        assertions.assertthat(game.getPlayer()).comparetobyattribute(list);
    }
}
