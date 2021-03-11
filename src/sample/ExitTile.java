package sample;

public class ExitTile extends Tile implements Collideable {
    // Variables
    private Room linkedRoom;
    private CollisionBox collisionBox;

    // Constructors
    public ExitTile(Room inRoom, double initialX, double initialY, Room linkedRoom) {
        super(inRoom, initialX, initialY, "spr_dungeon_exit.png");
        this.linkedRoom = linkedRoom;
        this.collisionBox = new CollisionBox(getPhysics(), new RectangleWireframe(Main.TILE_WIDTH, Main.TILE_HEIGHT), false);
        this.collisionBox.generate();
    }

    // Getters
    public Room getLinkedRoom() {
        return linkedRoom;
    }

    @Override
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }
}
