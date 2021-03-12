package sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ExitTile extends Tile implements Passable {
    // Variables
    private int linkedRoom;
    private CollisionBox collisionBox;

    // Constructors
    public ExitTile(Room inRoom, int initialX, int initialY, int linkedRoom) {
        super(inRoom, initialX, initialY, "spr_dungeon_exit.png");
        this.linkedRoom = linkedRoom;
        this.collisionBox = new CollisionBox(getPhysics(), new RectangleWireframe(Main.TILE_WIDTH, Main.TILE_HEIGHT), false);
        this.collisionBox.generate();
    }

    // Getters
    public int getLinkedRoom() {
        return linkedRoom;
    }

    @Override
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    @Override
    public boolean collisionWithPlayerEvent(Player player) {
        // Linking rooms goes here!
        //center point of player
        Vector2D playerCenter = player.getCollisionBox().getPhysics().getAbsolutePosition();
        return this.collisionBox.containsPoint(playerCenter);
    }

}
