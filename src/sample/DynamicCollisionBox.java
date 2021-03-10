package sample;

import java.util.ArrayList;

public class DynamicCollisionBox extends CollisionBox {
    // Variables
    private ArrayList<Vector2D> edges;
    private ArrayList<CollisionPoint> collisionPoints;
    private Vector2D center;

    // Constructors
    public DynamicCollisionBox(PhysicsController physics) {
        super(physics);
        this.edges = new ArrayList<>();
        this.collisionPoints = new ArrayList<>();
        this.center = new Vector2D(0, 0);
    }

    // Misc.
    public void generateBox() {
        // Generate the center of the box
        int sumX = 0;
        int sumY = 0;
        for (int i = 0; i < getVertices().size(); i++) {
            sumX += getVertices().get(i).getX();
            sumY += getVertices().get(i).getY();
        }
        center.setX(sumX / getVertices().size());
        center.setY(sumY / getVertices().size());
        getPhysics().setPosition(center);

        // Generate the edges of the box
        for (int i = 0; i < getVertices().size(); i++) {
            // Find the bordering vertex to draw an edge to
            int borderingVertexIndex = i + 1;
            if (i + 1 == getVertices().size()) {
                borderingVertexIndex = 0;
            }
            Vector2D borderingVertex = getVertices().get(borderingVertexIndex);

            Vector2D edge = borderingVertex.subtract(getVertices().get(i));
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
                Vector2D pointPos = getVertices().get(i).add(targetEdgeNorm.multiply(distBetweenCollisionPoints * j));
                collisionPoints.add(new CollisionPoint(pointPos, targetEdge));
            }
        }

        /*
        for (int i = 0; i < vertices.size(); i++) {
            // Vertex collision points
            Vector2D pointPos = vertices.get(i);
            int borderingVertexIndex = i - 1;
            if (i - 1 == -1) {
                borderingVertexIndex += vertices.size();
            }

            //collisionPoints.add(new CollisionPoint(pointPos, edges.get(i), edges.get(borderingVertexIndex)));
        }
        */

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
                    collisionVectorEdges = collisionVectorEdges.add(cp.getNormalTowardPoint(center));
                }
                if (!foundEdge) {
                    collisionVectorVertices = collisionVectorVertices.add(cp.getNormalTowardPoint(center));
                }
            }
        }

        if (foundEdge) {
            return collisionVectorEdges.norm();
        } else {
            return collisionVectorVertices.norm();
        }
    }

    // Methods
    public void generateDynamicBox() {
    }
}
