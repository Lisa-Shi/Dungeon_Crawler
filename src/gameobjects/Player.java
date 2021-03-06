/**
 *player object
 */
package gameobjects;

import gameobjects.ProjectileLauncher.LauncherInventory;
import gameobjects.ProjectileLauncher.ProjectileLauncher;
import gameobjects.ProjectileLauncher.ProjectileLauncherA;
import gameobjects.monsters.Monster;
import gameobjects.physics.collisions.Collideable;
import gameobjects.physics.collisions.DynamicCollisionBox;
import gameobjects.physics.collisions.Passable;
import gameobjects.physics.collisions.RectangleWireframe;
import gameobjects.graphics.functionality.CharacterImageSheet;
import gameobjects.potions.AttackPotion;
import gameobjects.potions.HealthPotion;
import gameobjects.potions.Potion;
import javafx.scene.layout.Pane;
import gameobjects.physics.Camera;
import gameobjects.graphics.functionality.Drawable;
import main.Main;
import gamemap.Room;
import gameobjects.physics.Vector2D;

import java.util.*;

public class Player extends GameObject implements Damageable, Collideable, Drawable, Openable, Interactable {

    // Variables
    private String name;
    private List<ProjectileLauncher> weaponList;
    private Map<Potion, Integer> inventory = new TreeMap<>();
    private int difficulty;
    private int holdingWeapon; // index of weapon in the weapon list
    private int health;
    private int maxHealth;
    private int damageAmt = 1;
    private Vector2D direction;
    private boolean moveability = true;
    private DynamicCollisionBox collisionBox;
    private int money;

    private int bulletShot = 0;
    private int monsterKilled = 0;
    private int challengeRooms = 0;
    private int potionsConsumed = 0;

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
        this.maxHealth = Main.PLAYER_STARTING_HEALTH;
        this.difficulty = difficulty;
        giveMoney(difficulty);

        this.collisionBox = new DynamicCollisionBox(getPhysics(),
                new RectangleWireframe(Main.PLAYER_WIDTH, Main.PLAYER_HEIGHT));
        this.collisionBox.generate();
        inventory.put(new AttackPotion(), 5);
        inventory.put(new HealthPotion(), 10);
    }

    public int getMaxHealth() {
        return maxHealth;
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



    @Override
    public void buttonAction(Player player, Potion potion) {
        potion.consume(player);
        loseItem(potion);
    }

    public Map<Potion, Integer> getInventory() {
        return inventory;
    }

    public void getItem(Potion potion) {
        if (inventory.containsKey(potion)) {
            inventory.put(potion, inventory.get(potion) + 1);
        } else {
            inventory.put(potion, 1);
        }
    }

    public void getItem(Map<Potion, Integer> list) {
        for (Map.Entry<Potion, Integer> entry : list.entrySet()) {
            if (inventory.containsKey(entry.getKey())) {
                inventory.put(entry.getKey(), inventory.get(entry.getKey()) + entry.getValue());
            } else {
                inventory.put(entry.getKey(), entry.getValue());
            }
        }
    }
    @Override
    public void open(Player player, Pane pane) {
        Inventory inventory = Inventory.getInstance(player, pane, player);
        inventory.show();
    }

    //@Override
    public void loseItem(Potion potion) {
        if (inventory.containsKey(potion)) {
            if (inventory.get(potion) - 1 != 0) {
                inventory.put(potion, inventory.get(potion) - 1);
            } else {
                inventory.remove(potion);
            }
        }
    }

    /**
     * shoot bullet
     * @param room the room player is in
     * @param pane the overall pane
     * @param camera the camera
     */
    public void launchProjectile(Room room, Pane pane, Camera camera) {

        if (weaponList.size() < 0 || weaponList.get(holdingWeapon) == null) {
            ProjectileLauncher weapon = ProjectileLauncherA.getInstance(this);
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

        if (getPhysics().getVelocity().len() > Main.MAX_PLAYER_SPEED * Main.powerUpSpeed) {
            Vector2D relenVel = getPhysics().getVelocity().relen(Main.MAX_PLAYER_SPEED * Main.powerUpSpeed);
            getPhysics().setVelocity(relenVel);
        }
    }
    public void setMoveability(boolean input) {
        moveability = input;
    }

    public boolean isMoveable() {
        return moveability;
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

    public void addBullet() {
        bulletShot++;
    }

    public void addMonsterKilled() {
        monsterKilled++;
    }

    public int getMonsterKilled() {
        return monsterKilled;
    }

    public void addPotionConsumed() {
        potionsConsumed++;
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
        LauncherInventory.getInstance().remove(newWeapon);
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

    public int getBulletShot() {
        return bulletShot;
    }

    public int getChallengeRooms() {
        return challengeRooms;
    }

    public void addChallenge() {
        challengeRooms++;
    }

    public int getPotionsConsumed() {
        return potionsConsumed;
    }
}
