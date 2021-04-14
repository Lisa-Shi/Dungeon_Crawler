package gameobjects.ProjectileLauncher;

import gamemap.Room;
import gameobjects.Player;
import gameobjects.graphics.functionality.ImageSheet;
import gameobjects.graphics.functionality.SingularImageSheet;
import javafx.scene.layout.Pane;
import main.Main;
import gameobjects.physics.Camera;

public abstract class ProjectileLauncher {
    private String name;
    private String description;

    public static ImageSheet defaultImg;
    public static ImageSheet bulletImg;
    Player player;
    Room room;
    Pane pane;
    int damage;
    int range;

    /**
     * Projectile Launcher constructor
     *
     * @param player shooting bullet (shooter)
     * @param range determines number of bounces and travel time
     * @param damage inflicted on monster
     * @param img of bullet
     */
    public ProjectileLauncher(Player player, int range, int damage, String img, String name) {
        this.player = player;
        this.range = range;
        this.damage = damage;
        this.name = name;
        look(img);
    }

    /**
     * Changes the image sheet
     *
     * @param img file name, ex: bullet.png
     */
    public void look(String img) {
        if (img == null) {
            bulletImg = defaultImg;
            return;
        }
        //Image = new Image(Main.class.getResource(name).toExternalForm());
        bulletImg = new SingularImageSheet(
                Main.getImageFrom("../gameobjects/graphics/sprites/bulletImg/" + img));
        if (defaultImg == null) {
            defaultImg = bulletImg;
        }
    }

    public void shoot(Room room, Pane pane, Camera camera) {
        Projectile bullet = new Projectile(player, room, pane, range, damage, bulletImg);
        room.add(bullet);
        pane.getChildren().add(bullet.getGraphics().getSprite());
        bullet.launch();
        bullet.update(camera);
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return "Weapon name: " + name + "\nDamage: " + damage + "\nRange: " + range;
    }

    /**
     * if the name and the damge of two weapons are equal, they are the same weapon
     * @param x the comparing weapon
     * @return true if they are equal, otherwise false
     */
    public boolean equals(ProjectileLauncher x) {
        return x.getName().equals(name) && x.getDamage() == damage;
    }
}
