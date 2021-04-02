/**
 * subclass of physicsController
 * as named, the relative gameobjects.physics to another object
 */
package gameobjects.physics;

public class PhysicsControllerRelative extends PhysicsController {
    // Variables
    private PhysicsController relativeTo;

    // Constructors

    /**
     * constructor
     * @param initialX initial X position
     * @param initialY initial Y position
     * @param relativeTo the gameobjects.physics of the object
     *                   that this gameobjects.physics is calculated with respect to
     */
    public PhysicsControllerRelative(
            double initialX, double initialY, PhysicsController relativeTo) {
        super(initialX, initialY);
        this.relativeTo = relativeTo;
    }

    // Methods
    @Override
    public void update() {
        setAcceleration(getAcceleration().add(relativeTo.getAcceleration()));
        setVelocity(getVelocity().add(relativeTo.getVelocity()));
        setPosition(getPosition().add(relativeTo.getPosition()));
        super.update();
    }

    public Vector2D getAbsolutePosition() {
        return getPosition().add(relativeTo.getAbsolutePosition());
    }

    public Vector2D getAbsoluteVelocity() {
        return getVelocity().add(relativeTo.getAbsoluteVelocity());
    }

    public Vector2D getAbsoluteAcceleration() {
        return getAcceleration().add(relativeTo.getAbsoluteAcceleration());
    }
}
