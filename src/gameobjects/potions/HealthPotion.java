package gameobjects.potions;

import gameobjects.Player;
import main.Main;

/**
 * Health potion for Dungeon Crawler.
 *
 * Can be held in inventory and when used
 *
 * Increases the health of the player.
 */
public class HealthPotion extends Potion {


    /**
     * Constructor for the Health Potion.
     */
    public HealthPotion() {
        super("HealthPotion", Main.HEALTH_POTION_HEAL, Main.HEALTH_POTION);
    }

    @Override
    public void consume(Player player) {
        player.addPotionConsumed();
        if (player.getHealth() + this.getValue() > player.getMaxHealth()) {
            player.setHealth(player.getMaxHealth());
        } else {
            player.setHealth(player.getHealth() + this.getValue());
        }
    }

}