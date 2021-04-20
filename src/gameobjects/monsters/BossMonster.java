package gameobjects.monsters;

import gamemap.Room;
import gameobjects.Damageable;
import gameobjects.potions.Potion;
import javafx.scene.layout.Pane;
import main.Main;

public class BossMonster extends Monster {

    public BossMonster(Room room, double initialX, double initialY) {
        super(room, 500, 500, 50, initialX, initialX, Main.BUZZ_STANDING_SHEET);
        this.getCarryReward().put(new Potion(), 4);
    }

    @Override
    public void attack(Room room, Pane pane, Damageable other) {
        if (inRange(other)) {
            other.hurt(1);
        }
    }
}
