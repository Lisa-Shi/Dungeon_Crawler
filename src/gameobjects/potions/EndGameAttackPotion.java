package gameobjects.potions;

import gameobjects.Player;
import gameobjects.ProjectileLauncher.ProjectileLauncher;
import main.Main;

/**
 * Potion that permanently triples player damage. It is only obtained by defeating the final boss.
 */
public class EndGameAttackPotion extends Potion {

    /**
     * Constructor for Attack Potion that is received after defeating the final boss.
     */
    public EndGameAttackPotion() {
        super("SuperAttackPotion", Main.END_GAME_ATTACK_POTION_AMP, Main.END_GAME_ATTACK_POTION);
    }

    @Override
    public void consume(Player player) {
        player.addPotionConsumed();
        ProjectileLauncher playerWeapon = player.getHoldingWeapon();
        playerWeapon.setDamage(playerWeapon.getDamage() * getValue());
    }

}
