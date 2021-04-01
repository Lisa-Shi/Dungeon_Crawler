package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.List;

public class Projectile extends GameObject implements Collideable {
    // Variables
    private int damage;
    private DynamicCollisionBox collisionBox;
    private Pane pane;
    private Room room;
    private int bouncesLeft;
    private boolean isPlayer;
    private GameObject sprite;
    //private ImageSheet img;
    //private double scale;
    public Projectile(Player player, Room room, Pane pane) {
        this(room, pane, player.getPhysics().getPosition().getX(), player.getPhysics().getPosition().getY(),
                0.5, Main.PLAYER_BULLET_DAMAGE, Main.PLAYER_BULLET_SHEET);
        sprite = player;
        //this.bouncesLeft = p.getWeaponList()[p.getHoldingWeapon()].getPower();
        this.bouncesLeft = 2;
    }
    public Projectile(Monster monster, Room room, Pane pane) {
        this(room, pane, monster.getPhysics().getPosition().getX(), monster.getPhysics().getPosition().getY(),
                0.5, Main.PLAYER_BULLET_DAMAGE, Main.MONSTER_BULLET_SHEET);
        sprite = monster;
        //this.bouncesLeft = p.getWeaponList()[p.getHoldingWeapon()].getPower();
        this.bouncesLeft = 2;
    }

    private Projectile(Room room, Pane pane, double initialX, double initialY, double scale, int damage, ImageSheet img) {
        super(room, initialX, initialY,  Main.BULLET_WIDTH * scale, Main.BULLET_HEIGHT * scale, img);
        this.room = room;
        this.collisionBox = new DynamicCollisionBox(getPhysics(), new RectangleWireframe(Main.BULLET_WIDTH, Main.BULLET_HEIGHT), false);
        this.collisionBox.generate();
        this.damage = damage;
        this.pane = pane;

        new Timeline(new KeyFrame(
                Duration.millis(10000),
                ae -> expire()))
                .play();
    }

    // Misc.
    public void launchTowardsPoint(Vector2D launchTowards, int vel) {
        getPhysics().setVelocity(getPhysics().getPosition().subtract(launchTowards).relen(-vel));
    }

    public void launch() {
        Player player = (Player) sprite;
        Vector2D direction = player.getDirection();
        PhysicsController physics = this.getPhysics();
        int vel = 10;
        physics.setVelocity(direction.multiply(vel));
        physics.setPosition(sprite.getPhysics().getPosition().add(direction.multiply(Main.TILE_WIDTH / 2)));

    }
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

                if (c.getCollisionBox().isSolid()) {
                    bouncesLeft--;

                    if (bouncesLeft <= 0) {
                        expire();
                        return;
                    }

                    Vector2D collisionVel = getPhysics().getVelocity();
                    Vector2D normal = collisionBox.calculateCollisionVector(c.getCollisionBox()).norm();

                    Vector2D reflected = collisionVel.subtract(normal.multiply(collisionVel.dot(normal) * 2));

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
