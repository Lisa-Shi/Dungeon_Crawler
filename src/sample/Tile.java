package sample;

import javafx.scene.image.Image;

public abstract class Tile extends GameObject {
    // Constructors
    public Tile(Room room, double initialX, double initialY, String spriteName) {
        super(room, (int) (initialX * Main.TILE_WIDTH), (int) (initialY * Main.TILE_HEIGHT), Main.TILE_WIDTH/2, Main.TILE_HEIGHT / 2, spriteName);
    }
}
