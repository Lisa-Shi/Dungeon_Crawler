package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WelcomeScreen {

    private Button startButton;
    private Button exitButton;

    /**
     * Creates border pane for the welcome screen.
     *
     * @return borderPane that will be used in Main.java to display welcome
     */
    public BorderPane welcomeLayout() {

        BorderPane border = new BorderPane();

        Label title = new Label("Dungeon Crawler");
        title.setId("title"); //adds css styling

        VBox buttonBox = new VBox();
        startButton = new Button("Start Game");
        exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        });
        border.setTop(title);

        //builds buttons to be in bottom L/R corners
        HBox bottom = LRNavigate.buildBox(exitButton, startButton);
        bottom.setPadding(new Insets(10));
        border.setBottom(bottom);

        return border;

    }

    /**
     * getter for exit button
     *
     * @return exit Button
     */

    public Button getExitButton() {
        return exitButton;
    }

    /**
     * getter for start button
     *
     * @return start button
     */
    public Button getStartButton() {
        return startButton;
    }
}
