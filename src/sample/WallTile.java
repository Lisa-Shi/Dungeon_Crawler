package sample;

public class WallTile extends Tile implements Collideable {
    // Variables
    private CollisionBox collisionBox;
    private int initialX;
    private int initialY;
    private Room inRoom;
    // Constructors
    public WallTile(Room inRoom, int initialX, int initialY) {
        super(inRoom, initialX, initialY, Main.WALLTILE);
        this.inRoom = inRoom;
        this.initialX = initialX;
        this.initialY = initialY;
        this.collisionBox = new CollisionBox(getPhysics(),
                new RectangleWireframe(Main.TILE_WIDTH, Main.TILE_HEIGHT));
        this.collisionBox.generate();
    }

    public int getInitialX() {
        return initialX;
    }
    public Room getRoom() {
        return inRoom; }
    public int getInitialY() {
        return initialY;
    }
    @Override
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }
    public boolean equals(Object other) {
        if (other instanceof WallTile) {
            return ((WallTile) other).getRoom().equals(inRoom)
                    && ((WallTile) other).getInitialX() == initialX
                    && ((WallTile) other).getInitialY() == initialY;
        }
        return false;
    }
}