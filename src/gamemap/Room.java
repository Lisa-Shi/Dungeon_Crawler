package gamemap;

import gameobjects.tiles.ExitTile;
import gameobjects.tiles.FloorTile;
import gameobjects.GameObject;
import gameobjects.tiles.Tile;
import gameobjects.tiles.WallTile;
import gameobjects.physics.Camera;
import gameobjects.physics.collisions.Collideable;
import gameobjects.physics.collisions.DynamicCollisionBox;
import javafx.scene.layout.Pane;
import gameobjects.monsters.BuzzMonster;
import gameobjects.monsters.MagicianMonster;
import gameobjects.monsters.Monster;
import gameobjects.monsters.SlimeMonster;
import gameobjects.physics.collisions.Physical;
import gameobjects.physics.PhysicsController;
import gameobjects.graphics.functionality.Drawable;
import gameobjects.HPBar;
import gameobjects.physics.Vector2D;

import java.util.LinkedList;
import java.util.*;

public class Room implements Physical {
    // Variables
    private int roomId;
    private static int id = 0;
    private int width;
    private int height;
    private String layout;
    private LinkedList<Physical> physicals = new LinkedList<>();
    private LinkedList<Collideable> collideables = new LinkedList<>();
    private LinkedList<Drawable> drawables = new LinkedList<>();
    private LinkedList<ExitTile> exits = new LinkedList<>();
    /**
     *gameobjects.monsters is empty when all gameobjects.monsters in this room die
     */
    private LinkedList<Monster> monsters = new LinkedList<>();
    private LinkedList<GameObject> toRemove = new LinkedList<>();
    private LinkedList<HPBar> healthbars = new LinkedList<>();
    private PhysicsController physics;
    public LinkedList<HPBar> getHealthbars() {
        return healthbars;
    }
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

