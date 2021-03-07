package sample;



public class Monster extends Vector2D implements Physical{
    //monster needs a name too. give some respect to the monster :)
    public String name;
    private int demage;
    private PhysicsController physics;
    private Sprite sprite;


    public Monster(String inputName, int inputDemage, double inX, double inY){
        super(inX, inY);
        name = inputName;
        demage = inputDemage;
        sprite = new Sprite((int) inX, (int) inY, Main.PLAYER_WIDTH,
                Main.PLAYER_HEIGHT, Main.MONSTER_IMAGE);
    }


    public PhysicsController getPhysics() {
        return physics;
    }
    public Sprite getSprite() {
        return sprite;
    }
}
