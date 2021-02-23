package sample;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Player {
    private int x;
    private int y;
    private String name;
    private List<Weapon> weaponList;
    //index of weapon in the weapon list
    private int holdingWeapon;
    private boolean death;

    public Player(String inputName, Weapon initialWeapon, int initialX, int initialY){
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

    public int getHoldingWeapon() {
        return holdingWeapon;
    }
}
