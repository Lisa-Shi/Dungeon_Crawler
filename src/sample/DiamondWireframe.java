package sample;

public class DiamondWireframe extends PolygonWireframe {
    // Constructors
    public DiamondWireframe(double sideLength) {
        // Add vertices
        int diagLength = (int) (sideLength / 2D * Math.sqrt(2) / 2D);

        addVertex(new Vector2D(-diagLength, 0));
        addVertex(new Vector2D(0, diagLength));
        addVertex(new Vector2D(diagLength, 0));
        addVertex(new Vector2D(0, -diagLength));
    }
}
