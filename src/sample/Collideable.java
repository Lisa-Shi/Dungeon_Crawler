package sample;

public interface Collideable {
    /**
     * Gets collision box of object
     *
     * @return collision box of object
     */
    CollisionBox getCollisionBox();
    boolean collideableEqual(Object other);
}
