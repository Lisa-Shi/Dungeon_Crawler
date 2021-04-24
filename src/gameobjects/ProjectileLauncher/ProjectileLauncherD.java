package gameobjects.ProjectileLauncher;

import gamemap.Room;
import gameobjects.Player;
import gameobjects.graphics.functionality.SingularImageSheet;
import gameobjects.monsters.Monster;
import gameobjects.physics.Camera;
import javafx.scene.layout.Pane;
import main.Main;

import java.util.Random;

public class ProjectileLauncherD extends ProjectileLauncher {
    private static ProjectileLauncherD weapon;
    private int range;
    private int damage;
    private Player player;



    private boolean randomPowerUp = false;

    private static SingularImageSheet BULLET_IMG = new SingularImageSheet(
            Main.getImageFrom("../gameobjects/graphics/sprites/" + "bulletM.png"));
    private static final SingularImageSheet defaultImg = new SingularImageSheet(
            Main.getImageFrom("../gameobjects/graphics/sprites/" + "bulletM.png"));
    private static final SingularImageSheet powerUp = new SingularImageSheet(
            Main.getImageFrom("../gameobjects/graphics/sprites/bulletImg/" + "light.png"));

    private ProjectileLauncherD(Player player) {
        super(player, 5, 10, BULLET_IMG, "trackB", true);
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

    public void increasePower() {
        int luck = 80;
        if (randomPowerUp) {
            int seed = 100;
            Random rand = new Random();
            int ranInt = rand.nextInt(seed);
            if (ranInt < luck) {
                BULLET_IMG = powerUp;
            }
        }
    }

    public void shoot(Room room, Pane pane, Camera camera) {
        if (randomPowerUp) {
            increasePower();
        }
        for (Monster m : room.getMonsters()) {
            Projectile bullet = new Projectile(player, room, pane, range * 2, damage * 2, BULLET_IMG);
            player.addBullet();
            room.add(bullet);
            pane.getChildren().add(bullet.getGraphics().getSprite());
            bullet.launchTowardsPoint(m.getPhysics().getPosition(), Main.ENEMY_BULLET_SPEED);
            bullet.update(camera);
        }
        BULLET_IMG = defaultImg;
    }

    public boolean isRandomPowerUp() {
        return randomPowerUp;
    }

    public void setRandomPowerUp(boolean b) {
        if (b) {
            this.setName("trackB2");
        }
        randomPowerUp = b;
    }

}
