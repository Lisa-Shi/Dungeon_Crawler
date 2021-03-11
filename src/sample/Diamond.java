package sample;

public class Diamond extends GameObject implements Collideable {
    // Variables
    private CollisionBox collisionBox;

    public Diamond(Room room, double initialX, double initialY, double sideLength) {
        super(room, initialX, initialY, (sideLength * Math.sqrt(2)/2)/2, (sideLength * Math.sqrt(2)/2)/2,"spr_dungeon_tile.png");
        this.collisionBox = new CollisionBox(getPhysics(), new DiamondWireframe(sideLength));
    }

    @Override
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }
}
