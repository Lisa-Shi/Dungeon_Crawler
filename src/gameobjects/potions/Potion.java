package gameobjects.potions;

import gameobjects.Consumable;
import gameobjects.Player;
import gameobjects.graphics.functionality.Drawable;
import gameobjects.graphics.functionality.ImageSheet;
import gameobjects.graphics.functionality.SpriteController;

public abstract class Potion implements Consumable, Comparable{

    private ImageSheet spriteSheet;
    private String name;
    private int value;
    public Potion(String name, int value, ImageSheet spriteSheet){
        this.spriteSheet = spriteSheet;
        this.name = name;
        this.value = value;
    }
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
    public String getName(){
        return name;
    };

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public ImageSheet getImage(){
        return this.spriteSheet;
    }
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
        hash *= this.getName().hashCode();
        return hash;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Potion) {
            return o.hashCode() - this.hashCode();
        }
        return 1;
    }
}
