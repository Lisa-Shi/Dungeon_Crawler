package sample;

public class CollisionBoxRectangle extends CollisionBox {
    // Variables
    private double width;
    private double height;

    // Constructors
    public CollisionBoxRectangle(PhysicsController physics, double width, double height) {
        super(physics);
        this.width = width;
        this.height = height;

        // Add vertices
        addVertex(getTopLeftLocation());
        addVertex(getTopRightLocation());
        addVertex(getBottomRightLocation());
        addVertex(getBottomLeftLocation());
        generateBox();
    }

    // Methods
    @Override
    public boolean containsPoint(Vector2D point) {
        Vector2D topLeftLoc = getTopLeftLocation();
        Vector2D bottomRightLoc = getBottomRightLocation();
        if (point.getX() >= topLeftLoc.getX() && point.getX() <= bottomRightLoc.getX()) {
            if (point.getY() >= topLeftLoc.getY() && point.getY() <= bottomRightLoc.getY()) {
                return true;
            }
        }

        return false;
    }

    // Getters
    // Assume origin is centered
    private Vector2D getCornerLocation(int cornerX, int cornerY) {
        double xPos = getPhysics().getPosition().getX() + (width / 2) * cornerX;
        double yPos = getPhysics().getPosition().getY() + (height / 2) * cornerY;

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
