/**
 *player object
 */
package gameobjects;

import gameobjects.ProjectileLauncher.ProjectileLauncher;
import gameobjects.ProjectileLauncher.ProjectileLauncherA;
import gameobjects.monsters.Monster;
import gameobjects.physics.collisions.Collideable;
import gameobjects.physics.collisions.DynamicCollisionBox;
import gameobjects.physics.collisions.Passable;
import gameobjects.physics.collisions.RectangleWireframe;
import gameobjects.graphics.functionality.CharacterImageSheet;
import javafx.scene.layout.Pane;
import gameobjects.physics.Camera;
import gameobjects.graphics.functionality.Drawable;
import main.Main;
import gamemap.Room;
import gameobjects.physics.Vector2D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Player extends GameObject implements Damageable, Collideable, Drawable {

    // Variables
    private String name;
    private List<ProjectileLauncher> weaponList;
    private int difficulty;
    private int holdingWeapon; // index of weapon in the weapon list
    private int health;
    private int damageAmt = 1;
    private Vector2D direction;

    private DynamicCollisionBox collisionBox;

    private int money;

    /**
     * Constructs the Player object that the user will control
     * to explore and escape the dungeon
     *
     * @param name Player name
     * @param room the room player locates
     * @param initialX x-location player starts at
     * @param initialY y-location player starts at
     * @param difficulty difficulty for the game
     */
    public Player(String name, Room room,
                  double initialX, double initialY,  int difficulty) {
        super(room, initialX, initialY, Main.PLAYER_WIDTH / 2,
                Main.PLAYER_HEIGHT / 2, Main.PLAYER_IMAGE_SHEET);
        this.name = name;
        weaponList = new ArrayList<>();
        holdingWeapon = 0;

        direction = new Vector2D(0, -1);
        this.health = Main.PLAYER_STARTING_HEALTH;

        this.difficulty = difficulty;
        giveMoney(difficulty);

        this.collisionBox = new DynamicCollisionBox(getPhysics(),
                new RectangleWireframe(Main.PLAYER_WIDTH, Main.PLAYER_HEIGHT));
        this.collisionBox.generate();
    }

    private void giveMoney(int difficulty) {
        if (difficulty == 1) {
            money = 100;
        } else if (difficulty == 2) {
            money = 60;
        } else {
            money = 20;
        }
    }

    /**
     * shoot bullet
     * @param room the room player is in
     * @param pane the overall pane
     * @param camera the camera
     * @param monsters list of monster
     */
    public void launchProjectile(Room room, Pane pane,
                                 Camera camera, LinkedList<Monster> monsters) {

        if (weaponList.size() < 0 || weaponList.get(holdingWeapon) == null) {
            ProjectileLauncher weapon = new ProjectileLauncherA(this);
            weaponList.add(weapon);
            holdingWeapon = weaponList.size() - 1;
        }
        ProjectileLauncher weapon = weaponList.get(holdingWeapon);
        weapon.shoot(room, pane, camera);
    }

    /**
     * Updates the player's position based on its gameobjects.physics as well
     * as the camera's
     *
     * @param camera Camera used to view the game
     *
     */
    public void update(Camera camera) {
        getPhysics().update();

        updateSprite(camera);

        if (getPhysics().getVelocity().len() > Main.MAX_PLAYER_SPEED) {
            Vector2D relenVel = getPhysics().getVelocity().relen(Main.MAX_PLAYER_SPEED);
            getPhysics().setVelocity(relenVel);
        }
    }

    public void update(Camera camera, LinkedList<Collideable> collideables) {
        update(camera);
        for (Collideable c : collideables) {
            if (c instanceof Passable && getCollisionBox().collidedWith(c.getCollisionBox())) {
                ((Passable) c).collisionWithPlayerEvent(this);
            }
        }
        getCollisionBox().raytraceCollision(getPhysics(), collideables);
        updateSprite(camera);
    }

    private void updateSprite(Camera camera) {
        getGraphics().getSprite().setTranslateX(getPhysics().getPosition().getX()
                - camera.getPhysics().getPosition().getX()
                + camera.getOffsetX() - Main.PLAYER_WIDTH / 2);
        getGraphics().getSprite().setTranslateY(getPhysics().getPosition().getY()
                - camera.getPhysics().getPosition().getY()
                + camera.getOffsetY() - Main.PLAYER_HEIGHT / 2);
    }

    /**
     * deduct player health point
     * @param healthDamage amount of hp to deduct from player
     */
    @Override
    public void hurt(int healthDamage) {
        health -= (healthDamage * damageAmt);
    }

    /**player is legal if the name is not empty string and difficulty is not -1
     *
     * @return if the player object is legal
     */
    public boolean isLegal() {
        return !(name.equals("") || difficulty == -1 || name.trim().equals(""));
    }

    // Getters
    /**
     * @return Collision box
     */
    @Override
    public DynamicCollisionBox getCollisionBox() {
        return collisionBox;
    }

    /**
     * @param newWeapon Weapon to add to player's collection
     */
    public void obtainNewWeapon(ProjectileLauncher newWeapon) {
        weaponList.add(newWeapon);
    }

    /**
     * @param target Weapon player will now hold
     */
    public void equipWeapon(ProjectileLauncher target) {
        holdingWeapon = weaponList.indexOf(target);
    }

    /**
     * @return Player's collection of weapons
     */
    public List<ProjectileLauncher> getWeaponList() {
        return weaponList;
    }

    /**
     * @return Index of the weapon the player is holding
     */
    public int getHoldingWeaponIndex() {
        return holdingWeapon;
    }

    /**
     * @return Index of the weapon the player is holding
     */
    public ProjectileLauncher getHoldingWeapon() {
        return weaponList.get(holdingWeapon);
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

    public CharacterImageSheet getSpriteSheet() {
        return (CharacterImageSheet) super.getSpriteSheet();
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

    public void setDirection(Vector2D direction) {
        this.direction = direction;
    }
    public Vector2D getDirection() {
        return direction;
    }
    @Override
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamageAmt(int damageAmt) {
        this.damageAmt = health;
    }

}
