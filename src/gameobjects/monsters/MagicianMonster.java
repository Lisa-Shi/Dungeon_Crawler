package gameobjects.monsters;

import gameobjects.Damageable;
import gameobjects.Projectile;
import javafx.scene.layout.Pane;
import main.Main;
import gamemap.Room;

public class MagicianMonster extends Monster {
    public MagicianMonster(Room room, double initialX, double initialY) {
        super(room, 100, 100, 20, initialX, initialY, Main.MAGICIAN_STANDING_SHEET);
    }
    @Override
    public void attack(Room room, Pane pane, Damageable other) {
        Projectile bullet = new Projectile(this, room, pane);

        room.add(bullet);
        pane.getChildren().add(bullet.getGraphics().getSprite());
        bullet.launchTowardsPoint(other.getPhysics().getPosition(), Main.ENEMY_BULLET_SPEED);
    }
}
