package main;

import gamemap.Room;
import gameobjects.Player;
import gameobjects.physics.collisions.PolygonWireframe;
import gameobjects.physics.collisions.RectangleWireframe;
import gameobjects.graphics.functionality.CharacterImageSheet;
import gameobjects.graphics.functionality.DirectionalImageSheet;
import gameobjects.graphics.functionality.ImageReel;
import gameobjects.graphics.functionality.ImageSheet;
import gameobjects.graphics.functionality.SingularImageSheet;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import screens.ConfigurationScreen;
import screens.CongratScreen;
import screens.WelcomeScreen;

import java.util.LinkedList;
import java.util.List;

public class Main extends Application {
    // Variables
    private Stage mainWindow;
    private Room firstRoom;
    private GameStage r;
    public static final int GAME_WIDTH = 650;
    public static final int GAME_HEIGHT = 650;

    public static final double DEFAULT_FORCE = 1;
    public static final double ENEMY_CONTROL_FORCE = 0.19D;
    public static final double DEFAULT_FRICTIONAL_FORCE = 0.18D;
    public static final double DEFAULT_CAMERA_SLOWDOWN_FACTOR = 7; // similar to a frictional force
    public static final double DEFAULT_CONTROL_PLAYER_FORCE = 0.45D;
    public static final double MAX_PLAYER_SPEED = 13D;

    public static final double PLAYER_WIDTH = 40;
    public static final double PLAYER_HEIGHT = 40;
    public static final double MONSTER_WIDTH = 65;
    public static final double MONSTER_HEIGHT = 65;
    public static final int TILE_WIDTH = 64;
    public static final int TILE_HEIGHT = 64;

    public static final int BULLET_WIDTH = 15;
    public static final int BULLET_HEIGHT = 15;
    public static final int ENEMY_BULLET_SPEED = 5;
    public static final int ENEMY_BULLET_FRICTION_FORCE = 0;
    public static final int ENEMY_BULLET_DAMAGE = 10;
    public static final int PLAYER_BULLET_DAMAGE = 5;

    //public static final int BULLET_TIME_UNTIL_EXPIRATION = 1000;
    public static final int ENEMY_BULLET_BOUNCES_UNTIL_EXPIRATION = 4;

    public static final int MONSTER_ATTACK_TIME = 1000;

    public static final String REPLACE_DIRECTION_REGEX = "<direction>";

    public static final int PLAYER_STARTING_HEALTH = 100;

    public static final int DEFAULT_COLLISION_PRECISION = 8;

    public static final PolygonWireframe TILE_WIREFRAME =
            new RectangleWireframe(Main.PLAYER_WIDTH, Main.PLAYER_HEIGHT);

    public static final Image getImageFrom(String name) {
        return new Image(Main.class.getResource(name).toExternalForm());
    }


    private static final DirectionalImageSheet PLAYER_STANDING_SHEET =
            getDirectionalImageSheet(
                    "../gameobjects/graphics/sprites/PlayerFace" + Main.REPLACE_DIRECTION_REGEX + ".png",
                    1
            );
    private static final DirectionalImageSheet PLAYER_WALKING_SHEET =
            getDirectionalImageSheet(
                    "../gameobjects/graphics/sprites/PlayerWalking" + Main.REPLACE_DIRECTION_REGEX + ".png",
                    2
            );
    public static final DirectionalImageSheet BUZZ_STANDING_SHEET =
            getDirectionalImageSheet(
                    "../gameobjects/graphics/sprites/monster/Buzz" + Main.REPLACE_DIRECTION_REGEX + ".png",
                    2
            );
    public static final DirectionalImageSheet MAGICIAN_STANDING_SHEET =
            getDirectionalImageSheet(
                    "../gameobjects/graphics/sprites/monster/magicianFace" + Main.REPLACE_DIRECTION_REGEX + ".png",
                    1
            );
    public static final DirectionalImageSheet SLIME_STANDING_SHEET =
            getDirectionalImageSheet(
                    "../gameobjects/graphics/sprites/monster/slime" + Main.REPLACE_DIRECTION_REGEX + ".png",
                    5
            );

    public static final ImageSheet ALL_MONSTER_SHEET =
            new SingularImageSheet(getImageFrom("../gameobjects/graphics/sprites/monster/monstersAll.png"));

