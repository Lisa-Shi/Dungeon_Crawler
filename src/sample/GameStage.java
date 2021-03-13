package sample;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.FileWriter;
import java.util.Random;

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
    private Stage stage;

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

        /*
        room.add(new ExitTile(room, 9, 0, null));
        room.add(new ExitTile(room, 0, 9, null));
        room.add(new ExitTile(room, 9, 19, null));
        room.add(new ExitTile(room, 19, 9, null));
         */

        room.generateExits(4);

        // test code, could make shapes into functions later
        room.add(new WallTile(room, 9, 9));
        room.add(new WallTile(room, 8, 9));
        room.add(new WallTile(room, 10, 9));
        room.add(new WallTile(room, 9, 8));
        room.add(new WallTile(room, 9, 10));
        room.add(new WallTile(room, 7, 7));
        room.add(new WallTile(room, 11, 11));
        room.add(new WallTile(room, 7, 11));
        room.add(new WallTile(room, 11, 7));
    }

    /**
     * Finishes setting up the room where the main game
     * takes place
     *
     * @param stage Stage to set up main game on
     */
    public void start(Stage stage) {
        this.stage = stage;
        Scene scene = new Scene(createContent());

        pane.getChildren().add(player.getSprite());

        HBox infoBar = new HBox();


        Text text = new Text();
        text.setFont(new Font(20));
        text.setText("$" + player.getMoney());
        text.setX(0);
        text.setY(20);
        text.setTextAlignment(TextAlignment.LEFT);

        Text testingPurpose = new Text();
        testingPurpose.setFont(new Font(20));
        testingPurpose.setText("now in room " + room.getRoomId() + " \n");
        testingPurpose.setX(0);
        testingPurpose.setY(50);
        testingPurpose.setTextAlignment(TextAlignment.LEFT);
        infoBar.getChildren().add(testingPurpose);

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

        for( ExitTile exit: room.getExits()){
            if (exit.collisionWithPlayerEvent(player)){
                enterRoom(exit);
                break;
            }
        }
        room.update(camera);
        player.update(camera, room.getCollideables());
        camera.update(null);
    }

    public void enterRoom(ExitTile fromExit){
        room = fromExit.getLinkedRoom();
        room.generateExits(fromExit);
        pane = new Pane();
        pane.setPrefSize(Main.GAME_WIDTH, Main.GAME_HEIGHT);
        room.finalize(pane);
        Scene scene = new Scene(pane);
        pane.getChildren().add(player.getSprite());
        HBox infoBar = new HBox();
        Text text = new Text();
        text.setFont(new Font(20));
        text.setText("$" + player.getMoney());
        text.setX(0);
        text.setY(20);
        text.setTextAlignment(TextAlignment.LEFT);
        Text testingPurpose = new Text();
        testingPurpose.setFont(new Font(20));
        testingPurpose.setText("now in room " + room.getRoomId() +" \n");
        testingPurpose.setX(0);
        testingPurpose.setY(50);
        testingPurpose.setTextAlignment(TextAlignment.LEFT);
        infoBar.getChildren().add(testingPurpose);
        infoBar.getChildren().add(text);
        pane.getChildren().add(infoBar);
        scene.setOnKeyPressed(keyPressed);
        scene.setOnKeyReleased(keyReleased);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Derived from https://stackoverflow.com/questions/39007382/moving-two-rectangles-with-keyboard-in-javafx
     * Register key press to start moving player in direction specified
     *
     */
    private EventHandler<KeyEvent> keyPressed = new EventHandler<>() {
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
     */
    private EventHandler<KeyEvent> keyReleased = new EventHandler<>() {

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