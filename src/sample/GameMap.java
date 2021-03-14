package sample;

import java.util.*;

/**
 * the overall map of the game
 *
 *
 */
public class GameMap {
    private static Room currentRoom;
    private Room start;
    private Room end;
    private Set<Edge> edge = new HashSet<>();
    private Set<Room> rooms;
    private Map<Room, List<Room>> adjList = new HashMap<>();

    /**
     * minimun of 6 room is initialized between starting room and end
     * in total 4 rooms are initialized around starting room
     * @param starting the room where the player is born
     */
    public GameMap(Room starting){
        currentRoom = starting;
        start = starting;
        end = new Room(20, 20, 999);
        rooms = new HashSet<>();
        for( int i = 0 ; i < 6; i++){
            rooms.add(new Room(20, 20));
        }
        Room pre = null;
        Room curr = starting;
        for( Room a: rooms){
            ArrayList<Room> adj = new ArrayList<>();
            adj.add(a);
            if(pre != null) {
                adj.add(pre);
            }
            adjList.put(curr, adj);
            edge.add(new Edge(curr, a));
            pre = curr;
            curr = a;
        }
        edge.add(new Edge(curr, end));
        ArrayList<Room> r1 = new ArrayList<Room>();
        r1.add(end);
        adjList.put(curr, r1);

        for(int i = 0; i < 3; i++){
            Room newRoom = new Room(20, 20);
            rooms.add(newRoom);
            edge.add(new Edge(newRoom, start));
            adjList.get(start).add(newRoom);
            ArrayList<Room> r2 = new ArrayList<Room>(List.of(start));
            adjList.put(newRoom, r2);
        }
        start.generateExits(adjList.get(start));
    }
    public static void enterRoom(Room entering){
        currentRoom = entering;
    }
    public static Room enterRoom(){
        return currentRoom;
    }
    /**
     * get the list of adjacent rooms around the param
     * @param room the center room that connects the adjacent rooms
     * @return list of adjacent rooms
     */
    public List<Room> getAdjRooms(Room room){
        return adjList.get(room);
    }

    private class Edge {
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

}
