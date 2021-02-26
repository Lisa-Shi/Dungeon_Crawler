package sample;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Player implements Physical {

    // Variables
    private String name;
    private List<Weapon> weaponList;
    private int difficulty;
    private int holdingWeapon; // index of weapon in the weapon list

    private PhysicsController physics;
    private Sprite sprite;


    public Player(String name, Weapon initialWeapon, int difficulty) {
        this(name, initialWeapon, 0,0);
        this.difficulty = difficulty;
    }

    public Player(String inputName, Weapon initialWeapon, double initialX, double initialY) {
        name = inputName;
        weaponList = new ArrayList<>();
        weaponList.add(initialWeapon);
        sprite = new Sprite((int) initialX, (int) initialY, 40, 40, "player", Main.PLAYER_IMAGE);

        this.physics = new PhysicsController(initialX, initialY);
    }

    public void update(Camera camera) {
        physics.update();
        sprite.setTranslateX(physics.getPosition().getX() - camera.getPhysics().getPosition().getX() + camera.getOffsetX() - 40 / 2);
        sprite.setTranslateY(physics.getPosition().getY() - camera.getPhysics().getPosition().getY() + camera.getOffsetY() - 40 / 2);
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public int getDifficulty(){ return difficulty;  }

    /**player is legal if the name is not empty string and difficulty is not -1
     *
     * @return if the player object is legal
     */
    public boolean isLegal(){
        return !(name.equals("") || difficulty == -1);
    }
}
