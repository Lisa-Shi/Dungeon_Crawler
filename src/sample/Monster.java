package sample;


public class Monster extends Vector2D implements Physical {
    //monster needs a name too. give some respect to the monster :)
    private String name;
    private int demage;
    private PhysicsController physics;
    private Sprite sprite;


    public Monster(String inputName, int inputDemage, double inX, double inY) {
        super(inX, inY);
        name = inputName;
        demage = inputDemage;
        sprite = new Sprite((int) inX, (int) inY, (int) Main.PLAYER_WIDTH,
                (int) Main.PLAYER_HEIGHT, Main.MONSTER_IMAGE);
    }


    @Override
    public void update(Camera camera) {

    }

    public PhysicsController getPhysics() {
        return physics;
    }
    public Sprite getSprite() {
        return sprite;
    }

    public String getName() {
        return name;
    }
}
