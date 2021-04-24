package gameobjects.ProjectileLauncher;

import gameobjects.Player;
import gameobjects.graphics.functionality.SingularImageSheet;
import main.Main;

public class EndGameProjectileLauncher extends ProjectileLauncher {

    private static EndGameProjectileLauncher weapon;

    private static final SingularImageSheet BULLET_IMG = new SingularImageSheet(
            Main.getImageFrom("../gameobjects/graphics/sprites/bulletImg/" + "bulletEndGame.png"));

    /**
     * Projectile Launcher constructor
     *
     * @param player shooting bullet (shooter)
     */
    public EndGameProjectileLauncher(Player player) {
        super(player, 10, 5, BULLET_IMG, "God Gun");
    }

    public static EndGameProjectileLauncher getInstance(Player player) {
        if (weapon == null) {
            synchronized (EndGameProjectileLauncher.class) {
                if (weapon == null) {
                    weapon = new EndGameProjectileLauncher(player);
                }
            }
        }
        return weapon;
    }
}
