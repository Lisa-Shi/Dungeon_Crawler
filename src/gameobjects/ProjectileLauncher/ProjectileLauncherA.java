package gameobjects.ProjectileLauncher;

import gameobjects.Player;
import gameobjects.graphics.functionality.SingularImageSheet;
import main.Main;

public class ProjectileLauncherA extends ProjectileLauncher {
    private static ProjectileLauncherA weapon;

    private static final SingularImageSheet BULLET_IMG = new SingularImageSheet(
            Main.getImageFrom("../gameobjects/graphics/sprites/bulletImg/" + "fire.png"));

    private ProjectileLauncherA(Player player) {
        super(player, 5, 1, BULLET_IMG, "fireball", false);
    }

    public static ProjectileLauncherA getInstance(Player player) {
        if (weapon == null) {
            synchronized (ProjectileLauncherA.class) {
                if (weapon == null) {
                    weapon = new ProjectileLauncherA(player);
                }
            }
        }
        return weapon;
    }


}
