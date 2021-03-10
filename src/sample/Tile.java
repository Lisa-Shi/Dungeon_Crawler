package sample;

import javafx.scene.image.Image;

public class Tile implements Physical, Drawable {
    // Variables
    private PhysicsControllerRelative physics;
    private Sprite sprite;

    // Constructors
    public Tile(Room room, double initialX, double initialY) {
        double tileX = (int) (initialX * Main.TILE_WIDTH);
        double tileY = (int) (initialY * Main.TILE_HEIGHT);

        this.physics = new PhysicsControllerRelative(tileX, tileY, room.getPhysics());
        this.sprite = new Sprite((int) tileX, (int) tileY, Main.TILE_WIDTH, Main.TILE_HEIGHT,
                new Image(getClass().getResource("spr_dungeon_tile.png").toExternalForm()));
    }

    public void update(Camera camera) {
        physics.update();
        sprite.setTranslateX(physics.getPosition().getX() - camera.getPhysics().getPosition().getX()
                + camera.getOffsetX() - Main.TILE_WIDTH / 2);
        sprite.setTranslateY(physics.getPosition().getY() - camera.getPhysics().getPosition().getY()
                + camera.getOffsetY() - Main.TILE_HEIGHT / 2);
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
