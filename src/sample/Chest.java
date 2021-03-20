package sample;


import javafx.scene.image.Image;

public class Chest{
    private Image image = Main.CHEST_IMAGE.get(0);
    private int money;
    private String treasure;
    private boolean isOpen = false;
    public Chest(int money, String treasure){
        this.money = money;
        this.treasure = treasure;
    }
    public Image getImage(){
        return image;
    }
    public int takeMoney(){
        int givePlayer = money;
        money = 0;
        if( treasure.equals("")){
            isOpen = true;
            image = Main.CHEST_IMAGE.get(2);
        }
        return givePlayer;
    }
    public String takeTreasure(){
        String givePlayer = treasure;
        treasure = "";
        isOpen = true;
        image = Main.CHEST_IMAGE.get(2);
        return givePlayer;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
