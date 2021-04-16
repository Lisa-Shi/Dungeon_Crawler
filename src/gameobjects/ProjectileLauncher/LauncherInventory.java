package gameobjects.ProjectileLauncher;

import java.util.LinkedList;

public class LauncherInventory {
    private static LinkedList<ProjectileLauncher> list = new LinkedList<>();

    public static LinkedList<ProjectileLauncher> getInstance() {
        return list;
    }

}
