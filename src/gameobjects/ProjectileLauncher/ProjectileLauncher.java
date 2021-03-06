package gameobjects.ProjectileLauncher;

import gamemap.Room;
import gameobjects.Player;
import gameobjects.graphics.functionality.SingularImageSheet;
import gameobjects.monsters.Monster;
import javafx.scene.layout.Pane;
import gameobjects.physics.Camera;
import main.Main;

public abstract class ProjectileLauncher {
    private String name;
    private String description;

    private Player player;
    private Room room;
    private Pane pane;
    private int damage;
    private int range;
    private int price;
    private boolean isDropped;

    public SingularImageSheet getImage() {
        return image;
    }

    public void setImage(SingularImageSheet image) {
        this.image = image;
    }

    private SingularImageSheet image;
    private boolean tracking;
    /**
     * Projectile Launcher constructor
     *
     * @param player shooting bullet (shooter)
     * @param range determines number of bounces and travel time
     * @param damage inflicted on monster
     * @param image of bullet
     * @param name of weapon
     */
    public ProjectileLauncher(Player player, int range, int damage,
                              SingularImageSheet image, String name, boolean tracking) {
        this.player = player;
        this.range = range;
        this.damage = damage;
        this.name = name;
        this.image = image;
        this.price = (range % 3 + 1) * (damage % 3 + 1) * 5;
        this.tracking = tracking;
    }

    public ProjectileLauncher(Monster monster, SingularImageSheet image) {
        this.range = Main.ENEMY_BULLET_BOUNCES_UNTIL_EXPIRATION;
        this.damage = Main.ENEMY_BULLET_DAMAGE;
        this.name = "monster";
        this.image = image;
    }


    public void shoot(Room room, Pane pane, Camera camera) {
        if (tracking) {
            for (Monster m : room.getMonsters()) {
                Projectile bullet = new Projectile(player, room, pane, range, damage, image);
                player.addBullet();
                room.add(bullet);
                pane.getChildren().add(bullet.getGraphics().getSprite());
                bullet.launchTowardsPoint(m.getPhysics().getPosition(), Main.ENEMY_BULLET_SPEED);
                bullet.update(camera);
            }

        } else {
            Projectile bullet = new Projectile(player, room, pane, range, damage, image);
            player.addBullet();
            room.add(bullet);
            pane.getChildren().add(bullet.getGraphics().getSprite());
            bullet.launch();
            bullet.update(camera);
        }
    }
    public void setDamage(int input) {
        this.damage = input;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return "Weapon name: " + name + "\nDamage: " + damage + "\nRange: " + range;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ProjectileLauncher && o.getClass().equals(this.getClass())) {
            ProjectileLauncher other = (ProjectileLauncher) o;

            boolean isEqual = name.equals(other.getName())
                    && damage == other.getDamage() && range == other.getRange();
            return isEqual;
        }
        return false;
    }

    /**
     * if the name and the damge of two weapons are equal, they are the same weapon
     * @param x the comparing weapon
     * @return true if they are equal, otherwise false
     */
    public boolean equals(ProjectileLauncher x) {
        return x.getName().equals(name) && x.getDamage() == damage;
    }

    public boolean getIsDropped() {
        return isDropped;
    }

    public void setIsDropped(boolean isDropped) {
        this.isDropped = isDropped;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
