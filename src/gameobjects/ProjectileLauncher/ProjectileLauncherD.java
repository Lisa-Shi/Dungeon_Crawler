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

    //only weapon that has a discrete number of bullets
    private int bulletsLeft = 20;

    private boolean randomPowerUp = false;

    private static SingularImageSheet BULLET_IMG = new SingularImageSheet(
            Main.getImageFrom("../gameobjects/graphics/sprites/" + "bulletM.png"));
    private static final SingularImageSheet defaultImg = new SingularImageSheet(
            Main.getImageFrom("../gameobjects/graphics/sprites/" + "bulletM.png"));
    private static final SingularImageSheet powerUp = new SingularImageSheet(
            Main.getImageFrom("../gameobjects/graphics/sprites/bulletImg/" + "light.png"));

    /**
     * Private constructor with properties of projectile launcher
     * @param player who has projectile launcher
     */
    private ProjectileLauncherD(Player player) {
        super(player, 5, 10, BULLET_IMG, "trackB", true);
        this.player = player;
        this.range = 5;
        this.damage = 10;
    }

    /**
     * Singleton design principle.
     * @param player that can have projectile launcher
     * @return instance of projectile launcher
     */
    public static ProjectileLauncherD getInstance(Player player) {
        if (weapon == null) {
            synchronized (ProjectileLauncherD.class) {
                if (weapon == null) {
                    weapon = new ProjectileLauncherD(player);
                }
            }
        }
        return weapon;
    }

    /**
     * Increases the power of projectile launcher.
     *
     * Will randomly shoot a "lucky" bullet
     */
    public void increasePower() {
        //luck / 100 = probability of getting lucky bullet
        int luck = 20;
        if (randomPowerUp) {
            Random rand = new Random();
            int ranInt = rand.nextInt(100);
            if (ranInt < luck) {
                BULLET_IMG = powerUp;
            }
        }
    }

    /**
     * Overrides shoot method in parent
     * @param room that player is in
     * @param pane of the stage
     * @param camera
     */
    public void shoot(Room room, Pane pane, Camera camera) {
        //checks to see if should power up
        if (randomPowerUp) {
            increasePower();
        }

        //finds the monsters in the room and directly shoots at them
        for (Monster m : room.getMonsters()) {

            //will only shoot if bullets are left
            if (bulletsLeft > 0) {
                System.out.println("shooting");
                Projectile bullet = new Projectile(player, room, pane, range * 2, damage * 2, BULLET_IMG);
                player.addBullet();
                room.add(bullet);
                pane.getChildren().add(bullet.getGraphics().getSprite());
                bullet.launchTowardsPoint(m.getPhysics().getPosition(), Main.ENEMY_BULLET_SPEED);
                bullet.update(camera);
                bulletsLeft--;
            } else {
                player.equipWeapon(player.getWeaponList().get(0));
            }
        }
        BULLET_IMG = defaultImg;
    }

    public boolean isRandomPowerUp() {
        return randomPowerUp;
    }
    public void addBullets() {
        bulletsLeft += 10;
    }

    /**
     * Changes the name of the projectile when can shoot lucky bullets
     *
     * Allows for css image change in inventory
     * @param b
     */
    public void setRandomPowerUp(boolean b) {
        if (b) {
            this.setName("trackB2");
        }
        randomPowerUp = b;
    }

}
