package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javafx.scene.control.*;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameStage extends Stage {
    private Pane pane = new Pane();

    private double t = 0;

    private boolean playerIsMovingLeft;
    private boolean playerIsMovingRight;
    private boolean playerIsMovingUp;
    private boolean playerIsMovingDown;

    private Button exitButton;
    private Player player;
    private Camera camera;
    private Room room;
    private GameMap map;
    private Stage stage;
    private Scene scene;
    private HBox infoBar = new HBox();
    private Text text = new Text();
    private Text testingPurpose = new Text();

    public GameMap getMap() {
        return map;
    }
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
        map = new GameMap(room);
        exitButton = new Button("finish");
        /*
        room.add(new ExitTile(room, 9, 0, null));
        room.add(new ExitTile(room, 0, 9, null));
        room.add(new ExitTile(room, 9, 19, null));
        room.add(new ExitTile(room, 19, 9, null));
         */
    }

    /**
     * Finishes setting up the room where the main game
     * takes place
     *
     * @param stage Stage to set up main game on
     */
    public void start(Stage stage) {
        this.stage = stage;
        scene = new Scene(createContent());

        pane.getChildren().add(player.getSprite());
        text.setFont(new Font(20));
        text.setText("$" + player.getMoney());
        text.setX(0);
        text.setY(20);
        text.setTextAlignment(TextAlignment.LEFT);


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
    private final List<Image> direction = new ArrayList<>();
    public void update() {
        KeyFrame frame;
        List<Image>[] direction = new List[1];
        if (playerIsMovingUp) {
            player.getPhysics().pushUp(Main.DEFAULT_CONTROL_PLAYER_FORCE);
            direction[0] = Main.WALKNORTH;
        }
        if (playerIsMovingDown) {
            player.getPhysics().pushDown(Main.DEFAULT_CONTROL_PLAYER_FORCE);
            direction[0] = Main.WALKSOUTH;
        }
        if (playerIsMovingLeft) {
            player.getPhysics().pushLeft(Main.DEFAULT_CONTROL_PLAYER_FORCE);
            direction[0] = Main.WALKWEST;
        }
        if (playerIsMovingRight) {
            player.getPhysics().pushRight(Main.DEFAULT_CONTROL_PLAYER_FORCE);
            direction[0] = Main.WALKEAST;
        }
        if( direction[0] != null) {
            frame = new KeyFrame(Duration.millis(50), e -> {
                player.getSprite().setImage(direction[0].get((imageindex++) % 2));

            });
            timeline.getKeyFrames().add(frame);
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
        player.update(camera, room.getCollideables());

        if (!GameMap.enterRoom().equals(room)) {
            Room previous = room;
            room = GameMap.enterRoom();
            enterRoom();
            player.update(camera, room.getCollideables());
            matchPlayerExit(previous);
        }
        room.update(camera);
        camera.update(null);
    }

    /**
     * Matches player's position to entrance.
     *
     * Takes in the previous room.
     * Finds the corresponding exit in current room.
     * Sets the player's position to be at the exit.
     *
     * @param previous room
     */
    public void matchPlayerExit(Room previous) {
        LinkedList<ExitTile> exits = room.getExits();
        System.out.println("NEW ROOM");

        for (ExitTile tile : exits) {
            System.out.println("exit id:" + tile.getLinkedRoom().getRoomId());

            if (tile.getLinkedRoom().getRoomId() == previous.getRoomId()) {
                GameObject obj = (GameObject) tile;
                double x = obj.getPhysics().getPosition().getX();
                double y = obj.getPhysics().getPosition().getY();
                int exitX = tile.getExitX();
                int exitY = tile.getExitY();
                System.out.println("Exit simple x: " + exitX + " y:" + exitY);
                double distance = 1;
                boolean teleport = false;
                Image image = null;
                if (exitX == -1) {
                    //right wall
                    teleport = true;
                    x += distance * Main.TILE_WIDTH;
                    image = Main.PLAYER_IMAGE.get(3);
                } else if (exitX == room.getWidth()) {
                    //left wall
                    teleport = true;
                    x -= distance * Main.TILE_WIDTH;
                    image = Main.PLAYER_IMAGE.get(1);
                } else if (exitY == -1) {
                    //top
                    teleport = true;
                    y += distance * Main.TILE_HEIGHT;
                    image = Main.PLAYER_IMAGE.get(2);
                } else if (exitY == room.getWidth()) {
                    //bottom
                    teleport = true;
                    y -= distance * Main.TILE_HEIGHT;
                    image = Main.PLAYER_IMAGE.get(0);
                }
                if (teleport) {
                    System.out.println("New x: " + x + " y:" + y);
                    Vector2D vec = new Vector2D(x, y);
                    player.getPhysics().setPosition(vec);
                    player.getPhysics().setVelocity(new Vector2D(0, 0));
                    player.getSprite().setImage(image);
                }
            }
        }
    }
    public void enterRoom() {
        pane = new Pane();
        pane.setPrefSize(Main.GAME_WIDTH, Main.GAME_HEIGHT);
        room.generateExits(map.getAdjRooms(room));

        room.finalize(pane);
        Scene scene = new Scene(pane);
        if (room.getRoomId() == 999) {
            infoBar.getChildren().add(exitButton);
        } else {
            infoBar.getChildren().remove(exitButton);
        }
        text.setText("$" + player.getMoney());
        testingPurpose.setText("now in room " + room.getRoomId() + " \n");
        pane.getChildren().add(player.getSprite());
        pane.getChildren().add(infoBar);
        scene.setOnKeyPressed(keyPressed);
        scene.setOnKeyReleased(keyReleased);
        stage.setScene(scene);
        stage.show();
    }
    public Room getRoom() {
        return room;
    }
    /**
     * Derived from https://stackoverflow.com/questions/39007382/moving-two-rectangles-with-keyboard-in-javafx
     * Register key press to start moving player in direction specified
     *
     */
    public Timeline timeline = new Timeline();
    public int imageindex = 0;
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
     */
    private EventHandler<KeyEvent> keyReleased = new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent event) {
            Image image = null;
            if (event.getCode() == KeyCode.A) {
                playerIsMovingLeft = false;
                image = Main.PLAYER_IMAGE.get(3);
            }
            if (event.getCode() == KeyCode.D) {
                playerIsMovingRight = false;
                image = Main.PLAYER_IMAGE.get(1);
            }
            if (event.getCode() == KeyCode.W) {
                playerIsMovingUp = false;
                image = Main.PLAYER_IMAGE.get(0);
            }
            if (event.getCode() == KeyCode.S) {
                playerIsMovingDown = false;
                image = Main.PLAYER_IMAGE.get(2);
            }
            player.getSprite().setImage(image);
            timeline.stop();
        }
    };
    public Button getExitButton() {
        return exitButton;
    }

}