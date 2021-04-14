package gameobjects;

import gamemap.Room;
import gameobjects.physics.collisions.Collideable;
import gameobjects.physics.collisions.CollisionBox;
import gameobjects.physics.collisions.RectangleWireframe;
import javafx.scene.image.Image;
import main.Main;

import java.util.LinkedList;

public class NPC extends GameObject implements Collideable{
    private CollisionBox collisionBox;
    private LinkedList<GameObject> sellingItem = new LinkedList<>();
    public NPC(Room room, double initialX, double initialY){
        super(room, initialX * Main.TILE_WIDTH, initialY * Main.TILE_HEIGHT,
                Main.MONSTER_WIDTH, Main.MONSTER_HEIGHT, Main.NPC_small);
        collisionBox = new CollisionBox(this.getPhysics(),
                new RectangleWireframe(Main.MONSTER_WIDTH, Main.MONSTER_HEIGHT));
    }
    public Image getBigIcon(){
        return Main.NPC_big;
    }
    public String getWelcome(){
        return "Welcome to the store";
    }
    public void sell(GameObject item, int quanlity){

    }
    public CollisionBox getCollisionBox(){
        return collisionBox;
    }
}
