package sample;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Room implements Physical {
    // Variables
    private int width;
    private int height;
    private Sprite[][] sprites;
    private Exit[] exits;
    private PhysicsController physics;

    // Constructors
    /**
     * Constructs a dungeon room
     *
     * @param width width of the room in tiles
     * @param height height of the room in tiles
     * @param exits List of vectors, which represent locations of individual exits
     */
    public Room(int width, int height, Exit[] exits) {
        this.width = width;
        this.height = height;
        this.exits = exits;

        // Sprites array so sprites can be moved around
        this.sprites = new Sprite[width][height];

        // Physics so the camera works properly
        this.physics = new PhysicsController(0, 0);
    }

    // Methods
    /**
     * Places the sprites that will be manipulated into the inputted pane
     * for the first time (do not call more than once if pane, etc. is not
     * reset)
     *
     * @param pane Pane to draw the room within
     */
    public void draw(Pane pane) {
        for (int r = 0; r < width; r++) {
            for (int c = 0; c < height; c++) {
                int tileWidth = Main.TILE_WIDTH;
                int tileHeight = Main.TILE_HEIGHT;

                String spriteToDraw = "spr_dungeon_tile.png";

                for (int exitIndex = 0; exitIndex < exits.length; exitIndex++) {
                    Vector2D targetExit = exits[exitIndex].getLocation();

                    // If found exit where player is, replace sprite
                    if (targetExit.getX() == r && targetExit.getY() == c) {
                        spriteToDraw = "spr_dungeon_exit.png";
                    }
                }

                Sprite s = new Sprite(r * tileWidth, c * tileHeight, tileWidth, tileHeight,
                        new Image(getClass().getResource(spriteToDraw).toExternalForm()));

                pane.getChildren().add(s);

                sprites[r][c] = s;
            }
        }
    }
    /**
     * Updates physics of the room relative to its old physics and the
     * camera physics
     *
     * @param camera Camera to extract physics information from
     */
    public void update(Camera camera) {
        // Room moves with the camera
        physics.update();

        for (int r = 0; r < sprites.length; r++) {
            for (int c = 0; c < sprites[r].length; c++) {
                Sprite s = sprites[r][c];
                s.setTranslateX(physics.getPosition().getX() + Main.TILE_WIDTH * r
                        - camera.getPhysics().getPosition().getX() + camera.getOffsetX());
                s.setTranslateY(physics.getPosition().getY() + Main.TILE_HEIGHT * c
                        - camera.getPhysics().getPosition().getY() + camera.getOffsetY());
            }
        }
    }



    // Getters
    /**
     * Gets a specific exit location
     *
     * @param index index to get exit location from
     * @return exit location at specified index
     */
    public Exit getExit(int index) {
        return exits[index];
    }
    /**
     * NOTE: Exit locations are represented by a list of vectors.
     * @return list of vectors representing exit locations
     */
    public Exit[] getExits() {
        return exits;
    }
    /**
     * @return room width
     */
    public int getWidth() {
        return width;
    }
    /**
     * @return room height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return physics of the room (important
     * for smooth camera physics)
     */
    @Override
    public PhysicsController getPhysics() {
        return physics;
    }
}
