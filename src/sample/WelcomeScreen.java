package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WelcomeScreen {

    public Button startButton;
    public Button exitButton;

    /**
     * Creates border pane for the welcome screen.
     *
     * @return borderPane that will be used in Main.java to display welcome
     */
    public BorderPane welcomeLayout() {

        BorderPane border = new BorderPane();

        Label title = new Label("Dungeon Crawler");
        Font titleFont = new Font("Times New Roman", 25);
        title.setFont(titleFont);

        VBox buttonBox = new VBox();
        startButton = new Button("Start Game");
        exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            Stage stage = (Stage)exitButton.getScene().getWindow();
            stage.close();
        });
        buttonBox.getChildren().addAll(startButton, exitButton);

        border.setTop(title);
        border.setBottom(buttonBox);

        return border;

    }
}
