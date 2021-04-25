package gameobjects.ProjectileLauncher;

import gameobjects.Player;
import gameobjects.graphics.functionality.SingularImageSheet;
import main.Main;

public class ProjectileLauncherB extends ProjectileLauncher {
    private static ProjectileLauncherB weapon;

    private static final SingularImageSheet BULLET_IMG = new SingularImageSheet(
            Main.getImageFrom("../gameobjects/graphics/sprites/bulletImg/" + "donut.png"));

    private ProjectileLauncherB(Player player) {
        super(player, 3, 3, BULLET_IMG, "donut", false);
    }

    public static ProjectileLauncherB getInstance(Player player) {
        if (weapon == null) {
            synchronized (ProjectileLauncherB.class) {
                if (weapon == null) {
                    weapon = new ProjectileLauncherB(player);
                }
            }
        }
        return weapon;
    }

}

