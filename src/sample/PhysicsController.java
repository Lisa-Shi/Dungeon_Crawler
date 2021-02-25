package sample;

public class PhysicsController {
    // Variables
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration;
    private Vector2D impulse;

    // Constructors
    public PhysicsController(int initialX, int initialY) {
        this.position = new Vector2D(initialX, initialY);
        this.velocity = new Vector2D(0, 0);
        this.acceleration = new Vector2D(0, 0);
        this.impulse = new Vector2D(0, 0);
    }

    // Update
    public void update() {
        update(Main.DEFAULT_FRICTIONAL_FORCE);
    }
    public void update(double frictionalForceMagnitude) {
        position = position.add(velocity);

        Vector2D totalAcceleration = acceleration.add(impulse);

        Vector2D frictionalForce = velocity.norm().opposite().multiply(frictionalForceMagnitude);

        if (velocity.len() > 10) {
            velocity = velocity.relen(10);
        }

        if (velocity.len() < frictionalForce.len()) {
            velocity = new Vector2D(0, 0);
        } else {
            velocity = velocity.add(totalAcceleration.add(frictionalForce));
        }

        impulse = new Vector2D(0, 0);
    }

    // Push Methods
    public void push(Vector2D force) {
        impulse.setX(impulse.getX() + force.getX());
        impulse.setY(impulse.getY() + force.getY());
    }

    public void pushLeft() {
        pushLeft(Main.DEFAULT_FORCE);
    }
    public void pushRight() {
        pushRight(Main.DEFAULT_FORCE);
    }
    public void pushUp() {
        pushUp(Main.DEFAULT_FORCE);
    }
    public void pushDown() {
        pushDown(Main.DEFAULT_FORCE);
    }

    public void pushLeft(double force) {
        push(new Vector2D(-1 * force, 0));
    }
    public void pushRight(double force) {
        push(new Vector2D(1 * force, 0));
    }
    public void pushUp(double force) {
        push(new Vector2D(0, -1 * force));
    }
    public void pushDown(double force) {
        push(new Vector2D(0, 1 * force));
    }

    // Getters
    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public Vector2D getAcceleration() {
        return acceleration;
    }
}
