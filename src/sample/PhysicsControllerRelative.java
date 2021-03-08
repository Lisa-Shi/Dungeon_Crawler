package sample;

public class PhysicsControllerRelative extends PhysicsController {
    // Variables
    private Physical relativeTo;

    // Constructors
    public PhysicsControllerRelative(double initialX, double initialY, Physical relativeTo) {
        super(initialX, initialY);
        this.relativeTo = relativeTo;
    }

    // Methods
    @Override
    public void update() {
        setAcceleration(getAcceleration().add(relativeTo.getPhysics().getAcceleration()));
        setVelocity(getVelocity().add(relativeTo.getPhysics().getVelocity()));
        setPosition(getPosition().add(relativeTo.getPhysics().getPosition()));
        super.update();
    }
}
