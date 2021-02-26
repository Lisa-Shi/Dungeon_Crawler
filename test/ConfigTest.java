import sample.ConfigurationScreen;
import sample.Main;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import java.awt.*;

import static org.testfx.api.FxAssert.verifyThat;

public class ConfigTest extends ApplicationTest {
    @Override
    public void start(Stage primaryStage) throws Exception{
        ConfigurationScreen config = new ConfigurationScreen();
    }
    @Test
    public void testNoNamePlayer() {
        clickOn("1: easy");
        verifyThat("We are in the second screen now!", NodeMatchers.isNotNull());
    }
}
