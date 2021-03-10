package sample;

import javafx.scene.image.Image;

public class Exit implements Physical, Collideable, Drawable {
    // Variables
    private PhysicsControllerRelative physics;
    private Room linkedRoom;
    private CollisionBox collisionBox;
    private Sprite sprite;

    // Constructors
    public Exit(Room inRoom, double initialX, double initialY, Room linkedRoom) {
        this.linkedRoom = linkedRoom;

        this.physics = new PhysicsControllerRelative(initialX * Main.TILE_WIDTH, initialY * Main.TILE_HEIGHT, inRoom.getPhysics());
        this.collisionBox = new CollisionBoxRectangle(physics, Main.TILE_WIDTH, Main.TILE_HEIGHT);

        this.sprite = new Sprite((int) initialX * Main.TILE_WIDTH, (int) initialY * Main.TILE_HEIGHT, Main.TILE_WIDTH, Main.TILE_HEIGHT,
                new Image(getClass().getResource("spr_dungeon_exit.png").toExternalForm()));
    }

    // Methods
    public void update(Camera camera) {
        physics.update();
        sprite.setTranslateX(physics.getPosition().getX() - camera.getPhysics().getPosition().getX()
                + camera.getOffsetX() - Main.TILE_WIDTH / 2);
        sprite.setTranslateY(physics.getPosition().getY() - camera.getPhysics().getPosition().getY()
                + camera.getOffsetY() - Main.TILE_HEIGHT / 2);
    }

    // Getters
    public Room getLinkedRoom() {
        return linkedRoom;
    }

    @Override
    public PhysicsController getPhysics() {
        return physics;
    }

    @Override
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }
}
