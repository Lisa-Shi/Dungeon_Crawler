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
        addVertex(new Vector2D(-width/2, -height/2));
        addVertex(new Vector2D(width/2, -height/2));
        addVertex(new Vector2D(width/2, height/2));
        addVertex(new Vector2D(-width/2, height/2));

        generateBox();
    }
}
