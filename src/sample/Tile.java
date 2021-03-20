package sample;

public abstract class Tile extends GameObject {

    // Constructors
    public Tile(Room room, int initialX, int initialY, String spriteName) {
        super(room, (initialX * Main.TILE_WIDTH), (initialY * Main.TILE_HEIGHT),
                Main.TILE_WIDTH / 2, Main.TILE_HEIGHT / 2, spriteName);
    }
}
