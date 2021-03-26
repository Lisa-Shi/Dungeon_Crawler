package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Projectile extends GameObject implements Collideable {
    // Variables
    private int damage;
    private DynamicCollisionBox collisionBox;
    private Pane pane;
    private Room room;
    private int bouncesLeft;

    public Projectile(Room room, Pane pane, double initialX, double initialY, double centerX, double centerY, int damage) {
        super(room, initialX, initialY, centerX, centerY, Main.BULLET_SHEET);
        this.room = room;
        this.collisionBox = new DynamicCollisionBox(getPhysics(), new RectangleWireframe(Main.BULLET_WIDTH, Main.BULLET_HEIGHT), false);
        this.collisionBox.generate();
        this.damage = damage;
        this.pane = pane;
        this.bouncesLeft = Main.ENEMY_BULLET_BOUNCES_UNTIL_EXPIRATION;

        new Timeline(new KeyFrame(
                Duration.millis(10000),
                ae -> expire()))
                .play();
        ;
    }

    // Misc.
    public void launchTowardsPoint(Vector2D launchTowards, int vel) {
        getPhysics().setVelocity(getPhysics().getPosition().subtract(launchTowards).relen(-vel));
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
                if (c instanceof Damageable && !(c instanceof Monster)) {
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
