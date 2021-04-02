/**essential attribute for collideable game object
 *Term in javadoc:
 *  owner: the game object which this collisionBox belong to
 */

package gameobjects.physics.collisions;

import gameobjects.physics.PhysicsController;
import gameobjects.physics.PhysicsControllerRelative;
import gameobjects.physics.Vector2D;

public class CollisionBox {
    // Variables
    private PhysicsControllerRelative physics;
    private PolygonWireframe wireframe;
    private boolean solid;

    // Constructors
    public CollisionBox(PhysicsController physics, PolygonWireframe wireframe) {
        this(physics, wireframe, true);
    }

    /**
     * constructor
     * must call generate() after instantiation
     * @param physics usually same as the gameobjects.physics of the owner
     * @param wireframe the actual box. height and weidth are usually the same as the owner
     * @param solid if true, other game object with solid collisionBox will not be
     *              able to get through the owner
     */
    public CollisionBox(PhysicsController physics, PolygonWireframe wireframe, boolean solid) {
        this.physics = new PhysicsControllerRelative(
                physics.getPosition().getX(), physics.getPosition().getY(), physics);
        this.wireframe = wireframe;
        this.solid = solid;
    }

    /**
     * must be called before construction
     */
    public void generate() {
        getWireframe().generate();
        getPhysics().setPosition(getWireframe().getCenter());
    }

    /**
     * check if the given point is within the collisionBox
     * @param absolutePoint point
     * @return true if the point is inside the collisionBox
     */
    public boolean containsPoint(Vector2D absolutePoint) {
        Vector2D relativePoint = absolutePoint.subtract(getPhysics().getAbsolutePosition());
        return wireframe.containsPoint(relativePoint);
    }

    @Override
    public String toString() {
        String verticesString = "";
        for (Vector2D vertex : wireframe.getVertices()) {
            verticesString = verticesString.concat(vertex + ", ");
        }
        return "Collision box with vertices: "
                + verticesString.substring(0, verticesString.length() - 2);
    }

    // Getters
    public PhysicsControllerRelative getPhysics() {
        return physics;
    }
    public PolygonWireframe getWireframe() {
        return wireframe;
    }
    public boolean isSolid() {
        return solid;
    }

    // Setters
    public void setSolid(boolean solid) {
        this.solid = solid;
    }
    /**
     * if gameobjects.physics (Position) and solid are the same,
     * then the two collision boxes are considered to be true
     *
     * @param other object to compare to
     * @return true if equal. otherwise false
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof CollisionBox) {
            return ((CollisionBox) other).getPhysics().equals(this.physics)
                    && ((CollisionBox) other).isSolid() == this.isSolid();
        }
        return false;
    }
}
