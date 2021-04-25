package gameobjects.ProjectileLauncher;

import gameobjects.Player;
import gameobjects.graphics.functionality.SingularImageSheet;
import main.Main;

public class ProjectileLauncherC extends ProjectileLauncher {
    private static ProjectileLauncherC weapon;

    private static final SingularImageSheet BULLET_IMG = new SingularImageSheet(
            Main.getImageFrom("../gameobjects/graphics/sprites/bulletImg/" + "bulletP.png"));

    public ProjectileLauncherC(Player player) {
        super(player, 1, 5, BULLET_IMG, "water");
    }

    public static ProjectileLauncherC getInstance(Player player) {
        if (weapon == null) {
            synchronized (ProjectileLauncherA.class) {
                if (weapon == null) {
                    weapon = new ProjectileLauncherC(player);
                }
            }
        }
        return weapon;
    }
}
