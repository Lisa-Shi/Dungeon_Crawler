package sample;

import java.util.*;

public class GameMap {
    private Room start;
    private Room end;
    private Set<Edge> edge = new HashSet<>();
    private Set<Room> rooms;
    private Map<Room, List<Room>> adjList = new HashMap<>();

    public GameMap(Room starting){
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
        adjList.put(curr, new ArrayList<Room>(List.of(end)));

        for(int i = 0; i < 3; i++){
            Room newRoom = new Room(20, 20);
            rooms.add(newRoom);
            edge.add(new Edge(newRoom, start));
            adjList.get(start).add(newRoom);
            adjList.put(newRoom, new ArrayList<Room>(List.of(start)));
        }
    }
    public List<Room> getAdjRooms(Room room){
        return adjList.get(room);
    }



    /**

    public Map<Room, Integer> dijkstras(Room start) {
        if (start == null || !rooms.contains(start)) {
            throw new IllegalArgumentException("the argument is not valid");
        }
        LinkedList<Room> visitedList = new LinkedList<>();
        Queue<roomDist> theQueue = new PriorityQueue<>();
        Map<Room, Integer> distanceMap = new HashMap<>();
        for (Room v: rooms) {
            distanceMap.put(v, 0);
        }
        theQueue.add(new roomDist(start, 0));
        while (!theQueue.isEmpty() && visitedList.size() < rooms.size()) {
            roomDist current= theQueue.remove();
            Room next = current.getNext();
            int dist = current.getDistance();
            if (!visitedList.contains(next)) {
                visitedList.add(next);
                distanceMap.put(next, dist);
            }
            for (ExitTile each : adjList.get(current)) {
                if (!visitedList.contains(each.getLinkedRoom())) {
                    theQueue.add(new roomDist(each.getLinkedRoom(), 1 + dist));
                } else {
                    theQueue.remove(each.getLinkedRoom());
                }
            }
        }
        return distanceMap;
    }
    private class roomDist{
        private Room next;
        private int distance;
        public roomDist(Room in, int dist){
            next = in;
            distance = dist;
        }
        public Room getNext() {
            return next;
        }
        public int getDistance() {
            return distance;
        }
    }*/
}
