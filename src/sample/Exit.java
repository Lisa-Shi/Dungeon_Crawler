package sample;

public class Exit {
    // Variables
    private Vector2D location;
    private Room linkedRoom;

    // Constructors
    public Exit(Vector2D location, Room linkedRoom) {
        this.location = location;
        this.linkedRoom = linkedRoom;
    }

    // Getters
    public Vector2D getLocation() {
        return location;
    }
    public Room getLinkedRoom() {
        return linkedRoom;
    }
}
