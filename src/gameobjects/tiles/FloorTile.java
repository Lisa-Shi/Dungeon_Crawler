package gameobjects.tiles;

import main.Main;
import gamemap.Room;

public class FloorTile extends Tile {
    // Constructors
    public FloorTile(Room inRoom, int initialX, int initialY) {
        super(inRoom, initialX, initialY, Main.FLOORTILE);
    }
}
