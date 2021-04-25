/**
 * class for buzz monster
 */
package gameobjects.monsters;

import gameobjects.Damageable;
import gameobjects.physics.Camera;
import gameobjects.physics.Vector2D;
import gameobjects.potions.HealthPotion;
import javafx.scene.layout.Pane;
import main.Main;
import gamemap.Room;

public class BuzzMonster extends Monster {
    public BuzzMonster(Room room, double initialX, double initialY) {
        super(room, 100, 100, 10, initialX, initialY, Main.BUZZ_STANDING_SHEET);
        this.getCarryReward().put(new HealthPotion(), 5);
    }

    public boolean inRange(Damageable other) {
        Vector2D otherPos = other.getPhysics().getPosition();
        Vector2D thisPos = getPhysics().getPosition();
        double radius = Math.sqrt(otherPos.distanceSquared(thisPos));
        return (radius < Main.BUZZ_ATTACK_RADIUS);
    }

    @Override
    public void attack(Room room, Pane pane, Damageable other) {
        if (inRange(other)) {
            other.hurt(1);
        }
    }
}
