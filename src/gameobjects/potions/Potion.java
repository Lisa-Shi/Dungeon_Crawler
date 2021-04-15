package gameobjects.potions;

import gameobjects.Consumable;
import gameobjects.Player;

public abstract class Potion implements Consumable {

    /**
     * Abstract implementation of consume from Consumable interface. Will be overridden by each of the other potion
     * classes.
     * @param player player object that consumes the potion
     */
    public abstract void consume(Player player);

    /**
     * Abstract method to get the name of the potion.
     * @return name of the potion
     */
    public abstract String getName();

    /**
     * Overrides objects equals method for the potions.
     * @param obj the other potion objects
     * @return boolean determining whether the two potions are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Potion) {
            return obj.hashCode() == this.hashCode();
        } else {
            return false;
        }
    }

    /**
     * Overrides Object hashcode function by setting value equal to 11 * the parseInt of the potion name.
     * @return hash code for the potion
     */
    public int hashCode() {
        int hash = 11;
        hash *= Integer.parseInt(this.getName());
        return hash;
    }
}
