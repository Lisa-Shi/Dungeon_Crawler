package sample;

public class WallTile extends Tile implements Collideable {
    // Variables
    private CollisionBox collisionBox;
    private int initialX;
    private int initialY;
    private Room inRoom;
    // Constructors
    public WallTile(Room inRoom, int initialX, int initialY) {
        super(inRoom, initialX, initialY, "spr_dungeon_wall.png");
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

    public int getInitialY() {
        return initialY;
    }
    @Override
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    @Override
    public boolean collideableEqual(Object other) {
        if( other instanceof Collideable){
            if (((Collideable) other).getCollisionBox().equals(this.collisionBox)){
                return true;
            }
        }
        return false;
    }
}