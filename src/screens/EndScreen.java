package screens;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.Main;

/**
 * Creates the scene that appears when the player is defeated.
 */

public class EndScreen {
    private Button restart;
    private Button exit;
    private BorderPane pane;
    private Scene scene;
    private boolean isWinner;

    /**
     * Sets up the initial title of the pane.
     *
     * Sets the scene to be the pane.
     * @param isWinner true if player won, false if player lost
     */
    public EndScreen(boolean isWinner) {
        this.pane = new BorderPane();
        this.isWinner = isWinner;
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

    public ImageView createImg(String file) {
        try {
            //Image image = new Image(Main.class.getResource(
            //        "../image/Monster/monstersAll.png").toExternalForm());
            Image image = new Image(Main.class.
                    getResource("../screens/" + file).
                    toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(Main.GAME_HEIGHT  / 1.5);
            imageView.setFitWidth(Main.GAME_WIDTH  / 1.5);
            imageView.setPreserveRatio(true);

            return imageView;
        } catch (Exception e) {
            System.out.println("Restart Image failed to print");
            return null;
        }
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

        Label title;
        if (isWinner) {
            title = new Label("Victory");
            //allows for more images to be stacked on, idk rn the naked baby scares me
            StackPane stack = new StackPane();
            ImageView img = createImg("stars.png");
            stack.getChildren().addAll(img);
            pane.setCenter(stack);
            pane.setTop(title);
        } else {
            title = new Label("The monsters won this time...");
            pane.setTop(title);
            ImageView img = createImg("monstersAll.png");
            pane.setCenter(img);
        }

        pane.getStylesheets().add("stylesheet.css");

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
