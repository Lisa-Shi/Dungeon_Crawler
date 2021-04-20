package gameobjects.monsters;

import gamemap.Room;
import gameobjects.Damageable;
import gameobjects.physics.Vector2D;
import gameobjects.potions.EndGameAttackPotion;
import javafx.scene.layout.Pane;
import main.Main;

public class BossMonster extends Monster {

    public BossMonster(Room room, double initialX, double initialY) {
        super(room, 500, 500, 50, initialX, initialY, Main.BOSS_STANDING_SHEET);
        this.getCarryReward().put(new EndGameAttackPotion(), 6);
    }

    @Override
    public void attack(Room room, Pane pane, Damageable other) {
        if (inRange(other)) {
            other.hurt(1);
        }
    }

    public boolean inRange(Damageable other) {
        Vector2D otherPos = other.getPhysics().getPosition();
        Vector2D thisPos = getPhysics().getPosition();
        double radius = Math.sqrt(otherPos.distanceSquared(thisPos));
        return (radius < Main.BOSS_ATTACK_RADIUS);
    }
}
