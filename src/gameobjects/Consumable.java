package gameobjects;

public interface Consumable {

    /**
     * Allows player to consume the object.
     * @param player that consumes object
     */
    void consume(Player player);
}
