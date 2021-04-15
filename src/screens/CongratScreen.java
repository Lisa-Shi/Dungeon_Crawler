package screens;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class CongratScreen {


    /**
     * Creates border pane for the welcome screen.
     *
     * @return borderPane that will be used in Main.java to display welcome
     */
    public BorderPane finishLayout() {
        BorderPane border = new BorderPane();
        Label title = new Label("congratulations");
        title.setId("title"); //adds css styling
        border.setTop(title);

        //builds buttons to be in bottom L/R corners
        return border;
    }
}