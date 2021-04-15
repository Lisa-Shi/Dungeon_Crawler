package gameobjects.ProjectileLauncher;

import gameobjects.Player;

public class ProjectileLauncherC extends ProjectileLauncher {
    private static ProjectileLauncherC weapon;

    public ProjectileLauncherC(Player player) {
        super(player, 1, 5, "bulletP.png", "water");
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
