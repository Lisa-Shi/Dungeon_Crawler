package gameobjects.npc;

import gamemap.Room;
import gameobjects.Inventory;
import gameobjects.Openable;
import gameobjects.Player;
import gameobjects.potions.AttackPotion;
import gameobjects.potions.HealthPotion;
import gameobjects.potions.Potion;
import javafx.scene.layout.Pane;

import java.util.Map;
import java.util.TreeMap;

public class storeNPC extends NPC implements Openable{

    private Map<Potion, Integer> inventory = new TreeMap<>();
    private Map<Potion, Integer> price = new TreeMap<>();
    public storeNPC(Room room, double initialX, double initialY){
        super(room, initialX, initialY);
        inventory.put(new AttackPotion(), 99);
        inventory.put(new HealthPotion(), 99);
        price.put(new AttackPotion(), 10);
        price.put(new HealthPotion(), 50);
    }
    public void buttonAction(Player player, Potion potion) {
        if (player.getMoney() >= getPrice(potion)) {
            player.setMoney(player.getMoney() - getPrice(potion));
            player.getItem(potion);
            loseItem(potion);
        }
    }
    public String getString(){
        return "Welcome to the store.";
    }

    @Override
    public void open(Player player, Pane pane) {
        Inventory inventory = Inventory.getInstance(this, pane, player);
        inventory.show();
    }
    public int getPrice(Potion potion) {
        return price.get(potion);
    }
    @Override
    public Map<Potion, Integer> getInventory() {
        return inventory;
    }

    @Override
    public void loseItem(Potion potion) {

    }
}
