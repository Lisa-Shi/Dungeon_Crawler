package sample;

public class CollisionBoxRectangle extends CollisionBox {
    // Variables
    private PhysicsController physics;
    private double width;
    private double height;

    // Constructors
    public CollisionBoxRectangle(PhysicsController physics, double width, double height) {
        super(physics);
        this.width = width;
        this.height = height;
    }

    // Methods
    @Override
    public boolean containsPoint(Vector2D point) {
        Vector2D topLeftLoc = getTopLeftLocation();
        Vector2D bottomRightLoc = getBottomRightLocation();

        if (point.getX() >= topLeftLoc.getX() && point.getX() <= bottomRightLoc.getY()) {
            if (point.getY() >= topLeftLoc.getY() && point.getY() <= bottomRightLoc.getY()) {
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean collidedWith(CollisionBox other) {
        return (other.containsPoint(getTopLeftLocation()) || other.containsPoint(getTopRightLocation())
                || other.containsPoint(getBottomLeftLocation()) || other.containsPoint(getBottomRightLocation()));
    }

    // Getters
    // Assume origin is centered
    private Vector2D getCornerLocation(int cornerX, int cornerY) {
        double xPos = physics.getPosition().getX() + (width / 2) * cornerX;
        double yPos = physics.getPosition().getY() + (height / 2) * cornerY;

        return new Vector2D(xPos, yPos);
    }
    public Vector2D getTopLeftLocation() {
        return getCornerLocation(-1, -1);
    }
    public Vector2D getTopRightLocation() {
        return getCornerLocation(1, -1);
    }
    public Vector2D getBottomLeftLocation() {
        return getCornerLocation(-1, 1);
    }
    public Vector2D getBottomRightLocation() {
        return getCornerLocation(1, 1);
    }
}
