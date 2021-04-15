package gameobjects;

import javafx.scene.image.Image;
//you can remove my comment after finishing
//NL:
// it will be great if you do it in the way we did monster
// -> have a potion class that implements consumable
// ->subclass for each type of potion

//in the abstract parent potion class
//please implement equal and hashcode.
//two potions are equal if the type and the integer value are the same
//Ex AttackPotion, 10 == AttackPotion, 10
//Ex AttackPotion, 5 != AttackPotion, 10
public class AttackPotion implements Consumable {
    //constructor
    //for potion, we need the image and a integer value
    //Image should come from Main Ex. Main.AttackPotion
    //please see monster for similar implementation
    private Image image;
    //with consume you can do it in two ways.
    //A: pass player as parameter to the consume()
    //B: make a method in player class consumer() and then pass the potion as parameter
    //A is recommended. because if you make a method in player, you need to do brunch of
    //  if statement to change different properties of player
    //In B, you can do player.setProperty();
    // you might need to implement the setProperty() methods in player.
    @Override
    public void consume() {

    }
    //need two getImage()
    //one return Image object
    //one return ImageView object
    public Image getImage() {
        return image;
    }
}
