package sample;

public interface Passable extends Collideable {
    /**
     * Event that will occur when the player
     * runs into the object
     *
     * @param player game player
     */
    void collisionWithPlayerEvent(Player player);
}
