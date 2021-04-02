/**
 * the monster class
 *
 */
package gameobjects.monsters;

import gameobjects.Damageable;
import gameobjects.GameObject;
import gameobjects.HPBar;
import gameobjects.Player;
import gameobjects.Projectile;
import gameobjects.physics.collisions.Collideable;
import gameobjects.physics.collisions.CollisionBox;
import gameobjects.physics.collisions.DynamicCollisionBox;
import gameobjects.physics.collisions.RectangleWireframe;
import gameobjects.graphics.functionality.DirectionalImageSheet;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import gameobjects.physics.Camera;
import gameobjects.graphics.functionality.Drawable;
import main.Main;
import gamemap.Room;
import gameobjects.physics.Vector2D;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class Monster extends GameObject implements Damageable, Collideable, Drawable {
    // Variables
    private int maxHealth;
    private int health;
    private int damagePerHit;
    private Room room;
    private DynamicCollisionBox collisionBox;
    private boolean isDead = false;
    private String facing;
    private DirectionalImageSheet sheet;
    private HPBar hpBar;
    private PropertyChangeSupport support;
    
    /**
     * constructor
     * @param room the room which the monster locates
     * @param maxHealth the maximun health of the monster
     * @param health the current health of the monster. always less than maxHealth
     * @param damagePerHit damage caused by the monster
     * @param initialX initial X position
     * @param initialY initial Y position
     * @param sheet gameobjects.graphics.image source for the monster
     */
    public Monster(Room room, int maxHealth, int health,
                   int damagePerHit, double initialX,
                   double initialY, DirectionalImageSheet sheet) {
        super(room, initialX * Main.TILE_WIDTH, initialY * Main.TILE_HEIGHT,
                Main.MONSTER_WIDTH / 2, Main.MONSTER_HEIGHT / 2, sheet);
        this.maxHealth = maxHealth;
        this.room = room;
        this.sheet = sheet;
        if (this.health > maxHealth) {
            this.health = maxHealth;
        } else {
            this.health = health;
        }
        this.damagePerHit = damagePerHit;

        this.collisionBox = new DynamicCollisionBox(getPhysics(),
                new RectangleWireframe(Main.MONSTER_WIDTH, Main.MONSTER_HEIGHT), false);
        this.collisionBox.generate();
        this.facing = "A";
        this.support = new PropertyChangeSupport(this);
    }

    /**
     * emmit bullet toward player
     * @param room the room which the monster locats
     * @param pane overall pane
     * @param player player
     */
    public void launchProjectileTowardsPlayer(Room room, Pane pane, Player player) {
        Projectile bullet = new Projectile(this, room, pane);

        //room, pane, getPhysics().getPosition().getX(), getPhysics().getPosition().getY(),
        //Main.BULLET_WIDTH/2, Main.BULLET_HEIGHT/2, Main.ENEMY_BULLET_DAMAGE);
        room.add(bullet);
        pane.getChildren().add(bullet.getGraphics().getSprite());
        bullet.launchTowardsPoint(player.getPhysics().getPosition(), Main.ENEMY_BULLET_SPEED);
    }

    /**
     * add the hp bar to the pane
     * @param room the room which the monster locates
     * @param pane overall pane
     */
    public void addHPBar(Room room, Pane pane) {
        hpBar = new HPBar(this, room, pane);
        addPropertyChangeListener(hpBar);
        room.add(hpBar);
        pane.getChildren().add(hpBar.getGraphics().getSprite());
        pane.getChildren().add(hpBar.getOutter());
        pane.getChildren().add(hpBar.getInner());
    }
    public String getFacing() {
        return facing;
    }

    /**
     * the monster positiona and hp bar position are updated here
     * @param camera camera that focuses on object
     */
    @Override
    public void update(Camera camera) {
        if (hpBar != null) {
            switch (facing) {
            case "A":
                hpBar.getPhysics().pushLeft(Main.ENEMY_CONTROL_FORCE);
                getPhysics().pushLeft(Main.ENEMY_CONTROL_FORCE);
                break;
            case "D":
                hpBar.getPhysics().pushRight(Main.ENEMY_CONTROL_FORCE);
                getPhysics().pushRight(Main.ENEMY_CONTROL_FORCE);
                break;
            case "W":
                hpBar.getPhysics().pushUp(Main.ENEMY_CONTROL_FORCE);
                getPhysics().pushUp(Main.ENEMY_CONTROL_FORCE);
                break;
            case "S":
                hpBar.getPhysics().pushDown(Main.ENEMY_CONTROL_FORCE);
                getPhysics().pushDown(Main.ENEMY_CONTROL_FORCE);
                break;
            default:
                break;
            }
        }
        super.update(camera);
    }

    /**
     * decrease the health of the monster
     * health bar width changes here as well
     * @param healthDamage the amount of damage to deduct from the monster
     */
    @Override
    public void hurt(int healthDamage) {
        support.firePropertyChange("health", (double) this.health / this.maxHealth,
                (this.health - healthDamage) / (double) this.maxHealth);
        health -= healthDamage;
        if (health <= 0) {
            isDead = true;
            facing = "";
            this.collisionBox.setSolid(false);
        }
    }

    /**
     * get the monster location relative to the scene
     * @return monster location relative to the scene
     */
    public Vector2D getLocalToScenePosition() {
        Bounds bounds = this.getGraphics().getSprite().localToScene(
                this.getGraphics().getSprite().getBoundsInLocal());
        double centerX = bounds.getMinX() + bounds.getWidth() / 2.0;
        double centerY = bounds.getMinY() + bounds.getHeight() / 2.0;
        return new Vector2D(centerX, centerY);
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

    public HPBar getHPBar() {
        return hpBar;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
    /**
     * using manhattan distance as heuristic funcytion. each path cost is one
     * @room room room that player and monster are in
     * @param damageable the target that monster moving toward
     * @param room room that player and monster are in
     */

    public void face(Damageable damageable, Room room) {
        Vector2D playerLoc =
                damageable.getPhysics().getPosition().multiply(1.0 / Main.TILE_HEIGHT).round();
        Vector2D monsterLoc = getPhysics().getPosition().multiply(1.0 / Main.TILE_HEIGHT).round();
        if (playerLoc.distanceSquared(monsterLoc) <= 1) {
            facing = "";
            return;
        }
        PriorityQueue<State> thePQ = new PriorityQueue<>(1, new Comparator<State>() {
            @Override
            public int compare(State o1, State o2) {
                if (o1.cost < o2.cost) {
                    return -1;
                }
                if (o1.cost > o2.cost) {
                    return 1;
                }
                return 0;
            }
        });
        LinkedList<Vector2D> visited = new LinkedList<>();
        LinkedList<String> path = new LinkedList<>();
        Map<Vector2D, Double> locAndCost = new HashMap<>();
        State currentState = new State(monsterLoc, path, 0);
        thePQ.add(currentState);
        locAndCost.put(monsterLoc, 0.0);
        while (!thePQ.isEmpty()) {
            State popped = thePQ.poll();
            Vector2D current = new Vector2D(popped.getState().getX(), popped.getState().getY());
            LinkedList<String> currPath = popped.getPath();
            double cost = popped.getCost();
            if (playerLoc.distanceSquared(current) <= 1) {
                if (currPath.size() >= 1) {
                    String a = currPath.remove(0);
                    facing = a;
                    return;
                }
            }
            ArrayList<Vector2D> successors = new ArrayList() {
                {
                    add(new Vector2D(1, 0));
                    add(new Vector2D(-1, 0));
                    add(new Vector2D(0, 1));
                    add(new Vector2D(0, -1));
                }
            };

            ArrayList<Vector2D> removeList = new ArrayList<>();
            for (Vector2D successor: successors) {
                current = current.add(successor);
                for (Collideable collideable: room.getCollideables()) {
                    if (collideable.getCollisionBox().containsPoint(current)) {
                        removeList.add(successor);
                    }
                }
                current = current.subtract(successor);
            }
            successors.removeAll(removeList);
            LinkedList<String> successorPath;
            for (Vector2D successor: successors) {
                successorPath = new LinkedList<>(currPath);
                Vector2D successorStateV = current.add(successor);
                double pathCost = cost
                        + successorStateV.distanceSquared(playerLoc)
                        - current.distanceSquared(playerLoc);
                if (successor.getX() > 0) {
                    successorPath.add("D");
                } else if (successor.getX() < 0) {
                    successorPath.add("A");
                } else if (successor.getY() > 0) {
                    successorPath.add("S");
                } else if (successor.getY() < 0) {
                    successorPath.add("W");
                }
                State successorState = new State(successorStateV, successorPath, pathCost);
                if (!visited.contains(successorStateV)) {
                    thePQ.add(successorState);
                    visited.add(successorStateV);
                    locAndCost.put(successorStateV, pathCost);
                } else if (locAndCost.get(successorStateV) > pathCost) {
                    thePQ.add(successorState);
                    locAndCost.put(successorStateV, pathCost);
                }
            }
        }
        facing = "";
        return;
    }

    private class State {
        private Vector2D state;
        private LinkedList<String> path;
        private double cost;
        public State(Vector2D inputState, LinkedList<String> inputPath, double pathCost) {
            state = inputState;
            path = inputPath;
            cost = pathCost;
        }

        public double getCost() {
            return cost;
        }

        public LinkedList<String> getPath() {
            return path;
        }

        public Vector2D getState() {
            return state;
        }
    }
}
