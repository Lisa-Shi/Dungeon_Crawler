package sample;

public interface Passable extends Collideable {
    /**
     * Event that will occur when the player
     * runs into the object
     *
     */
    void collisionWithPlayerEvent(Player player);
}
