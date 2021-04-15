/**
 * class for buzz monster
 */
package gameobjects.monsters;

import gameobjects.Damageable;
import gameobjects.physics.Vector2D;
import javafx.scene.layout.Pane;
import main.Main;
import gamemap.Room;

public class BuzzMonster extends Monster {
    public BuzzMonster(Room room, double initialX, double initialY) {
        super(room, 100, 100, 10, initialX, initialY, Main.BUZZ_STANDING_SHEET);
    }

    @Override
    public void attack(Room room, Pane pane, Damageable other) {
        Vector2D playerPos = other.getPhysics().getPosition();
        Vector2D monsterPos = this.getPhysics().getPosition();
        double squareDisplace = playerPos.distanceSquared(monsterPos);
        double tileDistanceSquared = Main.TILE_HEIGHT * Main.TILE_WIDTH;

        if (squareDisplace <= tileDistanceSquared) {
            other.hurt(1);
        }

    }
}
