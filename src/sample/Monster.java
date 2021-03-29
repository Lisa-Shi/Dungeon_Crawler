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

    private int shootingRange = 3;
    private CollisionBox attackRange =
            new CollisionBox(getPhysics(),
                    new RectangleWireframe(shootingRange * Main.TILE_WIDTH, shootingRange * Main.TILE_HEIGHT), false);
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
                new RectangleWireframe(Main.MONSTER_WIDTH, Main.MONSTER_HEIGHT), false);
        this.collisionBox.generate();
        this.attackRange.generate();

        this.facing = "A";
    }

    public void launchProjectileTowardsPlayer(Room room, Pane pane, Player player) {
        Projectile bullet = new Projectile(room, pane, getPhysics().getPosition().getX(), getPhysics().getPosition().getY(),
                Main.BULLET_WIDTH/2, Main.BULLET_HEIGHT/2, Main.ENEMY_BULLET_DAMAGE);
        room.add(bullet);
        pane.getChildren().add(bullet.getGraphics().getSprite());
        bullet.launchTowardsPoint(player.getPhysics().getPosition(), Main.ENEMY_BULLET_SPEED);
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
        }
    }
    /**
     * using manhattan distance as heuristic funcytion. each path cost is one
     * @room room room that player and monster are in
     * @return string representation of next action
     */


    public void face(Damageable damageable, Room room){
        Vector2D playerLoc = damageable.getPhysics().getPosition().round().multiply(1/Main.MONSTER_HEIGHT);
        Vector2D monsterLoc = getPhysics().getPosition().round().multiply(1/Main.MONSTER_HEIGHT);
        if( playerLoc.distanceSquared(monsterLoc) <= 3){
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
        while( !thePQ.isEmpty() ){
            State popped = thePQ.poll();
            Vector2D current = popped.state;
            LinkedList<String> currPath = popped.path;
            double cost = popped.cost;
            if( playerLoc.distanceSquared(current) <= 4){
                if(currPath.size() >= 1) {
                    String a = currPath.remove(0);
                    facing = a;
                    return;
                }
            }
            //possible state (action)
            ArrayList<Vector2D> successors = new ArrayList<Vector2D>() {{
                add(new Vector2D(1, 0));
                add(new Vector2D(-1, 0));
                add(new Vector2D(0, 1));
                add(new Vector2D(0, -1));
            }};
            //remove the action that will move the monster to the wall
            ArrayList<Vector2D> removeList = new ArrayList<>();
            for(Vector2D successor: successors){
                current = current.add(successor);
                WallTile temp = new WallTile(this.room, (int)current.getX(), (int)current.getY());
                if(room.checkObstacle(temp)){
                    removeList.add(successor);
                }

            }
            successors.removeAll(removeList);
            LinkedList<String> successorPath;
            for( Vector2D successor: successors){
                successorPath = new LinkedList<>(currPath);
                Vector2D successorPosition = current.add(successor);
                double pathCost = cost + successorPosition.distanceSquared(playerLoc) - current.distanceSquared(playerLoc);
                if( successor.getX() > 0){
                    successorPath.add("D");
                } else if( successor.getX() < 0){
                    successorPath.add("A");
                } else if( successor.getY() > 0){
                    successorPath.add("S");
                } else if( successor.getY() < 0){
                    successorPath.add("W");
                }
                State newSuccessor = new State(successorPosition, successorPath, pathCost);
                if( !visited.contains(successorPosition)){
                    thePQ.add(newSuccessor);
                    visited.add(successorPosition);
                    locAndCost.put(successorPosition, pathCost);
                } else if(locAndCost.get(successorPosition) > pathCost){
                    thePQ.add(newSuccessor);
                    locAndCost.put(successorPosition, pathCost);
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
