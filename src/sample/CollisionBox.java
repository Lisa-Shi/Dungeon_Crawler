package sample;

public abstract class CollisionBox {
    // Variables
    private PhysicsController physics;

    // Constructors
    public CollisionBox(PhysicsController physics) {
        this.physics = physics;
    }

    // Misc.
    public abstract boolean containsPoint(Vector2D point);
    public abstract boolean collidedWith(CollisionBox other);

    // Getters
    public PhysicsController getPhysics() {
        return physics;
    }
}
