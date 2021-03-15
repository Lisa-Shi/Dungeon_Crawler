package sample;

public class CollisionBox {
    // Variables
    private PhysicsControllerRelative physics;
    private PolygonWireframe wireframe;
    private boolean solid;

    // Constructors
    public CollisionBox(PhysicsController physics, PolygonWireframe wireframe) {
        this(physics, wireframe, true);
    }
    public CollisionBox(PhysicsController physics, PolygonWireframe wireframe, boolean solid) {
        this.physics = new PhysicsControllerRelative(
                physics.getPosition().getX(), physics.getPosition().getY(), physics);
        this.wireframe = wireframe;
        this.solid = solid;
    }

    public void generate() {
        getWireframe().generate();
        getPhysics().setPosition(getWireframe().getCenter());
    }

    public boolean containsPoint(Vector2D absolutePoint) {
        Vector2D relativePoint = absolutePoint.subtract(getPhysics().getAbsolutePosition());
        return wireframe.containsPoint(relativePoint);
    }

    @Override
    public String toString() {
        String verticesString = "";
        for (Vector2D vertex : wireframe.getVertices()) {
            verticesString = verticesString.concat(vertex + ", ");
        }
        return "Collision box with vertices: "
                + verticesString.substring(0, verticesString.length() - 2);
    }

    // Getters
    public PhysicsControllerRelative getPhysics() {
        return physics;
    }
    public PolygonWireframe getWireframe() {
        return wireframe;
    }
    public boolean isSolid() {
        return solid;
    }
}
