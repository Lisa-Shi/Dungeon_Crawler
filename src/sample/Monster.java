package sample;


import java.util.List;

public class Monster implements Physical{
    //monster needs a name too. give some respect to the monster :)
    public String name;
    private int demage;
    private PhysicsController physics;
    private int x;
    private int y;
    private Sprite sprite;

    public Monster(String inputName, int inputDemage, int inX, int inY){
        name = inputName;
        demage = inputDemage;
        x = inX;
        y = inY;
        sprite = new Sprite((int) x, (int) y, Main.PLAYER_WIDTH,
                Main.PLAYER_HEIGHT, Main.MONSTER_IMAGE);
    }


    public PhysicsController getPhysics() {
        return physics;
    }
    public Sprite getSprite() {
        return sprite;
    }
    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
