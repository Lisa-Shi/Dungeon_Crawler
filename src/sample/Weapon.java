package sample;

public class Weapon {
    private String name;
    private String description;
    private int damage;
    private int ammoRemaining;
    private int maxAmmo;

    public Weapon(String weaponName, String weaponDescription, int weaponDamage, int maximumAmmo){
        damage = weaponDamage;
        name = weaponName;
        description = weaponDescription;
        ammoRemaining = maximumAmmo;
        maxAmmo = maximumAmmo;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void decreAmmo(int byAmount) {
        ammoRemaining = ammoRemaining - byAmount;
    }

    public void refillAmmo(){
        ammoRemaining = maxAmmo;
    }

    public int getDamage() {
        return damage;
    }

    public int getAmmoRemaining() {
        return ammoRemaining;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
