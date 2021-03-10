package sample;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public abstract class CollisionBox {
    // Variables
    private PhysicsControllerRelative physics;
    private ArrayList<Vector2D> vertices;

    // Constructors
    public CollisionBox(PhysicsController physics) {
        this.physics = new PhysicsControllerRelative(physics.getPosition().getX(), physics.getPosition().getY(), physics);
        this.vertices = new ArrayList<>();
    }

    // https://stackoverflow.com/questions/1119627/how-to-test-if-a-point-is-inside-of-a-convex-polygon-in-2d-integer-coordinates
    public boolean containsPoint(Vector2D absolutePoint) {
        Vector2D relativePoint = absolutePoint.subtract(getPhysics().getAbsolutePosition());

        int intersections = 0;
        int pos = 0;
        int neg = 0;

        for (int i = 0; i < vertices.size(); i++) {
            int indexOfNextVertex = i + 1;
            if (indexOfNextVertex >= vertices.size()) {
                indexOfNextVertex = 0;
            }

            double x1 = vertices.get(i).getX();
            double y1 = vertices.get(i).getY();
            double x2 = vertices.get(indexOfNextVertex).getX();
            double y2 = vertices.get(indexOfNextVertex).getY();

            double x = relativePoint.getX();
            double y = relativePoint.getY();

            double cross = (x - x1) * (y2-y1) - (y - y1) * (x2 - x1);

            if (cross > 0) {
                pos++;
            }

            if (cross < 0) {
                neg++;
            }

            if (pos > 0 && neg > 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        String verticesString = "";
        for (Vector2D vertex : vertices) {
            verticesString = verticesString.concat(vertex + ", ");
        }
        return "Collision box with vertices: " + verticesString.substring(0, verticesString.length() - 2);
    }

    // Getters
    public PhysicsControllerRelative getPhysics() {
        return physics;
    }
    protected ArrayList<Vector2D> getVertices() {
        return vertices;
    }

    // Setters
    protected void addVertex(double x, double y) {
        addVertex(new Vector2D(x, y));
    }
    protected void addVertex(Vector2D vertex) {
        this.vertices.add(vertex);
    }
}
