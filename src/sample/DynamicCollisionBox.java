package sample;

import java.util.ArrayList;

public class DynamicCollisionBox extends CollisionBox {
    // Variables
    private ArrayList<CollisionPoint> collisionPoints;

    // Constructors
    public DynamicCollisionBox(PhysicsController physics, PolygonWireframe wireframe) {
        super(physics, wireframe);
        this.collisionPoints = new ArrayList<>();
    }

    // Misc.
    @Override
    public void generate() {
        super.generate();
        generateEdgeCollisionPoints();
    }
    private void generateEdgeCollisionPoints() {
        for (int i = 0; i < getWireframe().getEdges().size(); i++) {
            // Find number of collision points to generate on this edge
            Vector2D targetEdge = getWireframe().getEdges().get(i);
            double edgeLength = targetEdge.len();

            int numCollisionPoints = (int) (edgeLength / Main.DEFAULT_COLLISION_PRECISION);

            double distBetweenCollisionPoints = edgeLength / (numCollisionPoints + 1);

            Vector2D targetEdgeNorm = targetEdge.norm();
            for (int j = 1; j <= numCollisionPoints; j++) {
                Vector2D pointPos = getWireframe().getVertices().get(i).add(targetEdgeNorm.multiply(distBetweenCollisionPoints * j));
                collisionPoints.add(new CollisionPoint(pointPos, targetEdge));
            }
        }
    }
    private void generateVertexCollisionPoints() {
        for (int i = 0; i < getWireframe().getVertices().size(); i++) {
            Vector2D pointPos = getWireframe().getVertices().get(i);
            int borderingVertexIndex = i - 1;
            if (i - 1 == -1) {
                borderingVertexIndex += getWireframe().getVertices().size();
            }

            collisionPoints.add(new CollisionPoint(pointPos, getWireframe().getEdges().get(i), getWireframe().getEdges().get(borderingVertexIndex)));
        }
    }

    public boolean collidedWith(CollisionBox other) {
        for (CollisionPoint cp : collisionPoints) {
            if (other.containsPoint(getPhysics().getAbsolutePosition().add(cp.getPosition()))) {
                return true;
            }
        }
        return false;
    }
    public Vector2D calculateCollisionVector(CollisionBox other) {
        Vector2D collisionVectorVertices = new Vector2D(0, 0);
        Vector2D collisionVectorEdges = new Vector2D(0, 0);
        boolean foundEdge = false;

        for (CollisionPoint cp : collisionPoints) {
            if (other.containsPoint(getPhysics().getAbsolutePosition().add(cp.getPosition()))) {
                if (cp.getOnEdges().size() == 1) {
                    foundEdge = true;
                    collisionVectorEdges = collisionVectorEdges.add(cp.getNormalTowardPoint(getWireframe().getCenter()));
                }
                if (!foundEdge) {
                    collisionVectorVertices = collisionVectorVertices.add(cp.getNormalTowardPoint(getWireframe().getCenter()));
                }
            }
        }

        if (foundEdge) {
            return collisionVectorEdges.norm();
        } else {
            return collisionVectorVertices.norm();
        }
    }
}
