package sample;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Player {
    private int x;
    private int y;
    private String name;
    private List<Weapon> weaponList;
<<<<<<< Updated upstream
    //index of weapon in the weapon list
    private int holdingWeapon;
    private boolean death;

    public Player(String inputName, Weapon initialWeapon, int initialX, int initialY){
=======
    private int difficulty;
    private int holdingWeapon; // index of weapon in the weapon list

    private PhysicsController physics;
    private Sprite sprite;


    public Player(String name, Weapon initialWeapon, int difficulty) {
        this(name, initialWeapon, 0,0);
        this.difficulty = difficulty;
    }
  
    public Player(String inputName, Weapon initialWeapon, double initialX, double initialY) {
>>>>>>> Stashed changes
        name = inputName;
        weaponList = new ArrayList<>();
        weaponList.add(initialWeapon);
        x = initialX;
        y = initialY;
        death = false;
    }

    public void move(int deltaX, deltaY){
        x += deltaX;
        y += deltaY;
    }

    public void obtainNewWeapon(Weapon newWeapon){
        weaponList.add(newWeapon);
    }

    public void equipWeapon(Weapon target){
        holdingWeapon = weaponList.indexOf(target);
    }

    public List<Weapon> getWeaponList() {
        return weaponList;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

<<<<<<< Updated upstream
    public int getHoldingWeapon() {
        return holdingWeapon;
=======
    public String getName(){
        return name;
    }

    public int getDifficulty(){ return difficulty;  }

    public boolean isLegal(){
        if( name.equals("") || difficulty == -1 ){
            return false;
        }
        return true;
>>>>>>> Stashed changes
    }
}
