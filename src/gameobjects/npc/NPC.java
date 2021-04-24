package gameobjects.npc;

import gamemap.Room;
import gameobjects.*;
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

import java.util.Map;
import java.util.TreeMap;

public abstract class NPC extends GameObject implements Collideable, Drawable, Interactable {
    private CollisionBox collisionBox;
    private Room room;
    public NPC(Room room, double initialX, double initialY) {
        super(room, initialX * Main.TILE_WIDTH, initialY * Main.TILE_HEIGHT,
                Main.MONSTER_WIDTH / 2, Main.MONSTER_HEIGHT / 2, Main.NPC_SMALL);
        collisionBox = new CollisionBox(this.getPhysics(),
                new RectangleWireframe(Main.MONSTER_WIDTH, Main.MONSTER_HEIGHT));
        collisionBox.generate();
        this.room = room;
    }
    public Image getBigIcon() {
        return Main.NPC_BIG;
    }
    @Override
    public void update(Camera camera) {
        super.update(camera);
    }
    public abstract void open(Player player, Pane pane);
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }
}