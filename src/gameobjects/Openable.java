package gameobjects;

import gameobjects.potions.Potion;
import javafx.scene.layout.Pane;

import java.util.Map;

public interface Openable {
    void open(Player player, Pane pane);
    Map<Potion, Integer> getInventory();
    void loseItem(Potion potion);
}
