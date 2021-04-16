package gameobjects;

import gamemap.Room;
import gameobjects.graphics.functionality.Drawable;
import gameobjects.physics.Camera;
import gameobjects.physics.collisions.Collideable;
import gameobjects.physics.collisions.CollisionBox;
import gameobjects.physics.collisions.RectangleWireframe;
import gameobjects.potions.Potion;
import javafx.scene.layout.Pane;
import main.Main;
import java.util.Map;

public class LauncherChest extends GameObject implements Collideable, Drawable, Openable {
    // Variables
    private int money;
    private boolean isOpen = false;
    private boolean isEmpty = false;
    private CollisionBox collisionBox;

    // Constructors
    public LauncherChest(int money, Room room, double initialX, double initialY) {
        super(room, initialX * Main.TILE_WIDTH, initialY * Main.TILE_HEIGHT,
                Main.MONSTER_WIDTH / 2, Main.MONSTER_HEIGHT / 2, Main.CHEST_CLOSE);
        this.money = money;
        this.collisionBox = new CollisionBox(this.getPhysics(),
                new RectangleWireframe(Main.MONSTER_WIDTH, Main.MONSTER_HEIGHT));
        collisionBox.generate();
    }

    public void open(Player player, Pane pane) {
        Inventory inventory = Inventory.getInstance(this, pane, player);
        inventory.show();
    }

    @Override
    public Map<Potion, Integer> getInventory() {
        return null;
    }

    @Override
    public void loseItem(Potion potion) {

    }

    @Override
    public void update(Camera camera) {
        super.update(camera);
    }

    // Getters
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }
    public boolean isOpen() {
        return isOpen;
    }
}
