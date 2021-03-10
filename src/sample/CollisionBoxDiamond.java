package sample;

public class CollisionBoxDiamond extends CollisionBox {
    // TODO: Refactor

    // Constructors
    public CollisionBoxDiamond(PhysicsController physics, double sideLength) {
        super(physics);

        // Add vertices
        int diagLength = (int) (sideLength / 2D * Math.sqrt(2) / 2D);

        addVertex(new Vector2D(-diagLength, 0));
        addVertex(new Vector2D(0, diagLength));
        addVertex(new Vector2D(diagLength, 0));
        addVertex(new Vector2D(0, -diagLength));
    }
}
