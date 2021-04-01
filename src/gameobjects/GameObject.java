/**parent class for most of the objects in the game
 *
 */
package gameobjects;

import gameobjects.graphics.functionality.ImageSheet;
import gameobjects.graphics.functionality.Sprite;
import gameobjects.graphics.functionality.SpriteController;
import gameobjects.physics.collisions.Physical;
import gameobjects.physics.PhysicsController;
import gameobjects.physics.PhysicsControllerRelative;
import gameobjects.physics.Camera;
import gameobjects.graphics.functionality.Drawable;
import screens.Main;
import gamemap.Room;
import gameobjects.physics.Vector2D;

public abstract class GameObject implements Physical, Drawable {
    // Variables
    private PhysicsControllerRelative physics;
    private SpriteController graphics;
    private ImageSheet sheet;
    private Room room;
    private Vector2D center;

    // Constructors
    public GameObject(Room room, double initialX, double initialY,
                      double centerX, double centerY, ImageSheet sheet) {
        this.physics = new PhysicsControllerRelative(initialX, initialY, room.getPhysics());
        Sprite sprite = new Sprite((int) initialX,
                (int) initialY, (int) (centerX * 2),
                (int) (centerY * 2), Main.WALLTILE); // walltile = default no texture
        this.room = room;
        this.graphics = new SpriteController(sprite, sheet.getInitialReel());
        this.center = new Vector2D(centerX, centerY);
        this.sheet = sheet;
    }

    public void update(Camera camera) {
        update(camera, Main.DEFAULT_FRICTIONAL_FORCE);
    }

    public void update(Camera camera, double frictionalForce) {
        physics.update(frictionalForce);
        graphics.getSprite().setTranslateX(
                physics.getPosition().getX() - camera.getPhysics().getPosition().getX()
                + camera.getOffsetX() - center.getX());
        graphics.getSprite().setTranslateY(
                physics.getPosition().getY() - camera.getPhysics().getPosition().getY()
                + camera.getOffsetY() - center.getY());
    }

    // Getters
    @Override
    public PhysicsController getPhysics() {
        return physics;
    }
    public PhysicsControllerRelative getPhysicsRel() {
        return physics;
    }
    @Override
    public SpriteController getGraphics() {
        return graphics;
    }
    @Override
    public ImageSheet getSpriteSheet() {
        return sheet;
    }

}
