package gameobjects;

import gamemap.Room;
import gameobjects.ProjectileLauncher.LauncherInventory;
import gameobjects.ProjectileLauncher.Projectile;
import gameobjects.ProjectileLauncher.ProjectileLauncher;
import gameobjects.graphics.functionality.Drawable;
import gameobjects.graphics.functionality.ImageSheet;
import gameobjects.physics.Camera;
import gameobjects.physics.collisions.Collideable;
import gameobjects.physics.collisions.CollisionBox;
import gameobjects.physics.collisions.RectangleWireframe;
import gameobjects.potions.Potion;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import main.Main;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class PotionChest extends GameObject implements Collideable, Drawable, Openable {
    // Variables
    private int money;
    private Map<Potion, Integer> treasure;
    private Set<ProjectileLauncher> launcherSet;
    private boolean isOpen = false;
    private boolean isEmpty = false;
    private CollisionBox collisionBox;
    // Constructors
    public PotionChest(int money, Map<Potion, Integer> treasure, Room room, double initialX, double initialY) {
        super(room, initialX * Main.TILE_WIDTH, initialY * Main.TILE_HEIGHT,
                Main.MONSTER_WIDTH / 2, Main.MONSTER_HEIGHT / 2, Main.CHEST_CLOSE);
        this.money = money;
        this.treasure = treasure;
        this.collisionBox = new CollisionBox(this.getPhysics(), new RectangleWireframe(Main.MONSTER_WIDTH, Main.MONSTER_HEIGHT));
        collisionBox.generate();
    }

    public void open(Player player, Pane pane) {
        takeMoney(player);
        Inventory inventory = Inventory.getInstance(this, pane, player);
        inventory.show();
    }

    @Override
    public Map<Potion, Integer> getInventory() {
        return treasure;
    }

    public void loseItem(Potion potion) {
        if(treasure.containsKey(potion)) {
            if(treasure.get(potion)-1 != 0) {
                treasure.put(potion, treasure.get(potion) - 1);
            } else {
                treasure.remove(potion);
            }
        }
    }
    // Misc.
    public void takeMoney(Player player) {
        isOpen = true;
        player.setMoney(player.getMoney()+money);
        money = 0;
        if (treasure.keySet().isEmpty()) {
            isEmpty = true;
            this.getGraphics().setCurrentReel(Main.CHEST_EMPTY.getInitialReel());
        } else {
            this.getGraphics().setCurrentReel(Main.CHEST_OPEN.getInitialReel());
        }
    }

    @Override
    public void update(Camera camera) {
        super.update(camera);
    }

    // Getters
    public CollisionBox getCollisionBox(){
        return collisionBox;
    }
    public boolean isOpen() {
        return isOpen;
    }
}
