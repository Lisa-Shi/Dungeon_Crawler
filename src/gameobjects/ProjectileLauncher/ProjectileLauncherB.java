package gameobjects.ProjectileLauncher;

import gameobjects.Player;

public class ProjectileLauncherB extends ProjectileLauncher {
    private static ProjectileLauncherB weapon;

    private ProjectileLauncherB(Player player) {
        super(player, 3, 3, "donut.png", "donut");
    }

    public static ProjectileLauncherB getInstance(Player player) {
        if (weapon == null) {
            synchronized (ProjectileLauncherA.class) {
                if (weapon == null) {
                    weapon = new ProjectileLauncherB(player);
                }
            }
        }
        return weapon;
    }

}

