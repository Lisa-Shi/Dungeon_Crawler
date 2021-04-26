package screens;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import main.Main;

import java.io.FileInputStream;

import static javafx.scene.layout.BackgroundRepeat.NO_REPEAT;

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

        createImage(border, "dungeon.png");

        return border;

    }

    /**
     * Creates a background image for border pane passed in
     * @param border border pane
     * @param file name of image
     */
    private static void createImage(BorderPane border, String file) {

        //Creating a background image
        try {
            Image image = new Image(Main.class.getResource("../screens/" + file).toExternalForm(),
                    Main.GAME_WIDTH, Main.GAME_HEIGHT, true, true);
            BackgroundImage backgroundImage = new BackgroundImage(image, NO_REPEAT, NO_REPEAT,
                    BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
            border.setBackground(new Background(backgroundImage));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
