/**class for buzz monster
 *
 */
package gameobjects.monsters;

import main.Main;
import gamemap.Room;

public class BuzzMonster extends Monster {
    public BuzzMonster(Room room, double initialX, double initialY) {
        super(room, 100, 100, 10, initialX, initialY, Main.BUZZ_STANDING_SHEET);
    }
}
