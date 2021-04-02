package gameobjects.monsters;

import main.Main;
import gamemap.Room;

public class MagicianMonster extends Monster {
    public MagicianMonster(Room room, double initialX, double initialY) {
        super(room, 100, 100, 20, initialX, initialY, Main.MAGICIAN_STANDING_SHEET);
    }
}
