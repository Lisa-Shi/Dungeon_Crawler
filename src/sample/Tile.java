package sample;

import javafx.scene.image.Image;

public abstract class Tile extends GameObject {

    // Constructors
    public Tile(Room room, int initialX, int initialY, Image spriteImage) {
        super(room, (initialX * Main.TILE_WIDTH), (initialY * Main.TILE_HEIGHT),
                Main.TILE_WIDTH / 2, Main.TILE_HEIGHT / 2, new SingularImageSheet(spriteImage));
    }
}
