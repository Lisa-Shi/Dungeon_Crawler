package sample;

public class CollisionBoxRectangle extends DynamicCollisionBox {
    // TODO: Refactor

    // Constructors
    public CollisionBoxRectangle(PhysicsController physics, double width, double height) {
        super(physics);

        // Add vertices
        addVertex(new Vector2D(-width/2, -height/2));
        addVertex(new Vector2D(width/2, -height/2));
        addVertex(new Vector2D(width/2, height/2));
        addVertex(new Vector2D(-width/2, height/2));

        generateBox();
    }
}
