package gameobjects;

import gameobjects.potions.Potion;
import javafx.scene.layout.Pane;

import java.util.Map;

public interface Openable {
    Map<Potion, Integer> getInventory();
    void loseItem(Potion item);
    void buttonAction(Player player, Potion potion);
}
