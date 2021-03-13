package sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import java.util.LinkedList;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Room implements Physical {
    // Variables
    private int roomId;
    private static int id = 0;
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
        roomId = id++;
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
    public void generateExits(int numberOfExit) {
        LinkedList<ExitTile> availableExits = new LinkedList<>();
        availableExits.add(new ExitTile(this, (int) (Math.random() * (width - 2) + 1),
                0, new Room(this.width, this.height)));
        availableExits.add(new ExitTile(this, (int) 0,
                (int) (Math.random() * (height - 2) + 1), new Room(this.width, this.height)));
        availableExits.add(new ExitTile(this, (int) width - 1,
                (int) (Math.random() * (height - 2) + 1), new Room(this.width, this.height)));
        availableExits.add(new ExitTile(this, (int) (Math.random() * (width - 2) + 1),
                height - 1, new Room(this.width, this.height)));
        Random ran = new Random();
        if( exits.size() != 0){
            //if exit to the previous room is in the list
            //remove the choice that is on the same side of the room from the list
            //see equals in ExitTile.java
            availableExits.remove(exits.get(0));
        }
        for( int i = 0; i < numberOfExit; i++){
            int index = ran.nextInt(availableExits.size());
            ExitTile exit = availableExits.remove(index);
            add(exit);
        }
    }
    public void generateExits(ExitTile fromExit){
        Room previousRoom = fromExit.getInRoom();
        int preWidth = previousRoom.getWidth()-1;
        int preHeight = previousRoom.getHeight()-1;
        int x = fromExit.getExitX();
        int y = fromExit.getExitY();
        ExitTile corresponding = null;
        if( x == 0 || x == preWidth){
            corresponding = new ExitTile(this, preWidth - x, y, previousRoom);
        }else if( y == 0 || y == preHeight){
            corresponding = new ExitTile(this, x, preHeight - y, previousRoom);
        }
        add(corresponding);
        int numOfExit;
        do{
            numOfExit = (int)(Math.random()*3);
        } while( id < 7 && numOfExit == 0);
        generateExits(numOfExit);
    }
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

    public int getRoomId() {
        return roomId;
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

    public LinkedList<ExitTile> getExits() {
        return exits;
    }

    /**
     * @return physics of the room (important
     * for smooth camera physics)
     */
    @Override
    public PhysicsController getPhysics() {
        return physics;
    }

    @Override
    public boolean equals(Object other){
        return other instanceof Room && ((Room)other).getRoomId() == this.roomId;
    }
}
