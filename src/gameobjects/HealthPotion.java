package gameobjects;

import javafx.scene.image.Image;
import main.GameStage;
import main.Main;

/**
 * Health potion for Dungeon Crawler. Can be held in inventory and when used, increases the health of the player.
 */
public class HealthPotion implements Consumable {

    private Image image;
    private int healthGained;

    /**
     * Constructor for the Health Potion.
     */
    public HealthPotion() {
        healthGained = Main.HEALTH_POTION_HEAL;
    }

    @Override
    public void consume(Player player) {
        player.setHealth(player.getHealth() + healthGained);
    }

    /**
     * Getter method for image variable.
     * @return image of the potion
     */
    public Image getImage() {
        return image;
    }

    /**
     * Getter method for the healthGained variable.
     * @return the health that the player will gain
     */
    public int getHealthGained() {
        return healthGained;
    }

    /**
     * Setter for healthGained variable.
     * @param health health that the player will gain
     */
    public void setHealthGained(int health) {
        healthGained = health;
    }
}