package sample;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameStage extends Stage {
    private Pane pane = new Pane();

    private double t = 0;

    private boolean playerIsMovingLeft;
    private boolean playerIsMovingRight;
    private boolean playerIsMovingUp;
    private boolean playerIsMovingDown;

    private Player player;
    private Camera camera;
    private Room room;

    // SEE: https://www.youtube.com/watch?v=FVo1fm52hz0
    public GameStage() {
        player = new Player("Test Player", new Weapon("Test Weapon", "A test weapon.", 3, 5), 200, 200);
        camera = new Camera(Main.GAME_WIDTH / 2, Main.GAME_HEIGHT / 2, player);

        Vector2D[] exitLocations = new Vector2D[4];
        exitLocations[0] = new Vector2D(9, 0);
        exitLocations[1] = new Vector2D(0, 9);
        exitLocations[2] = new Vector2D(9, 19);
        exitLocations[3] = new Vector2D(19, 9);
        room = new Room(20, 20, exitLocations);
    }

    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createContent());

        pane.getChildren().add(player.getSprite());

        scene.setOnKeyPressed(e -> {

            switch (e.getCode()) {
            }
        });

        scene.setOnKeyPressed(keyPressed);
        scene.setOnKeyReleased(keyReleased);

        stage.setScene(scene);

        stage.show();

    }

    private Parent createContent() {
        pane.setPrefSize(Main.GAME_WIDTH, Main.GAME_HEIGHT);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        timer.start();

        room.draw(pane);

        return pane;
    }

    public void update() {
        if (playerIsMovingUp) {
            player.getPhysics().pushUp(Main.DEFAULT_CONTROL_PLAYER_FORCE);
        }
        if (playerIsMovingDown) {
            player.getPhysics().pushDown(Main.DEFAULT_CONTROL_PLAYER_FORCE);
        }
        if (playerIsMovingLeft) {
            player.getPhysics().pushLeft(Main.DEFAULT_CONTROL_PLAYER_FORCE);
        }
        if (playerIsMovingRight) {
            player.getPhysics().pushRight(Main.DEFAULT_CONTROL_PLAYER_FORCE);
        }

        room.update(camera);
        player.update(camera);
        camera.update();

    }

    // Thanks to https://stackoverflow.com/questions/39007382/moving-two-rectangles-with-keyboard-in-javafx

    private EventHandler<KeyEvent> keyPressed = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.A) {
                playerIsMovingLeft = true;
            }
            if (event.getCode() == KeyCode.D) {
                playerIsMovingRight = true;
            }
            if (event.getCode() == KeyCode.W) {
                playerIsMovingUp = true;
            }
            if (event.getCode() == KeyCode.S) {
                playerIsMovingDown = true;
            }
        }
    };
    private EventHandler<KeyEvent> keyReleased = new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.A) {
                playerIsMovingLeft = false;
            }
            if (event.getCode() == KeyCode.D) {
                playerIsMovingRight = false;
            }
            if (event.getCode() == KeyCode.W) {
                playerIsMovingUp = false;
            }
            if (event.getCode() == KeyCode.S) {
                playerIsMovingDown = false;
            }
        }

    };

}