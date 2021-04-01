/**camera is for following object so it is inside the screen
 *
 */
package sample;

public class Camera implements Physical {
    // Variables
    private PhysicsController physics;
    private Vector2D offset;
    private Physical following;

    // Constructors
    /**
     * Constructs the camera which follows an object
     * (typically the player).
     *
     * @param offsetX x-offset of camera (where followed object is centered)
     * @param offsetY y-offset of camera (where followed object is centered)
     * @param following physics-based object to follow
     */
    public Camera(int offsetX, int offsetY, Physical following) {
        this.physics = new PhysicsController(following.getPhysics().getPosition().getX(),
                following.getPhysics().getPosition().getY());

        this.offset = new Vector2D(offsetX, offsetY);
        this.following = following;
    }

    // Methods
    /*

     */
    /**
     * Update the camera's sprite position and physics.
     * @param camera camera that focuses on object
     */
    public void update(Camera camera) {
        Vector2D camPosition = physics.getPosition();
        Vector2D followingPosition = following.getPhysics().getPosition();

        Vector2D toFollowingVector = followingPosition.subtract(camPosition);

        physics.setVelocity(
                new Vector2D((toFollowingVector.getX()) / Main.DEFAULT_CAMERA_SLOWDOWN_FACTOR,
                            (toFollowingVector.getY()) / Main.DEFAULT_CAMERA_SLOWDOWN_FACTOR));

        physics.update();
    }

    /**
     * Gets x-offset of camera
     *
     * @return x-offset
     */
    public double getOffsetX() {
        return offset.getX();
    }

    /**
     * Gets y-offset of camera
     *
     * @return y-offset
     */
    public double getOffsetY() {
        return offset.getY();
    }

    /**
     * Gets camera physics information
     *
     * @return physics information
     */
    @Override
    public PhysicsController getPhysics() {
        return physics;
    }
}
