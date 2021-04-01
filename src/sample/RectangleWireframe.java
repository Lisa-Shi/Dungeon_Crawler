/**
 * a type of wireFrame for the collisionbox
 * it is a rectangle shape wireframe
 */
package sample;

public class RectangleWireframe extends PolygonWireframe {
    // Constructors
    public RectangleWireframe(double width, double height) {
        // Add vertices
        addVertex(new Vector2D(-width / 2, -height / 2));
        addVertex(new Vector2D(width / 2, -height / 2));
        addVertex(new Vector2D(width / 2, height  / 2));
        addVertex(new Vector2D(-width / 2, height / 2));
    }
}
