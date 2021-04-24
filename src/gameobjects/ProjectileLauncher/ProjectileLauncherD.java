package gameobjects.ProjectileLauncher;

import gamemap.Room;
import gameobjects.Player;
import gameobjects.graphics.functionality.SingularImageSheet;
import gameobjects.monsters.Monster;
import gameobjects.physics.Camera;
import javafx.scene.layout.Pane;
import main.Main;

public class ProjectileLauncherD extends ProjectileLauncher {
    private static ProjectileLauncherD weapon;
    private int range;
    private int damage;
    private Player player;

    private static final SingularImageSheet BULLET_IMG = new SingularImageSheet(
            Main.getImageFrom("../gameobjects/graphics/sprites/bulletImg/" + "donutBox.png"));

    private ProjectileLauncherD(Player player) {
        super(player, 5, 10, BULLET_IMG, "fireball", true);
        this.player = player;
        this.range = 5;
        this.damage = 10;
    }

    public static ProjectileLauncherD getInstance(Player player) {
        if (weapon == null) {
            synchronized (ProjectileLauncherA.class) {
                if (weapon == null) {
                    weapon = new ProjectileLauncherD(player);
                }
            }
        }
        return weapon;
    }


}
