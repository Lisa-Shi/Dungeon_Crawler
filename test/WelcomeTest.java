import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import sample.Main;

import static org.testfx.api.FxAssert.verifyThat;

public class WelcomeTest extends ApplicationTest {

    private Main game;

    public void start(Stage primaryStage) throws Exception {
        game = new Main();
        game.start(primaryStage);
    }

    /**
     * Tests to see if start button opens config screen.
     */
    @Test
    public void testStartButton() {
        clickOn("Start Game");
        verifyThat("Go to room", NodeMatchers.isVisible());
        verifyThat("Back", NodeMatchers.isVisible());
    }
}
