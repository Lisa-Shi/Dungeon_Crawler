package gameobjects;

import gamemap.Room;
import gameobjects.graphics.functionality.Drawable;
import gameobjects.physics.Camera;
import gameobjects.physics.collisions.Collideable;
import gameobjects.physics.collisions.CollisionBox;
import gameobjects.physics.collisions.RectangleWireframe;
import gameobjects.potions.AttackPotion;
import gameobjects.potions.HealthPotion;
import gameobjects.potions.Potion;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import main.Main;

import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class NPC extends GameObject implements Collideable, Drawable, Openable{
    private CollisionBox collisionBox;
    private Map<Potion, Integer> inventory = new TreeMap<>();
    private Map<Potion, Integer> price = new TreeMap<>();
    public NPC(Room room, double initialX, double initialY){
        super(room, initialX * Main.TILE_WIDTH, initialY * Main.TILE_HEIGHT,
                Main.MONSTER_WIDTH / 2, Main.MONSTER_HEIGHT / 2, Main.NPC_small);
        collisionBox = new CollisionBox(this.getPhysics(),
                new RectangleWireframe(Main.MONSTER_WIDTH, Main.MONSTER_HEIGHT));
        collisionBox.generate();
        inventory.put(new AttackPotion(), 99);
        inventory.put(new HealthPotion(), 99);
        price.put(new AttackPotion(), 10);
        price.put(new HealthPotion(), 50);

    }
    public Image getBigIcon(){
        return Main.NPC_big;
    }

    @Override
    public void update(Camera camera) {
        super.update(camera);
    }

    public String getWelcome(){
        return "Welcome to the store";
    }

    @Override
    public void open(Player player, Pane pane) {
        Inventory inventory = Inventory.getInstance(this, pane, player);
        inventory.show();
    }
    public int getPrice(Potion potion){
        return price.get(potion);
    }
    @Override
    public Map<Potion, Integer> getInventory() {
        return inventory;
    }

    @Override
    public void loseItem(Potion potion) {

    }

    public CollisionBox getCollisionBox(){
        return collisionBox;
    }
}