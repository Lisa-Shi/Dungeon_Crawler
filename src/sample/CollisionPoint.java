package sample;

public class CollisionPoint {
    // Variables
    private Vector2D position;
    private Vector2D onEdge;

    // Constructors
    public CollisionPoint(Vector2D position, Vector2D onEdge) {
        this.position = position;
        this.onEdge = onEdge;
    }

    // Methods
    public Vector2D getNormalTowardPoint(Vector2D towardPoint) {
        Vector2D normal1 = new Vector2D(-onEdge.getY(), onEdge.getX()).norm();
        Vector2D normal2 = normal1.opposite();

        if (position.add(normal1).distanceSquared(towardPoint) < position.add(normal2).distanceSquared(towardPoint)) {
            return normal1;
        } else {
            return normal2;
        }
    }

    // Getters
    public Vector2D getPosition() {
        return position;
    }
    public Vector2D getOnEdge() {
        return onEdge;
    }
}
