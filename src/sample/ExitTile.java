package sample;


public class ExitTile extends Tile implements Passable {
    // Variables
    private Room linkedRoom;
    private CollisionBox collisionBox;
    private int exitX;
    private int exitY;
    private Room inRoom;

    // Constructors
    public ExitTile(Room inRoom, int initialX, int initialY, Room linkedRoom) {
        super(inRoom, initialX, initialY, Main.EXITTILE);
        this.inRoom = inRoom;
        exitX = initialX;
        exitY = initialY;
        this.linkedRoom = linkedRoom;
        this.collisionBox = new CollisionBox(getPhysics(),
                new RectangleWireframe(Main.TILE_WIDTH, Main.TILE_HEIGHT), false);
        this.collisionBox.generate();
    }

    // Getters

    public Room getInRoom() {
        return inRoom;
    }

    public int getExitY() {
        return exitY;
    }

    public int getExitX() {
        return exitX;
    }

    public int[] getNextTo() {
        //X: 0, Y: # -> vertical right
        //X: 19, Y: # -> vertical left
        //X: #, Y: 0 -> top
        //X: #, Y: 19 -> bottom

        return null;
    }

    public Room getLinkedRoom() {
        return linkedRoom;
    }

    @Override
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    @Override
    public void collisionWithPlayerEvent(Player player) {
        System.out.println("entering " + getLinkedRoom().getRoomId());
        GameMap.enterRoom(getLinkedRoom());
    }

    /**
     * if two tiles are in the same room and on the same side of the room
     * they are considered to be equal.
     * @param other other object to compare with
     * @return true if this tile is equal to the param object
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof ExitTile) {
            if (this.inRoom.equals((((ExitTile) other).getInRoom()))) {
                return ((ExitTile) other).getExitX() == this.exitX
                        || ((ExitTile) other).getExitY() == this.exitY;
            }
        }
        return false;
    }
    @Override
    public int hashCode(){
        return this.exitX * 97 + this.exitY * 89;
    }
}
