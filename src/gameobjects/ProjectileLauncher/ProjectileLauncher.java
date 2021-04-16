package gameobjects.ProjectileLauncher;

import com.sun.java.swing.plaf.windows.WindowsDesktopPaneUI;
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

    private Player player;
    private Room room;
    private Pane pane;
    private int damage;
    private int range;
    private int price;
    private boolean isDropped;
    private SingularImageSheet image;
    /**
     * Projectile Launcher constructor
     *
     * @param player shooting bullet (shooter)
     * @param range determines number of bounces and travel time
     * @param damage inflicted on monster
     * @param img of bullet
     * @param name of weapon
     */
    public ProjectileLauncher(Player player, int range, int damage, SingularImageSheet image, String name) {
        this.player = player;
        this.range = range;
        this.damage = damage;
        this.name = name;
        this.image = image;
        this.price = (range % 3 + 1) * (damage % 3 + 1) * 5;
        //look(img);
        //LauncherInventory.getInstance().add(this);
    }

    /**
     * Changes the image sheet
     *
     * @param img file name, ex: bullet.png
     */
    public void look(String img) {
//        if (img == null) {
//            bulletImg = defaultImg;
//            return;
//        }
        //Image = new Image(Main.class.getResource(name).toExternalForm());
//        bulletImg = new SingularImageSheet(
//                Main.getImageFrom("../gameobjects/graphics/sprites/bulletImg/" + img));
//        if (defaultImg == null) {
//            defaultImg = bulletImg;
//        }
    }

    public void shoot(Room room, Pane pane, Camera camera) {
        Projectile bullet = new Projectile(player, room, pane, range, damage, image);
        room.add(bullet);
        pane.getChildren().add(bullet.getGraphics().getSprite());
        bullet.launch();
        bullet.update(camera);
    }
    public void setDamage(int input){
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

    public String getDescription() {
        return "Weapon name: " + name + "\nDamage: " + damage + "\nRange: " + range;
    }

    @Override
    public boolean equals (Object o) {
        if (o instanceof ProjectileLauncher && o.getClass().equals(this.getClass())) {
            ProjectileLauncher other = (ProjectileLauncher) o;

            boolean isEqual = name.equals(other.getName()) &&
                    damage == other.getDamage() && range == other.getRange();
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
