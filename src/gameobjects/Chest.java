package gameobjects;

import javafx.scene.image.Image;
import main.Main;

public class Chest {
    // Variables
    private Image image = Main.CHEST_IMAGE.get(0);
    private int money;
    private String treasure;
    private boolean isOpen = false;

    // Constructors
    public Chest(int money, String treasure) {
        this.money = money;
        this.treasure = treasure;
    }

    // Misc.
    public int takeMoney() {
        int givePlayer = money;
        money = 0;
        if (treasure.equals("")) {
            isOpen = true;
            image = Main.CHEST_IMAGE.get(2);
        }
        return givePlayer;
    }
    public String takeTreasure() {
        String givePlayer = treasure;
        treasure = "";
        isOpen = true;
        image = Main.CHEST_IMAGE.get(2);
        return givePlayer;
    }

    // Getters
    public Image getImage() {
        return image;
    }
    public boolean isOpen() {
        return isOpen;
    }
}
