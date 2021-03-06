package gameobjects.physics.collisions;

import main.Main;
import gameobjects.physics.PhysicsController;
import gameobjects.physics.Vector2D;

import java.util.ArrayList;
import java.util.LinkedList;

public class DynamicCollisionBox extends CollisionBox {
    // Variables
    private ArrayList<CollisionPoint> collisionPoints;
    public static int numCollisionsConsidered = 0;

    // Constructors
    public DynamicCollisionBox(PhysicsController physics, PolygonWireframe wireframe) {
        this(physics, wireframe, true);
    }
    public DynamicCollisionBox(PhysicsController physics,
                               PolygonWireframe wireframe, boolean solid) {
        super(physics, wireframe);
        this.collisionPoints = new ArrayList<>();
        this.setSolid(solid);
    }

    // Misc.
    @Override
    public void generate() {
        super.generate();
        generateEdgeCollisionPoints();
        //generateVertexCollisionPoints();
    }
    private void generateEdgeCollisionPoints() {
        for (int i = 0; i < getWireframe().getEdges().size(); i++) {
            // Find number of collision points to generate on this edge
            Vector2D targetEdge = getWireframe().getEdges().get(i);
            double edgeLength = targetEdge.len();

            int numCollisionPoints = (int) (edgeLength / Main.DEFAULT_COLLISION_PRECISION) + 1;

            double distBetweenCollisionPoints = edgeLength / (numCollisionPoints + 1);

            Vector2D targetEdgeNorm = targetEdge.norm();
            for (int j = 1; j <= numCollisionPoints; j++) {
                Vector2D pointPos = getWireframe().getVertices().get(i).add(
                        targetEdgeNorm.multiply(distBetweenCollisionPoints * j));
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

            collisionPoints.add(new CollisionPoint(pointPos, getWireframe().getEdges().get(i),
                    getWireframe().getEdges().get(borderingVertexIndex)));
        }
    }

    public boolean collidedWith(CollisionBox other) {
        if (other == this) {
            return false;
        }

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
                    collisionVectorEdges =
                            collisionVectorEdges.add(
                                    cp.getNormalTowardPoint(getWireframe().getCenter()));
                }
                if (!foundEdge) {
                    collisionVectorVertices =
                            collisionVectorVertices.add(
                                    cp.getNormalTowardPoint(getWireframe().getCenter()));
                }
            }
        }

        if (foundEdge) {
            return collisionVectorEdges.norm();
        } else {
            return collisionVectorVertices.norm();
        }
    }

    public void raytraceCollision(PhysicsController physics, LinkedList<Collideable> collideables) {

        int backtracks = 0;
        boolean hasCollidedWithSolid;
        Vector2D backtrackVel = new Vector2D(0, 0);
        LinkedList<Passable> boundaries = new LinkedList<>();

        do {
            if (numCollisionsConsidered > 200) {
                break;
            } else {
                numCollisionsConsidered++;
            }

            hasCollidedWithSolid = false;
            // Test if there are any gameobjects.physics.collisions, and continue moving player back
            for (Collideable collideable : collideables) {
                boolean solid = collideable.getCollisionBox().isSolid();
                boolean collided = collidedWith(collideable.getCollisionBox());
                if (collided) {
                    if (solid) {
                        backtrackVel = backtrackVel.add(
                                calculateCollisionVector(
                                        collideable.getCollisionBox()).multiply(0.005D));
                        hasCollidedWithSolid = true;
                    }
                }

            }

            if (hasCollidedWithSolid) {
                // Move player back to test if safe from gameobjects.physics.collisions
                physics.setPosition(physics.getPosition().add(backtrackVel));
                backtracks++;
                boundaries.clear();
            } else {
                break;
            }

            if (backtrackVel.len() < 0.001D) {
                break;
            }

            if (backtracks >= 20) {
                break;
            }

        } while (hasCollidedWithSolid);

        if (backtracks >= 1) {
            physics.setVelocity(physics.getVelocity().projectOnto(backtrackVel.normal()));
            physics.setAcceleration(physics.getAcceleration().projectOnto(backtrackVel.normal()));
        }
    }

}
