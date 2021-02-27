package sample;

public class PhysicsController {
    // Variables
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration;
    private Vector2D impulse;

    // Constructors
    public PhysicsController(double initialX, double initialY) {
        this.position = new Vector2D(initialX, initialY);
        this.velocity = new Vector2D(0, 0);
        this.acceleration = new Vector2D(0, 0);
        this.impulse = new Vector2D(0, 0);
    }

    // Update
    /**
     * Update physics information to make object exhibit movement
     * based on its old physics information
     * Uses default frictional force
     */
    public void update() {
        update(Main.DEFAULT_FRICTIONAL_FORCE);
    }
    /**
     * Update physics information to make object exhibit movement
     * based on its old physics information
     *
     * @param frictionalForceMagnitude Magnitude of the frictional force
     *                                 exerted on the object
     */
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
    /**
     * Apply a strong force to an object that lasts
     * for one physics update (referenced as an "impulse" here)
     *
     * @param force Force to apply
     */
    public void push(Vector2D force) {
        impulse.setX(impulse.getX() + force.getX());
        impulse.setY(impulse.getY() + force.getY());
    }

    /**
     * Apply impulse to the left
     * Force has default magnitude
     */
    public void pushLeft() {
        pushLeft(Main.DEFAULT_FORCE);
    }
    /**
     * Apply temporary force to the right
     * Force has default magnitude
     */
    public void pushRight() {
        pushRight(Main.DEFAULT_FORCE);
    }
    /**
     * Apply temporary force up
     * Force has default magnitude
     */
    public void pushUp() {
        pushUp(Main.DEFAULT_FORCE);
    }
    /**
     * Apply temporary force down
     * Force has default magnitude
     */
    public void pushDown() {
        pushDown(Main.DEFAULT_FORCE);
    }

    /**
     * Apply temporary force to the left
     *
     * @params force Magnitude of the force to apply
     */
    public void pushLeft(double force) {
        push(new Vector2D(-1 * force, 0));
    }
    /**
     * Apply temporary force to the right
     *
     * @params force Magnitude of the force to apply
     */
    public void pushRight(double force) {
        push(new Vector2D(1 * force, 0));
    }
    /**
     * Apply temporary force up
     *
     * @params force Magnitude of the force to apply
     */
    public void pushUp(double force) {
        push(new Vector2D(0, -1 * force));
    }
    /**
     * Apply temporary force down
     *
     * @params force Magnitude of the force to apply
     */
    public void pushDown(double force) {
        push(new Vector2D(0, 1 * force));
    }

    // Getters
    /**
     * @return object position
     */
    public Vector2D getPosition() {
        return position;
    }

    /**
     * @return object velocity
     */
    public Vector2D getVelocity() {
        return velocity;
    }

    /**
     * @return object acceleration
     */
    public Vector2D getAcceleration() {
        return acceleration;
    }

    // Setters
    /**
     * @param position new position of object
     */
    public void setPosition(Vector2D position) {
        this.position = position;
    }
    /**
     * @param velocity new velocity of object
     */
    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }
    /**
     * @param acceleration new acceleration of object
     */
    public void setAcceleration(Vector2D acceleration) {
        this.acceleration = acceleration;
    }
}
