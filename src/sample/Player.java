package sample;

import java.util.ArrayList;
import java.util.List;

public class Player implements Physical {

    // Variables
    private String name;
    private List<Weapon> weaponList;
    private int difficulty;
    private int holdingWeapon; // index of weapon in the weapon list

    private PhysicsController physics;
    private Sprite sprite;
    private int money;


    /**
     * Constructs the Player object that the user will control
     * to explore and escape the dungeon
     *
     * @param name Player name
     * @param initialWeapon Weapon player starts game with
     * @param difficulty Difficulty player is playing on
     *
     */


    /**
     * Constructs the Player object that the user will control
     * to explore and escape the dungeon
     *
     * @param name Player name
     * @param initialWeapon Weapon player starts game with
     * @param initialX x-location player starts at
     * @param initialY y-location player starts at
     * @param difficulty difficulty for the game
     */
    public Player(String name, Weapon initialWeapon,
                  double initialX, double initialY,  int difficulty) {
        this.name = name;
        weaponList = new ArrayList<>();
        weaponList.add(initialWeapon);
        sprite = new Sprite((int) initialX, (int) initialY, Main.PLAYER_WIDTH,
                Main.PLAYER_HEIGHT, Main.PLAYER_IMAGE);

        this.physics = new PhysicsController(initialX, initialY);
        this.difficulty = difficulty;
        if (difficulty == 1) {
            money = 100;
        } else if (difficulty == 2) {
            money = 60;
        } else {
            money = 20;
        }
    }

    /**
     * Updates the player's position based on its physics as well
     * as the camera's
     *
     * @param camera Camera used to view the game
     *
     */
    public void update(Camera camera) {
        physics.update();
        sprite.setTranslateX(physics.getPosition().getX() - camera.getPhysics().getPosition().getX()
                + camera.getOffsetX() - Main.PLAYER_WIDTH / 2);
        sprite.setTranslateY(physics.getPosition().getY() - camera.getPhysics().getPosition().getY()
                + camera.getOffsetY() - Main.PLAYER_HEIGHT / 2);
    }

    /**player is legal if the name is not empty string and difficulty is not -1
     *
     * @return if the player object is legal
     */
    public boolean isLegal() {
        return !(name.equals("") || difficulty == -1);
    }

    // Getters
    /**
     * @return Physics information
     */
    public PhysicsController getPhysics() {
        return physics;
    }
    /**
     * @return Sprite associated with the player
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * @param newWeapon Weapon to add to player's collection
     */
    public void obtainNewWeapon(Weapon newWeapon) {
        weaponList.add(newWeapon);
    }

    /**
     * @param target Weapon player will now hold
     */
    public void equipWeapon(Weapon target) {
        holdingWeapon = weaponList.indexOf(target);
    }

    /**
     * @return Player's collection of weapons
     */
    public List<Weapon> getWeaponList() {
        return weaponList;
    }

    /**
     * @return Index of the weapon the player is holding
     */
    public int getHoldingWeapon() {
        return holdingWeapon;
    }

    /**
     * @return Player's money
     */
    public int getMoney() {
        return money;
    }

    /**
     * @return Player's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return Difficult player is playing on
     */
    public int getDifficulty() {
        return difficulty;
    }


    // Setters
    /**
     * @param toAdd Money to add to player's stash
     */
    public void addMoney(int toAdd) {
        this.money += toAdd;
    }
    /**
     * @param toPay Money to pay from player's stash
     */
    public void payMoney(int toPay) {
        this.money -= toPay;
    }
    /**
     * @param money How much money the player will now have
     */
    public void setMoney(int money) {
        this.money = money;
    }
}
