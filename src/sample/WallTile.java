package sample;

public class WallTile extends Tile implements Collideable {
    // Variables
    private CollisionBox collisionBox;

    // Constructors
    public WallTile(Room inRoom, double initialX, double initialY) {
        super(inRoom, initialX, initialY, "spr_dungeon_wall.png");
        this.collisionBox = new CollisionBox(getPhysics(), new RectangleWireframe(Main.TILE_WIDTH, Main.TILE_HEIGHT));
        this.collisionBox.generate();
    }

    @Override
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }
}
