package gameobjects;

import javafx.scene.image.Image;

/**
 * Health potion for Dungeon Crawler. Can be held in inventory and when used, increases the health of the player.
 */
public class HealthPotion implements Consumable {

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
