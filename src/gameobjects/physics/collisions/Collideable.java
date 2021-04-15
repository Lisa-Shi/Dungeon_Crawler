/**when a game object is collideable, it will have an collisionBox attribute.
 * by calling the function in the collisionbox class, we can check the status of the object
 * for example: if it contains a particular point
 * see collisionBox.java for details
 */
package gameobjects.physics.collisions;

public interface Collideable extends Physical {
    /**
     * Gets collision box of object
     *
     * @return collision box of object
     */
    CollisionBox getCollisionBox();
}
