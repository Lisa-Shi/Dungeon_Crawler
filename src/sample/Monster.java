package sample;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;



public class Monster extends GameObject implements Physical, Collideable, Drawable {
    //monster needs a name too. give some respect to the monster :)
    private String name;
    private int hp;
    private int demage;
    private Sprite sprite;
    private DynamicCollisionBox collisionBox;
    private boolean isDead = false;
    private long lastAttack = System.nanoTime();
    private Timeline timeline = new Timeline();
    private KeyFrame frame;
    private DynamicCollisionBox attackRange =
            new DynamicCollisionBox(getPhysics(),
                    new RectangleWireframe(3 * Main.TILE_WIDTH, 3 * Main.TILE_HEIGHT));
    private int imageindex = 0;
    public Monster(Room room, String inputName, int hp, int inputDemage, double initialX, double initialY) {
        super(room, initialX * Main.TILE_WIDTH, initialY * Main.TILE_HEIGHT
                , Main.MONSTER_WIDTH / 2, Main.MONSTER_HEIGHT / 2,  Main.MONSTER_IMAGE.get(0));
        name = inputName;
        this.hp = hp;
        demage = inputDemage;
        sprite = new Sprite((int) initialX, (int) initialY, (int) Main.MONSTER_WIDTH,
                (int) Main.MONSTER_HEIGHT, Main.MONSTER_IMAGE.get(0));
        this.collisionBox = new DynamicCollisionBox(getPhysics(),
                new RectangleWireframe(Main.MONSTER_WIDTH, Main.MONSTER_HEIGHT));
        this.collisionBox.generate();
        attackRange.generate();
        frame = new KeyFrame(Duration.millis(50), e -> {
            sprite.setImage(Main.MONSTER_IMAGE.get((imageindex++) % 2));
        });
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    public void playerInRange(Player player){
        //attack every 1 second
        if( (System.nanoTime() - lastAttack >= 1000000000)
                && attackRange.containsPoint(player.getCollisionBox().getPhysics().getAbsolutePosition())){
            lastAttack = System.nanoTime();
            player.hurt(demage);
            System.out.println("attacked " + player);
    }
    }

    @Override
    public void update(Camera camera) {
        if( hp <= 0){
            isDead = true;
            collisionBox.setSolid(false);
            getSprite().setImage(Main.CHEST_IMAGE.get(0));
        }
        getPhysics().update();
        updateSprite(camera);
    }
    private void updateSprite(Camera camera) {
        sprite.setTranslateX(getPhysics().getPosition().getX() - camera.getPhysics().getPosition().getX()
                + camera.getOffsetX() - Main.MONSTER_WIDTH / 2);
        sprite.setTranslateY(getPhysics().getPosition().getY() - camera.getPhysics().getPosition().getY()
                + camera.getOffsetY() - Main.MONSTER_HEIGHT / 2);
    }
    @Override
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean collideableEqual(Object other) {
        if( other instanceof Collideable){
            if (((Collideable) other).getCollisionBox().equals(this.collisionBox)){
                return true;
            }
        }
        return false;
    }
}
