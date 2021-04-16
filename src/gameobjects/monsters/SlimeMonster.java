/**class for buzz monster
 *
 */
package gameobjects.monsters;

import gameobjects.Damageable;
import gameobjects.graphics.functionality.CharacterImageSheet;
import gameobjects.graphics.functionality.DirectionalImageSheet;
import gameobjects.graphics.functionality.SpriteController;
import gameobjects.physics.Vector2D;
import gameobjects.potions.HealthPotion;
import javafx.scene.layout.Pane;
import main.Main;
import gamemap.Room;

public class SlimeMonster extends Monster {
    public SlimeMonster(Room room, double initialX, double initialY) {
        super(room, 50, 50, 10, initialX, initialY, Main.SLIME_IMAGE_SHEET);
        this.getCarryReward().put(new HealthPotion(), 1);
    }
    public boolean inRange(Damageable other) {
        Vector2D otherPos = other.getPhysics().getPosition();
        Vector2D thisPos = getPhysics().getPosition();
        double radius = Math.sqrt(otherPos.distanceSquared(thisPos));
        return (radius < Main.SLIME_ATTACK_RADIUS);
    }
    @Override
    public void attack(Room room, Pane pane, Damageable other) {
        SpriteController graphics = getGraphics();
        CharacterImageSheet charImg = (CharacterImageSheet) getSpriteSheet();
        if (inRange(other)) {
            other.hurt(getDamagePerHit());
            DirectionalImageSheet img = charImg.getAttackSheet();
            // This can be refactored
            if (getFacing().equals("W")) {
                graphics.setCurrentReel(img.getUpImage());
            } else if (getFacing().equals("A")) {
                graphics.setCurrentReel(img.getLeftImage());
            } else if (getFacing().equals("S")) {
                graphics.setCurrentReel(img.getDownImage());
            } else {
                graphics.setCurrentReel(img.getRightImage());
            }
        } else {
            graphics.setCurrentReel(charImg.getStandSheet().getInitialReel());
        }
    }
}
