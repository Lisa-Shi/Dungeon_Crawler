/**
 * the bullet object
 */

package gameobjects.ProjectileLauncher;

import gameobjects.Damageable;
import gameobjects.GameObject;
import gameobjects.Player;
import gameobjects.monsters.Monster;
import gameobjects.physics.collisions.Collideable;
import gameobjects.physics.collisions.DynamicCollisionBox;
import gameobjects.physics.collisions.RectangleWireframe;
import gameobjects.graphics.functionality.ImageSheet;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import gameobjects.physics.PhysicsController;
import gameobjects.physics.Camera;
import main.Main;
import gamemap.Room;
import gameobjects.physics.Vector2D;

public class Projectile extends GameObject implements Collideable {
    // Variables
    private int damage;
    private DynamicCollisionBox collisionBox;
    private Pane pane;
    private Room room;
    private int bouncesLeft;
    private int timeRange;
    private GameObject sprite;

    /**constructor
     *
     * @param player player object who shoots the bullet
     * @param room the room in which player is
     * @param pane the overall pane
     * @param range a number between 1 - 5 determining range of weapon
     * @param damage determines harm inflicted, range between 1 - 5
     * @param img file name, ex: bullet.png
     */
    public Projectile(Player player, Room room, Pane pane, int range, int damage, ImageSheet img) {
        this(room, pane, player.getPhysics().getPosition().getX(),
                player.getPhysics().getPosition().getY(),
                (damage % 3 + 1) * 0.5, damage, img);
        sprite = player;
        //this.bouncesLeft = p.getWeaponList()[p.getHoldingWeapon()].getPower();
        range = range % 3;
        this.bouncesLeft = range + 1;
        this.timeRange = range * 5000;
    }

    /**
     * constructor
     *
     * @param monster monster who shoots the bullet
     * @param room the room in which the monster is
     * @param pane the overall pane
     */
    public Projectile(Monster monster, Room room, Pane pane) {
        this(room, pane, monster.getPhysics().getPosition().getX(),
                monster.getPhysics().getPosition().getY(), 0.5,
                Main.PLAYER_BULLET_DAMAGE, Main.MONSTER_BULLET_SHEET);
        sprite = monster;
        //this.bouncesLeft = p.getWeaponList()[p.getHoldingWeapon()].getPower();
        this.bouncesLeft = 2;
        this.timeRange = 10000;
    }

    private Projectile(Room room, Pane pane, double initialX,
                       double initialY, double scale, int damage, ImageSheet img) {
        super(room, initialX, initialY,
                Main.BULLET_WIDTH * scale, Main.BULLET_HEIGHT * scale, img);
        this.room = room;
        this.collisionBox =
                new DynamicCollisionBox(getPhysics(),
                        new RectangleWireframe(Main.BULLET_WIDTH, Main.BULLET_HEIGHT), false);
        this.collisionBox.generate();
        this.damage = damage;
        this.pane = pane;

        new Timeline(new KeyFrame(
                Duration.millis(timeRange),
                ae -> expire()))
                .play();
    }

    /**
     * aiming toward the giving position
     * @param launchTowards the target position
     * @param vel scale
     */
    // Misc.
    public void launchTowardsPoint(Vector2D launchTowards, int vel) {
        getPhysics().setVelocity(getPhysics().getPosition().subtract(launchTowards).relen(-vel));
    }

    /**
     * call to shoot the bullet
     */
    public void launch() {
        Player player = (Player) sprite;
        Vector2D direction = player.getDirection();
        PhysicsController physics = this.getPhysics();
        physics.setVelocity(direction.multiply(damage));
        physics.setPosition(
                sprite.getPhysics().getPosition().add(direction.multiply(Main.TILE_WIDTH / 2)));
    }

    /**
     * call when the bullet is being removed from the scene
     */
    public void expire() {
        pane.getChildren().remove(getGraphics().getSprite());
        room.remove(this);
    }


    @Override
    public void update(Camera camera) {
        super.update(camera, Main.ENEMY_BULLET_FRICTION_FORCE);
        for (Collideable c : room.getCollideables()) {
            if (collisionBox.collidedWith(c.getCollisionBox())) {

                boolean isTarget = false;

                if (sprite instanceof Player) {
                    isTarget = !(c instanceof Player);
                } else if (sprite instanceof Monster) {
                    isTarget = !(c instanceof Monster);
                }

                if (c instanceof Damageable && isTarget) {
                    ((Damageable) c).hurt(damage);
                    expire();
                    return;
                }

                if (c instanceof Projectile) {
                    expire();
                }

                if (c.getCollisionBox().isSolid()) {
                    bouncesLeft--;

                    if (bouncesLeft <= 0) {
                        expire();
                        return;
                    }

                    Vector2D collisionVel = getPhysics().getVelocity();
                    Vector2D normal =
                            collisionBox.calculateCollisionVector(c.getCollisionBox()).norm();

                    Vector2D reflected =
                            collisionVel.subtract(normal.multiply(collisionVel.dot(normal) * 2));

                    collisionBox.raytraceCollision(getPhysics(), room.getCollideables());

                    getPhysics().setVelocity(reflected);
                }
            }
        }
    }

    @Override
    public DynamicCollisionBox getCollisionBox() {
        return collisionBox;
    }
}