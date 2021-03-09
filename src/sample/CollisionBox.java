package sample;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public abstract class CollisionBox {
    // Variables
    private PhysicsController physics;
    private ArrayList<Vector2D> vertices;
    private ArrayList<Vector2D> edges;
    private ArrayList<CollisionPoint> collisionPoints;
    private Vector2D center;

    // Constructors
    public CollisionBox(PhysicsController physics) {
        this.physics = physics;
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.collisionPoints = new ArrayList<>();
        this.center = new Vector2D(0, 0);
    }

    // Misc.
    public void generateBox() {
        // Generate the center of the box
        int sumX = 0;
        int sumY = 0;
        for (int i = 0; i < vertices.size(); i++) {
            sumX += vertices.get(i).getX();
            sumY += vertices.get(i).getY();
        }
        center.setX(sumX / vertices.size());
        center.setY(sumY / vertices.size());
        physics.setPosition(center);

        // Generate the edges of the box
        for (int i = 0; i < vertices.size(); i++) {
            // Find the bordering vertex to draw an edge to
            int borderingVertexIndex = i + 1;
            if (i + 1 == vertices.size()) {
                borderingVertexIndex = 0;
            }
            Vector2D borderingVertex = vertices.get(borderingVertexIndex);

            Vector2D edge = borderingVertex.subtract(vertices.get(i));
            edges.add(edge);
        }

        // Generate the collision points
        for (int i = 0; i < edges.size(); i++) {
            // Find number of collision points to generate on this edge
            Vector2D targetEdge = edges.get(i);
            double edgeLength = targetEdge.len();

            int numCollisionPoints = (int) (edgeLength / Main.DEFAULT_COLLISION_PRECISION);
            double distBetweenCollisionPoints = edgeLength / (numCollisionPoints + 1);

            Vector2D targetEdgeNorm = targetEdge.norm();
            for (int j = 1; j <= numCollisionPoints; j++) {
                Vector2D pointPos = vertices.get(i).add(targetEdgeNorm.multiply(distBetweenCollisionPoints * j));
                collisionPoints.add(new CollisionPoint(pointPos, targetEdge));
            }
        }

    }
    public abstract boolean containsPoint(Vector2D point);

    public boolean collidedWith(CollisionBox other) {
        for (CollisionPoint cp : collisionPoints) {
            if (other.containsPoint(physics.getPosition().add(cp.getPosition()))) {
                return true;
            }
        }
        return false;
    }
    public Vector2D calculateCollisionVector(CollisionBox other) {
        Vector2D collisionVector = new Vector2D(0, 0);

        for (CollisionPoint cp : collisionPoints) {
            if (other.containsPoint(physics.getPosition().add(cp.getPosition()))) {
                collisionVector = collisionVector.add(cp.getNormalTowardPoint(center));
            }
        }

        return collisionVector.norm();
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
    public PhysicsController getPhysics() {
        return physics;
    }

    // Setters
    protected void addVertex(double x, double y) {
        addVertex(new Vector2D(x, y));
    }
    protected void addVertex(Vector2D vertex) {
        this.vertices.add(vertex);
    }
}
