package gameobjects.potions;

import gameobjects.Player;
import gameobjects.ProjectileLauncher.ProjectileLauncherD;
import gameobjects.graphics.functionality.ImageSheet;
import gameobjects.graphics.functionality.SingularImageSheet;
import main.Main;

/**
 * Bullet potion for Dungeon Crawler.
 *
 * Can be held in inventory and when used
 *
 * Increases the bullets of the player's projectile launcher d
 */
public class BulletPotion extends Potion {
    private static final ImageSheet BULLET_POTION =
            new SingularImageSheet(
                    Main.getImageFrom("../gameobjects/graphics/sprites/potionC.png"));

    /**
     * Constructor for the Bullet Potion.
     */
    public BulletPotion() {
        super("BulletPotion", 1, BULLET_POTION);
    }

    @Override
    public void consume(Player player) {
        ProjectileLauncherD projectileLauncherD = ProjectileLauncherD.getInstance(player);
        projectileLauncherD.addBullets();
    }

}