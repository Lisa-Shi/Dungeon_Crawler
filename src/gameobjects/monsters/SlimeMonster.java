/**class for buzz monster
 *
 */
package gameobjects.monsters;

import gameobjects.Damageable;
import gameobjects.graphics.functionality.CharacterImageSheet;
import javafx.scene.layout.Pane;
import main.Main;
import gamemap.Room;

public class SlimeMonster extends Monster {
    public SlimeMonster(Room room, double initialX, double initialY) {
        super(room, 50, 50, 10, initialX, initialY, Main.SLIME_IMAGE_SHEET);
    }
    public boolean inRange(Damageable other) {
        return (Math.sqrt(other.getPhysics().getPosition().
                distanceSquared(getPhysics().getPosition())) < Main.SLIME_ATTACK_RADIUS);
    }
    @Override
    public void attack(Room room, Pane pane, Damageable other) {
        if (inRange(other)) {
            other.hurt(getDamagePerHit());

            // This can be refactored
            if (getFacing().equals("W")) {
                getGraphics().setCurrentReel(
                        ((CharacterImageSheet) getSpriteSheet()).getAttackSheet().getUpImage());
            } else if (getFacing().equals("A")) {
                getGraphics().setCurrentReel(
                        ((CharacterImageSheet) getSpriteSheet()).getAttackSheet().getLeftImage());
            } else if (getFacing().equals("S")) {
                getGraphics().setCurrentReel(
                        ((CharacterImageSheet) getSpriteSheet()).getAttackSheet().getDownImage());
            } else {
                getGraphics().setCurrentReel(
                        ((CharacterImageSheet) getSpriteSheet()).getAttackSheet().getRightImage());
            }
        } else {
            getGraphics().setCurrentReel(
                    ((CharacterImageSheet) getSpriteSheet()).getStandSheet().getInitialReel());
        }
    }
}
