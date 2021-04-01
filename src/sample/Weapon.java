package sample;

/**
 * for the weapon in dungeon Crawler
 */
public class Weapon {
    private String name;
    private String description;
    private int damage;
    private int ammoRemaining;
    private int maxAmmo;

    /**
     * constructor for the weapon
     * @param weaponName name of the weapon
     * @param weaponDescription description of the weapon
     * @param weaponDamage damage of the weapon
     * @param maximumAmmo maximum ammo of the weapon
     */
    public Weapon(String weaponName, String weaponDescription, int weaponDamage, int maximumAmmo) {
        damage = weaponDamage;
        name = weaponName;
        description = weaponDescription;
        ammoRemaining = maximumAmmo;
        maxAmmo = maximumAmmo;
    }

    /**
     * setter for damage
     * @param damage new demage
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * decrement ammo of the weapon
     * @param byAmount the amount of ammo to be decremented
     */
    public void decreAmmo(int byAmount) {
        ammoRemaining = ammoRemaining - byAmount;
    }

    /**
     * reset remaining ammo to the weapon's maximum ammo
     */
    public void refillAmmo() {
        ammoRemaining = maxAmmo;
    }

    /**
     * getter for damage
     * @return damage of the weapon
     */
    public int getDamage() {
        return damage;
    }

    /**
     * getter for remaining ammo
     * @return remaining ammo
     */
    public int getAmmoRemaining() {
        return ammoRemaining;
    }

    /**
     * getter for name
     * @return name of the weapon
     */
    public String getName() {
        return name;
    }

    /**
     * getter for description
     * @return  description of the weapon
     */
    public String getDescription() {
        return description;
    }

    /**
     * toString
     * @return the string that represents the weapon
     */
    public String toString() {
        return name;
    }

    /**
     * if the name and the damge of two weapons are equal, they are the same weapon
     * @param x the comparing weapon
     * @return true if they are equal, otherwise false
     */
    public boolean equals(Weapon x) {
        return x.getName().equals(name) && x.getDamage() == damage;
    }

}
