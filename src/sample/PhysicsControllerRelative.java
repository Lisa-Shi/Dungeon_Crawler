package sample;

public class PhysicsControllerRelative extends PhysicsController {
    // Variables
    private PhysicsController relativeTo;

    // Constructors
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
        return getPosition().add(relativeTo.getPosition());
    }

    public Vector2D getAbsoluteVelocity() {
        return getVelocity().add(relativeTo.getVelocity());
    }

    public Vector2D getAbsoluteAcceleration() {
        return getAcceleration().add(relativeTo.getAcceleration());
    }
}
