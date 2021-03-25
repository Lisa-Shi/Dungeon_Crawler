package sample;

/**
 * the monster class
 */
import javafx.scene.image.Image;

import java.util.*;

public class Monster extends GameObject implements Damageable, Collideable, Drawable {
    // no

    // Variables
    private int health;
    private int damagePerHit;
    private DynamicCollisionBox collisionBox;

    private int shootingRange = 3;
    private CollisionBox attackRange =
            new CollisionBox(getPhysics(),
                    new RectangleWireframe(shootingRange * Main.TILE_WIDTH, shootingRange * Main.TILE_HEIGHT));

    private String facing;

    public Monster(Room room, int health, int damagePerHit, double initialX, double initialY, DirectionalImageSheet sheet) {
        super(room, initialX * Main.TILE_WIDTH, initialY * Main.TILE_HEIGHT
                    , Main.MONSTER_WIDTH / 2, Main.MONSTER_HEIGHT / 2, sheet);

        this.health = health;
        this.damagePerHit = damagePerHit;

        this.collisionBox = new DynamicCollisionBox(getPhysics(),
                new RectangleWireframe(Main.MONSTER_WIDTH, Main.MONSTER_HEIGHT));
        this.collisionBox.generate();
        this.attackRange.generate();

        this.facing = "A";
    }

    // Misc.
    private boolean damageableInRange(Damageable damageable) {
        return attackRange.containsPoint(damageable.getPhysics().getPosition());
    }
    public void attack(Damageable damageable) {
        if (damageableInRange(damageable)) {
            damageable.hurt(damagePerHit);
        }
    }

    @Override
    public void update(Camera camera) {
        switch(facing) {
            case "A": getPhysics().pushLeft(Main.ENEMY_CONTROL_FORCE);
                      break;
            case "D": getPhysics().pushRight(Main.ENEMY_CONTROL_FORCE);
                      break;
            case "W": getPhysics().pushUp(Main.ENEMY_CONTROL_FORCE);
                      break;
            case "S": getPhysics().pushDown(Main.ENEMY_CONTROL_FORCE);
                      break;
        }

        super.update(camera);
    }

    @Override
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void hurt(int healthDamage) {
        health -= healthDamage;
    }

    /**
     * using manhattan distance as heuristic funcytion. each path cost is one
     * @param player the target that monster moving toward
     * @room room room that player and monster are in
     * @return string representation of next action
     */

    public void face(Damageable damageable, Room room){
        Vector2D playerLoc = new Vector2D(Math.round(damageable.getPhysics().getPosition().getX() / 64)
                , Math.round(damageable.getPhysics().getPosition().getY() / 64));

        Vector2D monsterLoc = new Vector2D(Math.round(getPhysics().getPosition().getX() / 64)
                , Math.round(getPhysics().getPosition().getY() / 64));
        if( playerLoc.distanceSquared(monsterLoc) <= Math.pow(shootingRange / 2, 2) * 2){
            facing = "";
            return;
        }
        PriorityQueue<State> thePQ = new PriorityQueue<>(1, new Comparator<State>() {
            @Override
            public int compare(State o1, State o2) {
                if( o1.cost < o2.cost)  return -1;
                if( o1.cost > o2.cost)  return 1;
                return 0;
            }
        });
        LinkedList<Vector2D> visited = new LinkedList<>();
        LinkedList<String> path = new LinkedList<>();
        Map<Vector2D, Double> locAndCost = new HashMap<>();
        State currentState = new State( monsterLoc, path, 0);
        thePQ.add(currentState);
        locAndCost.put(monsterLoc, 0.0);
        while( !thePQ.isEmpty()){
            State popped = thePQ.poll();
            Vector2D current = new Vector2D(popped.state.getX(), popped.state.getY());
            LinkedList<String> currPath = popped.path;
            double cost = popped.cost;
            if( playerLoc.distanceSquared(current) <= 1){
                if(currPath.size() >= 1) {
                    String a = currPath.remove(0);
                    facing = a;
                    return;
                }
            }
            //possible state (action)
            ArrayList<Vector2D> successors = new ArrayList<>(
                    List.of(new Vector2D(1, 0),
                            new Vector2D(-1, 0),
                            new Vector2D(0, 1),
                            new Vector2D(0, -1))
            );
            //remove the action that will move the monster to the wall
            ArrayList<Vector2D> removeList = new ArrayList<>();
            for(Vector2D successor: successors){
                if(room.checkObstacle(current.add(successor).multiply(Main.TILE_HEIGHT))){
                    removeList.add(successor);
                }
            }
            successors.removeAll(removeList);
            LinkedList<String> successorPath;
            for( Vector2D successor: successors){
                successorPath = new LinkedList<>(currPath);
                Vector2D successorStateV = current.add(successor);
                double pathCost = cost + successorStateV.distanceSquared(playerLoc) - current.distanceSquared(playerLoc);
                if( successor.getX() > 0){
                    successorPath.add("D");
                } else if( successor.getX() < 0){
                    successorPath.add("A");
                } else if( successor.getY() > 0){
                    successorPath.add("S");
                } else if( successor.getY() < 0){
                    successorPath.add("W");
                }
                State successorState = new State(successorStateV, successorPath, pathCost);
                if( !visited.contains(successorStateV)){
                    thePQ.add(successorState);
                    visited.add(successorStateV);
                    locAndCost.put(successorStateV, pathCost);
                } else if(locAndCost.get(successorStateV) > pathCost){
                    thePQ.add(successorState);
                    locAndCost.put(successorStateV, pathCost);
                }
            }
        }
        facing = "";
        return;
    }

    private class State{
        public Vector2D state;
        public LinkedList<String> path;
        public double cost;
        public State(Vector2D inputState, LinkedList<String> inputPath, double pathCost){
            state = inputState;
            path = inputPath;
            cost = pathCost;
        }
    }

}
