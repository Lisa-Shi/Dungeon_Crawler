package sample;

import javafx.scene.image.Image;

public abstract class GameObject implements Physical, Drawable {
    // Variables
    private PhysicsControllerRelative physics;
    private SpriteController graphics;
    private ImageSheet sheet;

    private Vector2D center;

    // Constructors
    public GameObject(Room room, double initialX, double initialY,
                      double centerX, double centerY, ImageSheet sheet) {
        this.physics = new PhysicsControllerRelative(initialX, initialY, room.getPhysics());
        Sprite sprite = new Sprite((int) initialX, (int) initialY,
                (int) (centerX * 2), (int) (centerY * 2), Main.WALLTILE); // walltile = default no texture

        this.graphics = new SpriteController(sprite, sheet.getInitialReel());
        this.center = new Vector2D(centerX, centerY);
    }

    public void update(Camera camera) {
        physics.update();
        graphics.getSprite().setTranslateX(physics.getPosition().getX() - camera.getPhysics().getPosition().getX()
                + camera.getOffsetX() - center.getX());
        graphics.getSprite().setTranslateY(physics.getPosition().getY() - camera.getPhysics().getPosition().getY()
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
