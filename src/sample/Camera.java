package sample;

public class Camera implements Physical {
    // Variables
    private PhysicsController physics;
    private Vector2D offset;
    private Physical following;

    // Constructors
    public Camera(int offsetX, int offsetY, Physical following) {
        this.physics = new PhysicsController(following.getPhysics().getPosition().getX(),
                following.getPhysics().getPosition().getY());

        this.offset = new Vector2D(offsetX, offsetY);
        this.following = following;
    }

    // Methods
    public void update() {
        Vector2D camPosition = physics.getPosition();
        Vector2D followingPosition = following.getPhysics().getPosition();

        Vector2D toFollowingVector = followingPosition.subtract(camPosition);

        physics.setVelocity(new Vector2D((toFollowingVector.getX()) / Main.DEFAULT_CAMERA_SLOWDOWN_FACTOR,
                            (toFollowingVector.getY()) / Main.DEFAULT_CAMERA_SLOWDOWN_FACTOR));

        physics.update();
    }

    public double getOffsetX() {
        return offset.getX();
    }

    public double getOffsetY() {
        return offset.getY();
    }

    @Override
    public PhysicsController getPhysics() {
        return physics;
    }
}