        this.toRemove = new LinkedList<>();
    }
    public void restart() {
        clear();
        id = 0;
    }
    public Room(int width, int height, int inputId) {
        roomId = inputId;
        this.width = width;
        this.height = height;

        // Physics so the camera works properly
        this.physics = new PhysicsController(0, 0);

        this.toRemove = new LinkedList<>();
    }

    /**
     * add random amount (from 1 to 5) of gameobjects.monsters to the room.
     * this method will not check if there is already monster in the room
     */
    public void generateMonsters() {
        if (monsters.isEmpty()) {
            Monster monster = null;
            if (roomId == 999) {
                // Boss monster
                monster = new BuzzMonster(this, width / 2, height / 2);
                add(monster);
            } else if (roomId != 0) {
                Random ran = new Random();
                int numOfMon = ran.nextInt(5) + 1;
                for (int i = 1; i <= numOfMon; i++) {
                    int monsterX;
                    int monsterY;
                    do {
                        monsterX = ran.nextInt(width - 2) + 1;
                        monsterY = ran.nextInt(height - 2) + 1;
                        int monsterType = ran.nextInt(3);
                        if (monsterType == 0) {
                            monster = new SlimeMonster(this, monsterX, monsterY);
                        } else if (monsterType == 1) {
                            monster = new MagicianMonster(this, monsterX, monsterY);
                        } else {
                            monster = new BuzzMonster(this, monsterX, monsterY);
                        }
                    } while (findExistingCollideable(monster));
                    add(monster);
                }
            }
        }
    }

    /**
     * check if the passed monster collides with any collideable object in the room
     * @param monster target monster
     * @return true if monster collides with one collideable.
     */
    public boolean findExistingCollideable(Monster monster) {
        for (Collideable object: collideables) {
            if (((DynamicCollisionBox) monster.
                    getCollisionBox()).collidedWith(object.getCollisionBox())) {
                return true;
            }
        }
        return false;
    }

    /**
     * it will make sure there is only one exit on one side of the room
     * @param listOfExit list of rooms that are adjacent to this room
     */
    public void generateExits(List<Room> listOfExit) {
        if (exits.isEmpty() && listOfExit != null) {
            LinkedList<Vector2D> availableExits = new LinkedList<>();
            availableExits.add(
                    new Vector2D((int) (Math.random() * (width - 2) + 1), -1));
            availableExits.add(
                    new Vector2D((int) -1, (int) (Math.random() * (height - 2) + 1)));
            availableExits.add(
                    new Vector2D((int) width, (int) (Math.random() * (height - 2) + 1)));
            availableExits.add(
                    new Vector2D((int) (Math.random() * (width - 2) + 1), height));
            Random ran = new Random();
            for (Room room : listOfExit) {
                int index = ran.nextInt(availableExits.size());
                Vector2D vec = availableExits.remove(index);
                ExitTile exit = new ExitTile(this, (int) vec.getX(), (int) vec.getY(), room);
                for (ExitTile exitA : room.getExits()) {
                    if (vec.getY() == exitA.getExitY()) {
                        if (Math.abs(vec.getX() - exitA.getExitX()) < 5) {
                            exit = new ExitTile(this,
                                    (int) (width - vec.getX()), (int) vec.getY(), room);
                        }
                    }
                    if (vec.getX() == exitA.getExitX()) {
                        if (Math.abs(vec.getY() - exitA.getExitY()) < 5) {
                            exit = new ExitTile(this,
                                    (int) vec.getX(), (int) (height - vec.getY()), room);
                        }
                    }
                }
                add(exit);
            }
        }
    }
    /**
     * Places the sprites that will be manipulated into the inputted pane
     * for the first time (do not call more than once if pane, etc. is not
     * reset)
     *
     * @param pane Pane to draw the room within
     */
    public void finalize(Pane pane) {
        addRoomLayout();
        generateMonsters();
        addFloorTiles();
        addSurroundingWalls();
        addAllSprites(pane);
        for (Monster monster: monsters) {
            monster.addHPBar( this, pane);
        }
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
    public void addRoomLayout() {
        layout = RoomLayout.design(this);
    }
    private void addAllSprites(Pane pane) {
        for (Drawable drawable : drawables) {
            pane.getChildren().add(drawable.getGraphics().getSprite());
        }
    }
    /**
     * Updates gameobjects.physics of the room relative to its old gameobjects.physics and the
     * camera gameobjects.physics
     *
     * @param camera Camera to extract gameobjects.physics information from
     */
    @Override
    public void update(Camera camera) {
        // Room moves with the camera
        physics.update();

        for (Physical physical : physicals) {
            physical.update(camera);
        }

        flushToRemove();
    }

    public void clear() {
        this.physicals = new LinkedList<>();
        this.collideables = new LinkedList<>();
        this.exits = new LinkedList<>();
        this.drawables = new LinkedList<>();
        this.monsters = new LinkedList<>();
    }

    // Getters

    public int getRoomId() {
        return roomId;
    }

    public LinkedList<Monster> getMonsters() {
        return monsters;
    }

    public LinkedList<ExitTile> getExits() {
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

    public LinkedList<Physical> getPhysicals() {
        return physicals;
    }

    public LinkedList<Collideable> getCollideables() {
        return collideables;
    }

    public String getLayout() {
        return layout;
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
        if (obj instanceof Monster) {
            monsters.add(0, (Monster) obj);
        }
    }

    public void remove(GameObject obj) {
        toRemove.add(obj);
    }

    private void flushToRemove() {
        for (GameObject obj : toRemove) {
            if (obj instanceof Physical) {
                // so far, all game objects are physical
                physicals.remove((Physical) obj);
            }
            if (obj instanceof Collideable) {
                collideables.remove((Collideable) obj);
            }
            if (obj instanceof ExitTile) {
                exits.remove((ExitTile) obj);
            }
            if (obj instanceof Drawable) {
                drawables.remove((Drawable) obj);
            }
            if (obj instanceof Monster) {
                monsters.remove((Monster) obj);
            }
        }

        toRemove.clear();
    }

    /**
     * @return gameobjects.physics of the room (important
     * for smooth camera gameobjects.physics)
     */
    @Override
    public PhysicsController getPhysics() {
        return physics;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Room && ((Room) other).getRoomId() == this.roomId;
    }
    @Override
    public int hashCode() {
        return roomId;
    }

    public LinkedList<Drawable> getDrawables() {
        return drawables;
    }
}
