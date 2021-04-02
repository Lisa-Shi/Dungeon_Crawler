package gameobjects.tiles;

import gameobjects.GameObject;
import gameobjects.graphics.functionality.SingularImageSheet;
import javafx.scene.image.Image;
import main.Main;
import gamemap.Room;

public abstract class Tile extends GameObject {

    // Constructors
    public Tile(Room room, int initialX, int initialY, Image spriteImage) {
        super(room, (initialX * Main.TILE_WIDTH), (initialY * Main.TILE_HEIGHT),
                Main.TILE_WIDTH / 2, Main.TILE_HEIGHT / 2, new SingularImageSheet(spriteImage));
    }
}
