package sample;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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

    /**
     * Constructs the Stage where the main game takes place
     * Adapted from https://www.youtube.com/watch?v=FVo1fm52hz0
     *
     * @param player the player user will be controlling in the game
     */
    public GameStage(Player player) {
        this.player = player;
        camera = new Camera(Main.GAME_WIDTH / 2, Main.GAME_HEIGHT / 2, player);

        room = new Room(20, 20);

        room.addExit(new Exit(room, 9, 0, null));
        room.addExit(new Exit(room, 0, 9, null));
        room.addExit(new Exit(room, 9, 19, null));
        room.addExit(new Exit(room, 19, 9, null));

        //room.finalize(pane);
    }

    /**
     * Finishes setting up the room where the main game
     * takes place
     *
     * @param stage Stage to set up main game on
     */
    public void start(Stage stage) {
        Scene scene = new Scene(createContent());

        pane.getChildren().add(player.getSprite());

        HBox infoBar = new HBox();


        Text text = new Text();
        text.setFont(new Font(20));
        text.setText("$" + player.getMoney());
        text.setX(0);
        text.setY(20);
        text.setTextAlignment(TextAlignment.LEFT);

        infoBar.getChildren().add(text);
        pane.getChildren().add(infoBar);

        scene.setOnKeyPressed(keyPressed);
        scene.setOnKeyReleased(keyReleased);

        stage.setScene(scene);

        stage.show();

    }

    /**
     * Sets up the parent Pane object where the main game
     * takes place
     *
     * @return the created parent
     */
    private Parent createContent() {
        pane.setPrefSize(Main.GAME_WIDTH, Main.GAME_HEIGHT);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        timer.start();

        room.finalize(pane);

        return pane;
    }

    /**
     * Updates room physics, player movement from keyboard input,
     * etc.
     */
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
        camera.update(null);

        // Check for collisions
        for (Collideable collideable : room.getCollideables()) {
            if (player.getCollisionBox().collidedWith(collideable.getCollisionBox())) {
                //System.out.println("Collision detected with " + collideable);
            }
        }
    }


    /**
     * Derived from https://stackoverflow.com/questions/39007382/moving-two-rectangles-with-keyboard-in-javafx
     * Register key press to start moving player in direction specified
     *
     * @return the event information
     */
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
    /**
     * Derived from https://stackoverflow.com/questions/39007382/moving-two-rectangles-with-keyboard-in-javafx
     * Register key release to stop moving player in direction specified
     *
     * @return the event information
     */
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