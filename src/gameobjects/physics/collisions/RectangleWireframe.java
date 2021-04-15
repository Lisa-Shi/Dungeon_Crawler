/**
 * a type of wireFrame for the collisionbox
 * it is a rectangle shape wireframe
 */
package gameobjects.physics.collisions;

import gameobjects.physics.Vector2D;

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
