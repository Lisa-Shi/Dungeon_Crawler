package screens;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import main.Main;

/**
 * Creates the scene that appears when the player is defeated.
 */

public class LoseScreen {
    private Button restart;
    private Button exit;
    private BorderPane pane;
    private Scene scene;

    /**
     * Sets up the initial title of the pane.
     *
     * Sets the scene to be the pane.
     */
    public LoseScreen() {
        this.pane = new BorderPane();
        Label title = new Label("The monsters won this time...");
        title.setId("title");
        pane.setTop(title);

        this.scene = new Scene(pane, Main.GAME_WIDTH, Main.GAME_HEIGHT);
    }

    /**
     * Sets the stage to be the scene.
     * @param stage Main stage
     */
    public void setStage(Stage stage) {
        scene.getStylesheets().add("stylesheet.css");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Adds the buttons to the scene
     * @param exit button from game stage
     * @param restart button from game stage
     */
    public void createButton(Button exit, Button restart) {
        this.exit = exit;
        exit.setId("exit");
        this.restart = restart;
        restart.setId("restart");
        HBox buttonBox = LRNavigate.buildBox(restart, exit);
        try {
            Image image = new Image(Main.class.getResource(
                    "../gameobjects/graphics/sprites/monster/monstersAll.png").toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(Main.GAME_HEIGHT  / 1.5);
            imageView.setFitWidth(Main.GAME_WIDTH  / 1.5);
            imageView.setPreserveRatio(true);

            pane.setCenter(imageView);
        } catch (Exception e) {
            System.out.println("Restart Image failed to print");
        }

        pane.setBottom(buttonBox);

        exit.setOnAction(e -> {
            Stage stage = (Stage) exit.getScene().getWindow();
            stage.close();
            Platform.exit();
        });

    }

    public Button getExit() {
        return exit;
    }

    public Button getRestart() {
        return restart;
    }

    public BorderPane getPane() {
        return pane;
    }
}
