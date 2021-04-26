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

    public static void createImage(BorderPane border, String file) {

        //Creating an image
        try {
            StackPane stackPane = new StackPane();
            Image image = new Image(Main.class.getResource("../screens/" + file).toExternalForm(), Main.GAME_WIDTH, Main.GAME_HEIGHT, true, true);
            BackgroundImage backgroundImage = new BackgroundImage(image, NO_REPEAT, NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
//            ImageView imageView = new ImageView(image);
//            //setting the fit height and width of the image view
//            imageView.setFitHeight(Main.GAME_HEIGHT * 3 / 4.0);
//            imageView.setFitWidth(Main.GAME_WIDTH);
//
//            //Setting the preserve ratio of the image view
//            imageView.setPreserveRatio(true);
//
//            border.setCenter(imageView);
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
