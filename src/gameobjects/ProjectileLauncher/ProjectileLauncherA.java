package gameobjects.ProjectileLauncher;

import gameobjects.Player;

public class ProjectileLauncherA extends ProjectileLauncher {
    private static ProjectileLauncherA weapon;

    private ProjectileLauncherA(Player player) {
        super(player, 5, 1, "fire.png", "fireball");
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
