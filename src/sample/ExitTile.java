package sample;

import javafx.scene.image.Image;

public class ExitTile extends Tile implements Collideable {
    // Variables
    private Room linkedRoom;
    private CollisionBox collisionBox;

    // Constructors
    public ExitTile(Room inRoom, double initialX, double initialY, Room linkedRoom) {
        super(inRoom, initialX, initialY, "spr_dungeon_exit.png");
        this.linkedRoom = linkedRoom;
        this.collisionBox = new CollisionBoxRectangle(getPhysics(), Main.TILE_WIDTH, Main.TILE_HEIGHT);
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
