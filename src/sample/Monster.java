package sample;

/**
 * the monster class
 */
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.*;

public class Monster extends GameObject implements Damageable, Collideable, Drawable {
    // no

    // Variables
    private int health;
    private int damagePerHit;
    private Room room;
    private DynamicCollisionBox collisionBox;
    private boolean isDead = false;
    private String facing;
    private DirectionalImageSheet sheet;

    public Monster(Room room, int health, int damagePerHit, double initialX, double initialY, DirectionalImageSheet sheet) {
        super(room, initialX * Main.TILE_WIDTH, initialY * Main.TILE_HEIGHT
                    , Main.MONSTER_WIDTH / 2, Main.MONSTER_HEIGHT / 2, sheet);
        this.room = room;
        this.sheet = sheet;
        this.health = health;
        this.damagePerHit = damagePerHit;

        this.collisionBox = new DynamicCollisionBox(getPhysics(),
                new RectangleWireframe(Main.MONSTER_WIDTH, Main.MONSTER_HEIGHT), true);
        this.collisionBox.generate();

        this.facing = "A";
    }

    public void launchProjectileTowardsPlayer(Room room, Pane pane, Player player) {
        Projectile bullet = new Projectile(this, room, pane);

//                room, pane, getPhysics().getPosition().getX(), getPhysics().getPosition().getY(),
//                Main.BULLET_WIDTH/2, Main.BULLET_HEIGHT/2, Main.ENEMY_BULLET_DAMAGE);
        room.add(bullet);
        pane.getChildren().add(bullet.getGraphics().getSprite());
        bullet.launchTowardsPoint(player.getPhysics().getPosition(), Main.ENEMY_BULLET_SPEED);
    }

    public String getFacing() {
        return facing;
    }

    @Override
    public void update(Camera camera) {
        switch (facing) {
            case "A":
                getPhysics().pushLeft(Main.ENEMY_CONTROL_FORCE);
                break;
            case "D":
                getPhysics().pushRight(Main.ENEMY_CONTROL_FORCE);
                break;
            case "W":
                getPhysics().pushUp(Main.ENEMY_CONTROL_FORCE);
                break;
            case "S":
                getPhysics().pushDown(Main.ENEMY_CONTROL_FORCE);
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

    public boolean isDead() {
        return isDead;
    }

    @Override
    public void hurt(int healthDamage) {
        health -= healthDamage;
        if( health <= 0){
            isDead = true;
            facing = "";
            this.collisionBox.setSolid(false);
        }
    }

    /**
     * using manhattan distance as heuristic funcytion. each path cost is one
     * @room room room that player and monster are in
     * @param damageable the target that monster moving toward
     * @param room room that player and monster are in
     * @return string representation of next action
     */

    public void face(Damageable damageable, Room room){
        Vector2D playerLoc = damageable.getPhysics().getPosition().multiply(1.0/Main.TILE_HEIGHT).round();
        Vector2D monsterLoc = getPhysics().getPosition().multiply(1.0/Main.TILE_HEIGHT).round();
        if( playerLoc.distanceSquared(monsterLoc) <= 1){
            facing = "";
            return ;
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
            ArrayList<Vector2D> successors = new ArrayList<>(
                    List.of(new Vector2D(1, 0),
                            new Vector2D(-1, 0),
                            new Vector2D(0, 1),
                            new Vector2D(0, -1))
            );
            ArrayList<Vector2D> removeList = new ArrayList<>();
            for(Vector2D successor: successors){
                current = current.add(successor);
                for( Collideable collideable: room.getCollideables()) {
                    if( collideable.getCollisionBox().containsPoint(current)){
                        removeList.add(successor);
                    }
                }
                current = current.subtract(successor);
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
        return ;
    }

    private class State {
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
