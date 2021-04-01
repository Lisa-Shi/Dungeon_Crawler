package sample;

public class MagicianMonster extends Monster {
    public MagicianMonster(Room room, double initialX, double initialY) {
        super(room, 100, 100, 10, initialX, initialY, Main.MAGICIAN_STANDING_SHEET);
    }
}
