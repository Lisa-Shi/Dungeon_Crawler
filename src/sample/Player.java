package sample;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Player {

    // Variables
    private String name;
    private List<Weapon> weaponList;
    private int holdingWeapon; // index of weapon in the weapon list

    private PhysicsController physics;
    private Sprite sprite;

    public Player(String inputName, Weapon initialWeapon, int initialX, int initialY) {
        name = inputName;
        weaponList = new ArrayList<>();
        weaponList.add(initialWeapon);
        sprite = new Sprite(initialX, initialY, 40, 40, "player", Main.PLAYER_IMAGE);

        this.physics = new PhysicsController(initialX, initialY);
    }

    public void update() {
        physics.update();
        sprite.setX(physics.getPosition().getX());
        sprite.setY(physics.getPosition().getY());
    }

    // Getters/Setters
    public PhysicsController getPhysics() {
        return physics;
    }
    public Sprite getSprite() {
        return sprite;
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

    public int getHoldingWeapon() {
        return holdingWeapon;
    }
}
