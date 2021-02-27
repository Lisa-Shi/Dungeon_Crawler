package sample;

import java.util.ArrayList;
import java.util.List;

public class Player implements Physical {

    // Variables
    private String name;
    private List<Weapon> weaponList;
    private int holdingWeapon; // index of weapon in the weapon list

    private PhysicsController physics;
    private Sprite sprite;
    private int money;

    public Player(String inputName, Weapon initialWeapon, double initialX, double initialY) {
        name = inputName;
        weaponList = new ArrayList<>();
        weaponList.add(initialWeapon);
        sprite = new Sprite((int) initialX, (int) initialY, Main.PLAYER_WIDTH,
                Main.PLAYER_HEIGHT, "player", Main.PLAYER_IMAGE);

        this.physics = new PhysicsController(initialX, initialY);
    }

    public void update(Camera camera) {
        physics.update();
        sprite.setTranslateX(physics.getPosition().getX() - camera.getPhysics().getPosition().getX()
                + camera.getOffsetX() - Main.PLAYER_WIDTH / 2);
        sprite.setTranslateY(physics.getPosition().getY() - camera.getPhysics().getPosition().getY()
                + camera.getOffsetY() - Main.PLAYER_HEIGHT / 2);
    }

    // Getters
    public PhysicsController getPhysics() {
        return physics;
    }
    public Sprite getSprite() {
        return sprite;
    }

    public void obtainNewWeapon(Weapon newWeapon) {
        weaponList.add(newWeapon);
    }

    public void equipWeapon(Weapon target) {
        holdingWeapon = weaponList.indexOf(target);
    }

    public List<Weapon> getWeaponList() {
        return weaponList;
    }

    public int getHoldingWeapon() {
        return holdingWeapon;
    }

    public int getMoney() {
        return money;
    }

    // Setters
    public void addMoney(int toAdd) {
        this.money += toAdd;
    }
    public void payMoney(int toPay) {
        this.money -= toPay;
    }
    public void setMoney(int money) {
        this.money = money;
    }
}
