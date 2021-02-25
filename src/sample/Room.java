package sample;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class Room extends Stage {
    private Pane pane = new Pane();

    private double t = 0;

    // load the image
    private final Image playerImage = new Image(getClass().getResource("testimg.png").toExternalForm());

    private Sprite player = new Sprite(300, 750, 40, 40, "player", playerImage);

    // SEE: https://www.youtube.com/watch?v=FVo1fm52hz0
    public Room() {
    }

    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createContent());
        Canvas c = new Canvas(scene.getWidth(), scene.getHeight());

        pane.getChildren().add(player);

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A:
                    player.moveLeft();
                    break;
                case D:
                    player.moveRight();
                    break;
                case W:
                    //player.moveUp();
                    break;
                case S:
                    //player.moveDown();
                    break;
                case SPACE:
                    shoot(player);
                    break;
            }
        });

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

        nextLevel();

        return pane;
    }

    private void nextLevel() {

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                int tileWidth = 64;
                int tileHeight = 64;

                Sprite s = new Sprite(90 + i * tileWidth, 90 + j * tileHeight, tileWidth, tileHeight, "floor_type", new Image(getClass().getResource("spr_dungeon_tile.png").toExternalForm()));
                pane.getChildren().add(s);
            }
        }

        /*
        for (int i = 0; i < 5; i++) {
            Sprite s = new Sprite(90 + i*100, 150, 30, 30, "enemy", playerImage);

            pane.getChildren().add(s);
        }

        */
    }

    private List<Sprite> sprites() {
        return pane.getChildren().stream().map(n -> (Sprite) n).collect(Collectors.toList());
    }

    private void update() {
        t += 0.016;

        sprites().forEach(s -> {
            switch (s.type) {
                case "enemybullet":
                    s.moveDown();

                    if (s.getBoundsInParent().intersects(player.getBoundsInParent())) {
                        player.dead = true;
                        s.dead = true;
                    }
                    break;

                case "playerbullet":
                    s.moveUp();

                    sprites().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.dead = true;
                            s.dead = true;
                        }
                    });

                    break;

                case "enemy":
                    if (t > 2) {
                        if (Math.random() < 0.3) {
                            shoot(s);
                        }
                    }

                    break;
            }
        });

        pane.getChildren().removeIf(n -> {
            Sprite s = (Sprite) n;
            return s.dead;
        });

        if (t > 2) {
            t = 0;
        }
    }

    private void shoot(Sprite who) {
        Sprite s = new Sprite((int) who.getTranslateX() + 20, (int)who.getTranslateY(), 5, 20, who.type + "bullet", playerImage);

        pane.getChildren().add(s);
    }

    private static class Sprite extends ImageView {
        boolean dead = false;
        final String type;

        Sprite(int x, int y, int w, int h, String type, Image img) {
            super(img);

            this.type = type;
            this.setFitWidth(w);
            this.setFitHeight(h);

            setTranslateX(x);
            setTranslateY(y);
        }

        public void moveLeft() {
            setTranslateX(getTranslateX() - 5);
        }

        public void moveRight() {
            setTranslateX(getTranslateX() + 5);
        }

        public void moveUp() {
            setTranslateY(getTranslateY() - 5);
        }

        public void moveDown() {
            setTranslateY(getTranslateY() + 5);
        }
    }
}