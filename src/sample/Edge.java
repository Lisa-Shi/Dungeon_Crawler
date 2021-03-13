package sample;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Edge {
    private Room linkedRoom;
    private Room inRoom;

    public Edge(Room inRoom, Room linkedRoom) {
        this.inRoom = inRoom;
        this.linkedRoom = linkedRoom;
    }

    public Room getInRoom() {
        return inRoom;
    }

    public Room getLinkedRoom() {
        return linkedRoom;
    }
    @Override
    public boolean equals(Object other){
        if( other instanceof Edge){
            if( ((Edge)other).getInRoom().equals(this.inRoom) && ((Edge)other).getLinkedRoom().equals(this.linkedRoom)){
                return true;
            }
            if( ((Edge)other).getInRoom().equals(this.linkedRoom) && ((Edge)other).getLinkedRoom().equals(this.inRoom)){
                return true;
            }
        }
        return false;
    }
}
