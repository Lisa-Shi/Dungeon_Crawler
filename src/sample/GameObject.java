package sample;

import javafx.scene.image.Image;

public abstract class GameObject implements Physical, Drawable {
    // Variables
    private PhysicsControllerRelative physics;
    private Sprite sprite;
    private Vector2D center;

    // Constructors
    public GameObject(Room room, double initialX, double initialY,
                      double centerX, double centerY, String spriteName) {
        this.physics = new PhysicsControllerRelative(initialX, initialY, room.getPhysics());
        this.sprite = new Sprite((int) initialX, (int) initialY,
                (int) (centerX * 2), (int) (centerY * 2),
                new Image(getClass().getResource(spriteName).toExternalForm()));
        this.center = new Vector2D(centerX, centerY);
    }

    public void update(Camera camera) {
        physics.update();
        sprite.setTranslateX(physics.getPosition().getX() - camera.getPhysics().getPosition().getX()
                + camera.getOffsetX() - center.getX());
        sprite.setTranslateY(physics.getPosition().getY() - camera.getPhysics().getPosition().getY()
                + camera.getOffsetY() - center.getY());
    }

    // Getters
    @Override
    public PhysicsController getPhysics() {
        return physics;
    }
    public Sprite getSprite() {
        return sprite;
    }
}
