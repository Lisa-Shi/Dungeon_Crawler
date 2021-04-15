/**
 * the hp bar that shows monster health point.
 * it is essentially two rectangle
 * Term in JavaDoc
 *      parent: the monster that this hp bar belongs to
 */
package gameobjects;

import gamemap.Room;
import gameobjects.graphics.functionality.ImageSheet;
import gameobjects.graphics.functionality.SingularImageSheet;
import gameobjects.monsters.Monster;
import gameobjects.physics.Vector2D;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;
import gameobjects.physics.Camera;
import main.Main;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class HPBar extends GameObject implements PropertyChangeListener {
    // Variables
    private double health;
    private Pane pane;
    private Room room;
    private Rectangle inner;
    private Rectangle outter;
    private Monster sprite;

    /**
     * constructor
     * @param monster parent
     * @param room the room which the parent is in
     * @param pane the game pane
     */
    public HPBar(Monster monster, Room room, Pane pane) {
        this(room, pane, monster.getPhysics().getPosition().getX(),
                monster.getPhysics().getPosition().getY(),
                0.5, Main.PLAYER_BULLET_DAMAGE,
                new SingularImageSheet(
                        Main.getImageFrom("../gameobjects/graphics/sprites/transparent.png")
                ));
        this.sprite = monster;
        this.getPhysics().setPosition(sprite.getPhysics().getPosition());
        this.getPhysics().setAcceleration(sprite.getPhysics().getAcceleration());
        this.getPhysics().setVelocity(sprite.getPhysics().getVelocity());
        inner = new Rectangle(this.getPhysics().getPosition().getX(),
                this.getPhysics().getPosition().getY(), 27, 4);
        inner.setFill(Color.BLUE);
        outter = new Rectangle(this.getPhysics().getPosition().getX(),
                this.getPhysics().getPosition().getY(), 30, 7);
        outter.setFill(Color.WHITE);
        outter.setStroke(Color.BLACK);
        outter.setStrokeType(StrokeType.OUTSIDE);
        outter.setStrokeWidth(1.5);
    }

    private HPBar(Room room, Pane pane, double initialX,
                  double initialY, double scale, int health, ImageSheet img) {
        super(room, initialX, initialY,  30, 10, img);
        this.room = room;
        this.health = health;
        this.pane = pane;
        new Timeline(new KeyFrame(
            Duration.INDEFINITE,
            ae -> expire()))
            .play();
    }

    /**
     * the HP bar expires and disappears from the scene when the parent dies
     */
    public void expire() {
        pane.getChildren().remove(getGraphics().getSprite());
        pane.getChildren().remove(inner);
        pane.getChildren().remove(outter);
        room.remove(this);
    }
    public void update(Camera camera) {
        Vector2D monsterPositionToScene = sprite.getLocalToScenePosition();
        super.update(camera, Main.DEFAULT_FRICTIONAL_FORCE);
        outter.setX(monsterPositionToScene.getX() - 15);
        outter.setY(monsterPositionToScene.getY() - 50);
        inner.setX(monsterPositionToScene.getX() - 13.5);
        inner.setY(monsterPositionToScene.getY() - 48.5);
    }

    /**
     * the weidth of the inner rectangle changes when the parent's health changes.
     * @param evt event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.setHealth((double) evt.getNewValue());
        pane.getChildren().remove(inner);
        inner.setWidth(this.health * 27);
        pane.getChildren().add(inner);
    }
    public void setHealth(double health) {
        this.health = health;
    }

    public Rectangle getOutter() {
        return outter;
    }

    public Rectangle getInner() {
        return inner;
    }
}
