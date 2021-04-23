package main;

import gamemap.ChallengeRoom;
import gamemap.GameMap;
import gamemap.Room;
import gameobjects.*;
import gameobjects.physics.Vector2D;
import gameobjects.tiles.ExitTile;
import gameobjects.graphics.functionality.ImageReel;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javafx.scene.control.*;
import javafx.util.Duration;
import gameobjects.monsters.Monster;
import gameobjects.physics.Camera;
import screens.EndScreen;

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

    private Button winButton;
    private Button restartButton;
    private Button exitButton;

    private AnimationTimer timer;
    private Player player;
    private Camera camera;
    private Room room;
    private GameMap map;
    private Stage stage;
    private Scene scene;
    private HBox infoBar = new HBox();
    private Text text = new Text();
    private Text healthText = new Text();
    private Text testingPurpose = new Text();
    private static Room prevRoom;
    private StackPane inv;
    private ProgressBar pbar = new ProgressBar(0);
    private LinkedList<ProgressBar> monsterHP = new LinkedList<>();
    public GameMap getMap() {
        return map;
    }
    /**
     * Constructs the Stage where the main game takes place
     * Adapted from https://www.youtube.com/watch?v=FVo1fm52hz0
     *
     * @param player the player user will be controlling in the game
     * @param firstRoom first room in the game
     */
    public GameStage(Player player, Room firstRoom) {
        this.player = player;
        camera = new Camera(Main.GAME_WIDTH / 2, Main.GAME_HEIGHT / 2, player);

        room = firstRoom;
        map = new GameMap(room);

        winButton = new Button("finish");
        restartButton = new Button("restart");
        exitButton = new Button("exit");

        Timeline timeline = new Timeline(new KeyFrame(
            Duration.millis(Main.MONSTER_ATTACK_TIME),
            ae -> moveMonsters()));
        timeline.play();
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

        pane.getChildren().add(player.getGraphics().getSprite());

        text.setFont(new Font(20));
        text.setText("$" + player.getMoney());
        healthText.setText("HP: " + player.getHealth());
        text.setX(0);
        text.setY(20);
        text.setTextAlignment(TextAlignment.LEFT);


        testingPurpose.setFont(new Font(20));
        testingPurpose.setText("now in room " + room.getRoomId() + " \n");
        testingPurpose.setX(0);
        testingPurpose.setY(50);
        testingPurpose.setTextAlignment(TextAlignment.LEFT);
        infoBar.getChildren().add(testingPurpose);

        Insets insets = new Insets(6);

        pbar.setPadding(insets);

        infoBar.getChildren().addAll(text, healthText, pbar);
        pane.getChildren().add(infoBar);

        scene.setOnKeyPressed(keyPressed);
        scene.setOnKeyReleased(keyReleased);

        room.getCollideables().add(player);

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
        timer = new AnimationTimer() {
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
     * Updates room gameobjects.physics, player movement from keyboard input,
     * etc.
     */
    private final List<Image> direction = new ArrayList<>();

    /**
     * If player health is 0, then lose.
     *
     * If end room, and final boss defeated, then win.
     *
     * else update
     */
    public void update() {
        if (player.getHealth() <= 0) {
            stop(false);
        } else if (room.getMonsters().size() == 0 && room.getRoomId() == 999) {
            stop(true);
        } else {
            player.update(camera, room.getCollideables());

            switchPlayerGraphicsStateToStill();

            movePlayer();
            teleportPlayerToEnteredRoom();

            room.update(camera);
            camera.update(null);

            updateText();
        }

    }

    /**
     * Helper method to stop the game.
     *
     * Clears the room so that all sprites will stop.
     * Restarts the map so that game restarts in room 0.
     * Creates the losing screen.
     *
     * @param isWinner true if player won; false if player lost
     */
    private void stop(boolean isWinner) {
        timer.stop();
        room.restart();
        map.restartMap();
        EndScreen screen = new EndScreen(isWinner);
        screen.createButton(exitButton, restartButton);
        screen.setStage(stage);
    }

    private void updateText() {
        text.setText("$" + player.getMoney());
        healthText.setText("HP: " + player.getHealth());
        testingPurpose.setText("now in room " + room.getRoomId() + " \n");
    }
    private void switchPlayerGraphicsStateToStill() {
        double threshold = 0.1D;
        //if (Math.abs(player.getPhysics().getVelocity().getX())
        // < threshold && Math.abs(player.getPhysics().getVelocity().getY()) < threshold) {
        if (!playerIsMovingLeft && !playerIsMovingDown
                && !playerIsMovingRight && !playerIsMovingUp) {
            ImageReel currReel = player.getGraphics().getCurrentReel();
            if (currReel == player.getSpriteSheet().getWalkSheet().getLeftImage()) {
                player.getGraphics().setCurrentReel(
                        player.getSpriteSheet().getStandSheet().getLeftImage());
            } else if (currReel == player.getSpriteSheet().getWalkSheet().getRightImage()) {
                player.getGraphics().setCurrentReel(
                        player.getSpriteSheet().getStandSheet().getRightImage());
            } else if (currReel == player.getSpriteSheet().getWalkSheet().getUpImage()) {
                player.getGraphics().setCurrentReel(
                        player.getSpriteSheet().getStandSheet().getUpImage());
            } else if (currReel == player.getSpriteSheet().getWalkSheet().getDownImage()) {
                player.getGraphics().setCurrentReel(
                        player.getSpriteSheet().getStandSheet().getDownImage());
            }
        }
    }
    private void movePlayer() {
        if (playerIsMovingLeft) {
            player.getPhysics().pushLeft(Main.DEFAULT_CONTROL_PLAYER_FORCE);
        } else if (playerIsMovingRight) {
            player.getPhysics().pushRight(Main.DEFAULT_CONTROL_PLAYER_FORCE);
        }

        if (playerIsMovingUp) {
            player.getPhysics().pushUp(Main.DEFAULT_CONTROL_PLAYER_FORCE);
        } else if (playerIsMovingDown) {
            player.getPhysics().pushDown(Main.DEFAULT_CONTROL_PLAYER_FORCE);
        }
    }
    private void moveMonsters() {
        for (int i = 0; i < room.getMonsters().size(); i++) {
            Monster monster = room.getMonsters().get(i);

            if (!monster.isDead()) {
                monster.face(player);
                monster.update(camera);
                monster.attack(room, pane, player);
            } else {
                player.getItem(monster.die());
                i--;
            }
        }
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(Main.MONSTER_ATTACK_TIME),
            ae -> moveMonsters()));
        timeline.play();
    }


    private void teleportPlayerToEnteredRoom() {
        if (room != null && map != null && !GameMap.enterRoom().equals(room)) {
            prevRoom = room;
            room = GameMap.enterRoom();
            enterRoom();
            player.update(camera, room.getCollideables());
            matchPlayerExit(prevRoom);

            if (!room.getCollideables().contains(player)) {
                room.getCollideables().add(player);
            }
        }
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

        for (ExitTile tile : exits) {
            if (tile.getLinkedRoom().getRoomId() == previous.getRoomId()) {
                GameObject obj = (GameObject) tile;
                double x = obj.getPhysics().getPosition().getX();
                double y = obj.getPhysics().getPosition().getY();
                int exitX = tile.getExitX();
                int exitY = tile.getExitY();

                double distance = 1;
                boolean teleport = false;
                Image image = null;
                if (exitX == -1) {
                    //right wall
                    teleport = true;
                    x += distance * Main.TILE_WIDTH;
                    player.getGraphics().setCurrentReel(
                            player.getSpriteSheet().getStandSheet().getLeftImage());
                } else if (exitX == room.getWidth()) {
                    //left wall
                    teleport = true;
                    x -= distance * Main.TILE_WIDTH;
                    player.getGraphics().setCurrentReel(
                            player.getSpriteSheet().getStandSheet().getRightImage());
                } else if (exitY == -1) {
                    //top
                    teleport = true;
                    y += distance * Main.TILE_HEIGHT;
                    player.getGraphics().setCurrentReel(
                            player.getSpriteSheet().getStandSheet().getDownImage());
                } else if (exitY == room.getWidth()) {
                    //bottom
                    teleport = true;
                    y -= distance * Main.TILE_HEIGHT;
                    player.getGraphics().setCurrentReel(
                            player.getSpriteSheet().getStandSheet().getUpImage());
                }
                if (teleport) {
                    Vector2D vec = new Vector2D(x, y);
                    player.getPhysics().setPosition(vec);
                    player.getPhysics().setVelocity(new Vector2D(0, 0));
                }
            }
        }
    }
    public void enterRoom() {
        pane = new Pane();
        pane.setPrefSize(Main.GAME_WIDTH, Main.GAME_HEIGHT);
        room.generateExits(map.getAdjRooms(room));
        room.finalize(pane);
        pbar.setProgress(room.getRoomId() / 9.0);
        monsterHP.clear();
        Scene scene = new Scene(pane);

        text.setText("$" + player.getMoney());
        testingPurpose.setText("now in room " + room.getRoomId() + " \n");
        pane.getChildren().add(player.getGraphics().getSprite());
        pane.getChildren().add(infoBar);
        scene.setOnKeyPressed(keyPressed);
        scene.setOnKeyReleased(keyReleased);
        stage.setScene(scene);
        stage.show();
    }


    public static Room getPrevRoom() {
        return prevRoom;
    }

    public Room getRoom() {
        return room;
    }
    /**
     * Derived from https://stackoverflow.com/questions/39007382/moving-two-rectangles-with-keyboard-in-javafx
     * Register key press to start moving player in direction specified
     *
     */

    private EventHandler<KeyEvent> keyPressed = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.A && player.isMoveable()) {
                playerIsMovingLeft = true;
                player.getGraphics().setCurrentReel(
                        player.getSpriteSheet().getWalkSheet().getLeftImage());
            }
            if (event.getCode() == KeyCode.D && player.isMoveable()) {
                playerIsMovingRight = true;
                player.getGraphics().setCurrentReel(
                        player.getSpriteSheet().getWalkSheet().getRightImage());
            }
            if (event.getCode() == KeyCode.W && player.isMoveable()) {
                playerIsMovingUp = true;
                player.getGraphics().setCurrentReel(
                        player.getSpriteSheet().getWalkSheet().getUpImage());
            }
            if (event.getCode() == KeyCode.S && player.isMoveable()) {
                playerIsMovingDown = true;
                player.getGraphics().setCurrentReel(
                        player.getSpriteSheet().getWalkSheet().getDownImage());
            }
            if (event.getCode() == KeyCode.ENTER && player.isMoveable()) {
                player.launchProjectile(room, pane, camera, room.getMonsters());
            }
            if (event.getCode() == KeyCode.E && player.isMoveable()) {
                //check if there is openable chest/npc in front of player;
                Vector2D center = player.getPhysics().getPosition().add(
                        player.getDirection().multiply(Main.TILE_HEIGHT));
                Interactable interactable = room.findInteractable(center);
                if (interactable != null) {
                    player.setMoveability(false);
                    interactable.open(player, pane);
                }
            }
            if (event.getCode() == KeyCode.Q && player.isMoveable()) {
                player.setMoveability(false);
                Inventory inventory = Inventory.getInstance(player, pane, player);
                inventory.show();
            }
            if (event.getCode() == KeyCode.TAB){
                //for demo purpose
                for( Monster monster: room.getMonsters()){
                    monster.getHPBar().expire();
                    pane.getChildren().remove(monster.getGraphics().getSprite());
                }
                room.getMonsters().clear();

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
            if (event.getCode() == KeyCode.A) {
                playerIsMovingLeft = false;
                player.setDirection(new Vector2D(-1, 0));
                fixPlayerFacingDirection();
            }
            if (event.getCode() == KeyCode.D) {
                playerIsMovingRight = false;
                player.setDirection(new Vector2D(1, 0));
                fixPlayerFacingDirection();
            }
            if (event.getCode() == KeyCode.W) {
                playerIsMovingUp = false;
                player.setDirection(new Vector2D(0, -1));
                fixPlayerFacingDirection();
            }
            if (event.getCode() == KeyCode.S) {
                playerIsMovingDown = false;
                player.setDirection(new Vector2D(0, 1));
                fixPlayerFacingDirection();
            }
        }
    };
    private void fixPlayerFacingDirection() {
        if (playerIsMovingUp) {
            player.getGraphics().setCurrentReel(
                    player.getSpriteSheet().getWalkSheet().getUpImage());
        } else if (playerIsMovingDown) {
            player.getGraphics().setCurrentReel(
                    player.getSpriteSheet().getWalkSheet().getDownImage());
        } else if (playerIsMovingLeft) {
            player.getGraphics().setCurrentReel(
                    player.getSpriteSheet().getWalkSheet().getLeftImage());
        } else if (playerIsMovingRight) {
            player.getGraphics().setCurrentReel(
                    player.getSpriteSheet().getWalkSheet().getRightImage());
        }
    }

    public Button getExitButton() {
        return winButton;
    }
    public Camera getCamera() {
        return camera;
    }
    public Pane getPane() {
        return pane;
    }
    public Player getPlayer() {
        return player;
    }

    public Button getRestartButton() {
        return restartButton;
    }
}