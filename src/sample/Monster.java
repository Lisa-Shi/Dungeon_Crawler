package sample;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Monster implements Physical{
    //monster needs a name too. give some respect to the monster :)
    @SerializedName("name")
    public String name;
    @SerializedName("demage")
    private int demage;
    private PhysicsController physics;
    @SerializedName("x")
    private int x;
    @SerializedName("y")
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
