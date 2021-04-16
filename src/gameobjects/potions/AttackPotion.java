package gameobjects.potions;

import gameobjects.Player;
import gameobjects.ProjectileLauncher.ProjectileLauncher;
import main.Main;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Attack potion for Dungeon Crawler. Can be held in inventory and when used, increases the damage of the weapon that
 * the player is wielding.
 */
public class AttackPotion extends Potion {
    private double duration;

    /**
     * Constructor for Attack Potion.
     */
    public AttackPotion() {
        super("AttackPotion",Main.ATTACK_POTION_AMP, Main.ATTACK_POTION);
    }

    @Override
    public void consume(Player player) {
        ProjectileLauncher playerWeapon = player.getHoldingWeapon();
        playerWeapon.setDamage(playerWeapon.getDamage() * getValue());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                playerWeapon.setDamage(playerWeapon.getDamage() / getValue());
            }
        }, (long) (duration * 10000));
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

}
