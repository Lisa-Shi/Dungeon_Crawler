package gameobjects;

import javafx.scene.image.Image;

/**
 * Attack potion for Dungeon Crawler. Can be held in inventory and when used, increases the damage of the weapon that
 * the player is wielding.
 */
public class AttackPotion implements Consumable {

    private Image image;

    @Override
    public void consume() {

    }

    /**
     * Getter method for image variable.
     * @return image of the potion
     */
    public Image getImage() {
        return image;
    }
}
