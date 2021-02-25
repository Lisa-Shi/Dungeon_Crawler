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

public class Room extends Stage {
    private Pane pane = new Pane();

    private double t = 0;

    private boolean playerIsMovingLeft;
    private boolean playerIsMovingRight;
    private boolean playerIsMovingUp;
    private boolean playerIsMovingDown;

    // load the image
    private Player player = new Player("Test Player", new Weapon("Test Weapon", "A test weapon.", 3, 5), 200, 200);

    // SEE: https://www.youtube.com/watch?v=FVo1fm52hz0
    public Room() {
    }

    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createContent());
        Canvas c = new Canvas(scene.getWidth(), scene.getHeight());

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
        pane.setPrefSize(800, 800);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        timer.start();

        generateDungeon();

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

        player.update();

    }

    private void generateDungeon() {

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                int tileWidth = 64;
                int tileHeight = 64;

                Sprite s = new Sprite(90 + i * tileWidth, 90 + j * tileHeight, tileWidth, tileHeight, "floor_type", new Image(getClass().getResource("spr_dungeon_tile.png").toExternalForm()));
                pane.getChildren().add(s);
            }
        }
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