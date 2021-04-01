package gameobjects.physics.collisions;

import gameobjects.GameObject;
import screens.Main;
import gamemap.Room;
import gameobjects.graphics.functionality.SingularImageSheet;

public class Diamond extends GameObject implements Collideable {
    // Variables
    private CollisionBox collisionBox;

    public Diamond(Room room, double initialX, double initialY, double sideLength) {
        super(room, initialX, initialY, (sideLength * Math.sqrt(2) / 2) / 2,
                (sideLength * Math.sqrt(2) / 2) / 2, new SingularImageSheet(Main.FLOORTILE));
        this.collisionBox = new CollisionBox(getPhysics(), new DiamondWireframe(sideLength));
    }

    @Override
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }
}