    public static final ImageSheet MONSTER_BULLET_SHEET =
            new SingularImageSheet(getImageFrom("../gameobjects/graphics/sprites/bulletM.png"));
    public static final ImageSheet PLAYER_BULLET_SHEET =
            new SingularImageSheet(getImageFrom("../gameobjects/graphics/sprites/bulletP.png"));
    public static final ImageSheet TRANSPARENT_SHEET =
            new SingularImageSheet(getImageFrom("../gameobjects/graphics/sprites/transparent.png"));
    public static final Image TRANSPARENT_IMAGE =
            new Image(Main.class.getResource("../gameobjects/graphics/sprites/transparent.png").toExternalForm());
    private static final DirectionalImageSheet getDirectionalImageSheet(
            String directionImageBase, int numDirectionFrames) {
        // Find <direction> tag in direction gameobjects.graphics.sprites base address
        int replaceIndex = directionImageBase.indexOf(REPLACE_DIRECTION_REGEX);
        if (replaceIndex == -1) {
            throw new IllegalArgumentException("No "
                    + REPLACE_DIRECTION_REGEX + " regex to replace with direction"
                    + " in the base address for the directional images.");
        }

        String leftStr = directionImageBase.substring(0, replaceIndex);
        String rightStr = directionImageBase.substring(replaceIndex
                + REPLACE_DIRECTION_REGEX.length());

        DirectionalImageSheet sheet = new DirectionalImageSheet();
        String[] directions = new String[4];
        directions[0] = "North";
        directions[1] = "East";
        directions[2] = "South";
        directions[3] = "West";

        ImageReel[] reels = new ImageReel[4];

        for (int i = 0; i < directions.length; i++) {
            ImageReel ir = new ImageReel();

            for (int j = 0; j < numDirectionFrames; j++) {
                char letter = (char) ('A' + j);
                System.out.println(leftStr + directions[i] + letter + rightStr);
                ir.add(getImageFrom(leftStr + directions[i] + letter + rightStr));
            }

            reels[i] = ir;
        }

        sheet.setUpImage(reels[0]);
        sheet.setRightImage(reels[1]);
        sheet.setDownImage(reels[2]);
        sheet.setLeftImage(reels[3]);

        return sheet;
    }

    public static final CharacterImageSheet PLAYER_IMAGE_SHEET =
            new CharacterImageSheet(PLAYER_STANDING_SHEET, PLAYER_WALKING_SHEET);

    public static final List<Image> CHEST_IMAGE = new LinkedList<Image>() {
        {
            add(new Image(Main.class.getResource("../gameobjects/graphics/sprites/closedChest.png").toExternalForm()));
            add(new Image(Main.class.getResource("../gameobjects/graphics/sprites/openChest.png").toExternalForm()));
            add(new Image(Main.class.getResource("../gameobjects/graphics/sprites/emptyChest.png").toExternalForm()));
        }
    };
    public static final Image WALLTILE =
            new Image(Main.class.getResource("../gameobjects/graphics/sprites/spr_dungeon_wall.png").toExternalForm());
    public static final Image EXITTILE =
            new Image(Main.class.getResource("../gameobjects/graphics/sprites/spr_dungeon_exit.png").toExternalForm());
    public static final Image FLOORTILE =
            new Image(Main.class.getResource("../gameobjects/graphics/sprites/spr_dungeon_tile.png").toExternalForm());
    private Player player;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainWindow = primaryStage;
        mainWindow.setTitle("Dungeon Crawler");

        goWelcome();
    }

    /**
     * Sets the stage to welcome scene
     */
    private void goWelcome() {
        //Creates the welcome screen
        WelcomeScreen welcome = new WelcomeScreen();

        BorderPane welcomeLayout = welcome.welcomeLayout();
        Scene scene = new Scene(welcomeLayout, GAME_WIDTH, GAME_HEIGHT);
        scene.getStylesheets().add("stylesheet.css");
        mainWindow.setScene(scene);

        mainWindow.show();

        //Sets button to move to config scene from welcome
        welcome.getStartButton().setOnAction(e -> goConfigScreen());
    }

    /**
     * Sets the stage to configuration scene
     */
    private void goConfigScreen() {
        //Creates the configuration screen and layout
        ConfigurationScreen configScene = new ConfigurationScreen(GAME_WIDTH, GAME_HEIGHT);

        //display scene
        Scene scene = configScene.getScene();
        mainWindow.setScene(scene);
        mainWindow.show();

        //Button for moving
        Button goRoom = configScene.getGoRoom();
        Button goBack = configScene.getGoBack();

        goBack.setOnAction(e -> {
            goWelcome();
        });

        //Button action for moving to Room
        goRoom.setOnAction(event -> {
            //initialize player
            firstRoom = new Room(20, 20);
            player = configScene.createChar(firstRoom);
            if (player != null && !player.isLegal()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if (player.getName().equals("")) {
                    alert.setContentText("please enter a name");
                } else if (player.getName().trim().equals("")) {
                    alert.setContentText("invalid name");
                } else if (player.getDifficulty() == -1) {
                    alert.setContentText("please select difficulty");
                }
                alert.showAndWait();
            } else {
                goToRoom(player);
            }
        });
    }

    /**
     * Sets the stage to room
     *
     * @param player character that user will be controlling in the game
     */
    private void goToRoom(Player player) {
        mainWindow.setScene(new Scene(new Pane(),
                GAME_WIDTH, GAME_HEIGHT, false, SceneAntialiasing.DISABLED));
        this.r = new GameStage(player, firstRoom);
        try {
            r.start(mainWindow);
        } catch (Exception e) {

        }
        Button exit = r.getExitButton();
        exit.setOnAction(event -> {
            goCongrat();
        });

        Button restart = r.getRestartButton();
        restart.setOnAction(event -> {
            r = null;
            goConfigScreen();
        });
    }


    private void goCongrat() {
        CongratScreen welcome = new CongratScreen();

        BorderPane finishLayout = welcome.finishLayout();
        Scene scene = new Scene(finishLayout, GAME_WIDTH, GAME_HEIGHT);
        scene.getStylesheets().add("stylesheet.css");
        mainWindow.setScene(scene);

        mainWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Returns player. (for testing purposes only)
     * @return player
     */
    public Player getPlayer() {
        return player;
    }
}