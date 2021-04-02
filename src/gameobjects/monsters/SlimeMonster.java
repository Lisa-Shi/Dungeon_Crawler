/**class for buzz monster
 *
 */
package gameobjects.monsters;

import main.Main;
import gamemap.Room;

public class SlimeMonster extends Monster {
    public SlimeMonster(Room room, double initialX, double initialY) {
        super(room, 100, 100, 10, initialX, initialY, Main.SLIME_STANDING_SHEET);
    }
}
