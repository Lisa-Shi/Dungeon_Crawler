package gameobjects;

import javafx.scene.image.Image;
import main.Main;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Attack potion for Dungeon Crawler. Can be held in inventory and when used, increases the damage of the weapon that
 * the player is wielding.
 */
public class AttackPotion implements Consumable {

    private Image image;
    private double duration;
    private double damageAmp;

    /**
     * Constructor for Attack Potion.
     */
    public AttackPotion() {
//        image =;
        duration = Main.ATTACK_POTION_DURATION;
        damageAmp = Main.ATTACK_POTION_AMP;
    }

    @Override
    public void consume() {
        player.getWeapon().setDamage(getDamage() * 2);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                player.getWeapon().setDamage(getDamage() / 2);
            }
        }, duration * 10000);
    }

    /**
     * Getter method for image variable.
     * @return image of the potion
     */
    public Image getImage() {
        return image;
    }

    /**
     * Getter method for the duration variable.
     * @return the duration of the potion
     */
    public double getDuration() {
        return duration;
    }

    /**
     * Setter for duration variable.
     * @param time time that the potion will last
     */
    public void setDuration(int time) {
        duration = time;
    }

    /**
     * Getter method for the damageAmp variable.
     * @return the damage amplifier of the potion
     */
    public double getDamageAmp() {
        return damageAmp;
    }

    /**
     * Setter for damageAmp variable.
     * @param damage damage that the potion will increase
     */
    public void setDamageAmp(double damage) {
        this.damageAmp = damage;
    }


}
