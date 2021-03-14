package sample;

import java.util.ArrayList;

public class CollisionPoint {
    // Variables
    private Vector2D position;
    private ArrayList<Vector2D> onEdges;

    // Constructors
    public CollisionPoint(Vector2D position, Vector2D onEdge) {
        this(position, onEdge, null);
    }
    public CollisionPoint(Vector2D position, Vector2D onEdge, Vector2D onEdge2) {
        this.position = position;
        this.onEdges = new ArrayList<>();
        this.onEdges.add(onEdge);

        if (onEdge2 != null) {
            this.onEdges.add(onEdge2);
        }
    }

    // Methods
    public Vector2D getNormalTowardPoint(Vector2D towardPoint) {
        Vector2D normal1 = new Vector2D(0, 0);
        Vector2D normal2 = new Vector2D(0, 0);

        for (Vector2D onEdge : onEdges) {
            Vector2D edgeNormal = (new Vector2D(-onEdge.getY(), onEdge.getX())).norm();
            normal1 = normal1.add(edgeNormal);
            normal2 = normal2.add(edgeNormal.opposite());
        }

        if (position.add(normal1).distanceSquared(towardPoint)
                < position.add(normal2).distanceSquared(towardPoint)) {
            return normal1.norm();
        } else {
            return normal2.norm();
        }
    }

    // Getters
    public Vector2D getPosition() {
        return position;
    }
    public ArrayList<Vector2D> getOnEdges() {
        return onEdges;
    }
}
