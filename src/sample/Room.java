package sample;

import javafx.scene.layout.Pane;

import java.util.LinkedList;

public class Room implements Physical {
    // Variables
    private int width;
    private int height;
    private LinkedList<Physical> physicals;
    private LinkedList<Collideable> collideables;
    private LinkedList<Drawable> drawables;
    private LinkedList<ExitTile> exits;
    private PhysicsController physics;

    // Constructors
    /**
     * Constructs a dungeon room
     *
     * @param width width of the room in tiles
     * @param height height of the room in tiles
     */
    public Room(int width, int height) {
        this.width = width;
        this.height = height;

        // Physics so the camera works properly
        this.physics = new PhysicsController(0, 0);

        this.physicals = new LinkedList<>();
        this.collideables = new LinkedList<>();
        this.exits = new LinkedList<>();
        this.drawables = new LinkedList<>();
    }

    // Methods
    /**
     * Places the sprites that will be manipulated into the inputted pane
     * for the first time (do not call more than once if pane, etc. is not
     * reset)
     *
     * @param pane Pane to draw the room within
     */
    public void finalize(Pane pane) {
        addFloorTiles();
        addSurroundingWalls();

        addAllSprites(pane);
    }
    private void addFloorTiles() {
        for (int r = 0; r < width; r++) {
            for (int c = 0; c < height; c++) {
                Tile tile = new FloorTile(this, r, c);
                add(tile);
            }
        }
    }
    private void addSurroundingWalls() {
        // Add walls around the dungeon
        for (int r = 0; r < width; r++) {
            Tile topWall = new WallTile(this, r, -1);
            Tile bottomWall = new WallTile(this, r, height);
            add(topWall);
            add(bottomWall);
        }
        for (int c = 0; c < height; c++) {
            Tile leftWall = new WallTile(this, -1, c);
            Tile rightWall = new WallTile(this, width, c);
            add(leftWall);
            add(rightWall);
        }
    }
    private void addAllSprites(Pane pane) {
        for (Drawable drawable : drawables) {
            pane.getChildren().add(drawable.getSprite());
        }
    }
    /**
     * Updates physics of the room relative to its old physics and the
     * camera physics
     *
     * @param camera Camera to extract physics information from
     */
    @Override
    public void update(Camera camera) {
        // Room moves with the camera
        physics.update();

        for (Physical physical : physicals) {
            physical.update(camera);
        }
    }



    // Getters
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

    public LinkedList<Physical> getPhysicals() {
        return physicals;
    }

    public LinkedList<Collideable> getCollideables() {
        return collideables;
    }

    // Setters
    public void add(GameObject obj) {
        if (obj instanceof Physical) {
            // so far, all game objects are physical
            physicals.add(0, (Physical) obj);
        }
        if (obj instanceof Collideable) {
            collideables.add(0, (Collideable) obj);
        }
        if (obj instanceof ExitTile) {
            exits.add(0, (ExitTile) obj);
        }
        if (obj instanceof Drawable) {
            drawables.add(0, (Drawable) obj);
        }
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
