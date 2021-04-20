package gameobjects.monsters;

import gamemap.Room;
import gameobjects.Damageable;
import gameobjects.physics.Vector2D;
import gameobjects.potions.EndGameAttackPotion;
import javafx.scene.layout.Pane;
import main.Main;

/**
 * Final monster to be fought in the last room of the game.
 */
public class BossMonster extends Monster {

    /**
     * Constructor for the boss
     * @param room room 9 that the boss is found
     * @param initialX x value where the boss is spawned
     * @param initialY y value where the boss is spawned
     */
    public BossMonster(Room room, double initialX, double initialY) {
        super(room, 500, 500, 50, initialX, initialY, Main.BOSS_STANDING_SHEET);
        this.getCarryReward().put(new EndGameAttackPotion(), 6);
    }

    /**
     * Overrides the attack method for the monster class
     * @param room the room which the monster is located
     * @param pane overall pane
     * @param other what to damage
     */
    @Override
    public void attack(Room room, Pane pane, Damageable other) {
        if (inRange(other)) {
            other.hurt(1);
        }
    }

    /**
     * Method to check if the player is in range of the monster.
     * @param other the player
     * @return boolean of whether the monster is in range or not
     */
    public boolean inRange(Damageable other) {
        Vector2D otherPos = other.getPhysics().getPosition();
        Vector2D thisPos = getPhysics().getPosition();
        double radius = Math.sqrt(otherPos.distanceSquared(thisPos));
        return (radius < Main.BOSS_ATTACK_RADIUS);
    }
}
