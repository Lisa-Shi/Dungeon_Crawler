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
     * minimum of 6 room is initialized between starting room and end
     * in total 4 rooms are initialized around starting room
     * @param starting the room where the player is born
     */
    public GameMap(Room starting) {
        currentRoom = starting;
        start = starting;
        end = new Room(20, 20, 999);
        rooms = new HashSet<>();

        addRooms();
        connectRooms();

        start.generateExits(adjList.get(start));
    }

    public void restartMap() {
        start = null;
        end = null;
        currentRoom = null;
        rooms = null;
        adjList = new HashMap<>();
        edge = new HashSet<>();
    }

    private void addRooms() {
        for (int i = 0; i < 6; i++) {
            rooms.add(new Room(20, 20));
        }
    }

    private void connectRooms() {
        Room roomBeforePrev = null;
        Room prev = start;

        for (Room room : rooms) {
            // Create list of adjacent rooms to this room
            ArrayList<Room> adjacent = new ArrayList<>();

            // Add room to list
            adjacent.add(room);

            // Add previous room list
            if (roomBeforePrev != null) {
                adjacent.add(roomBeforePrev);
            }

            // Record the adjacent rooms
            adjList.put(prev, adjacent);

            // Connect rooms
            edge.add(new Edge(prev, room));
            roomBeforePrev = prev;
            prev = room;
        }

        ArrayList arr1 = new ArrayList<>();
        arr1.add(roomBeforePrev);
        adjList.put(prev, arr1);
        adjList.get(prev).add(end);

        ArrayList arr2 = new ArrayList<>();
        arr2.add(prev);
        adjList.put(end, arr2);
        for (int i = 0; i < 3; i++) {
            Room newRoom = new Room(20, 20);
            rooms.add(newRoom);
            edge.add(new Edge(newRoom, start));
            adjList.get(start).add(newRoom);
            ArrayList<Room> r2 = new ArrayList<Room>();
            r2.add(start);
            adjList.put(newRoom, r2);
        }
    }


    public static void enterRoom(Room entering) {
        currentRoom = entering;
    }
    public static Room enterRoom() {
        return currentRoom;
    }
    /**
     * get the list of adjacent rooms around the param
     * @param room the center room that connects the adjacent rooms
     * @return list of adjacent rooms
     */
    public List<Room> getAdjRooms(Room room) {
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
        public boolean equals(Object other) {
            if (other instanceof Edge) {
                if (((Edge) other).getInRoom().equals(this.inRoom)
                        && ((Edge) other).getLinkedRoom().equals(this.linkedRoom)) {
                    return true;
                }
                if (((Edge) other).getInRoom().equals(this.linkedRoom)
                        && ((Edge) other).getLinkedRoom().equals(this.inRoom)) {
                    return true;
                }
            }
            return false;
        }
    }

}
