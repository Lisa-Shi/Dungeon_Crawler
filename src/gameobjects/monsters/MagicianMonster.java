package gameobjects.monsters;

import gameobjects.Damageable;
import gameobjects.ProjectileLauncher.Projectile;
import gameobjects.physics.Camera;
import gameobjects.potions.AttackPotion;
import javafx.scene.layout.Pane;
import main.Main;
import gamemap.Room;

public class MagicianMonster extends Monster {
    public MagicianMonster(Room room, double initialX, double initialY) {
        super(room, 100, 100, 20, initialX, initialY, Main.MAGICIAN_STANDING_SHEET);
        this.getCarryReward().put(new AttackPotion(), 1);
    }
    @Override
    public void attack(Room room, Pane pane, Damageable other, Camera camera) {
        Projectile bullet = new Projectile(this, room, pane);
        room.add(bullet);
        pane.getChildren().add(bullet.getGraphics().getSprite());
        bullet.launchTowardsPoint(other.getPhysics().getPosition(), Main.ENEMY_BULLET_SPEED);
        bullet.update(camera);
    }
}
