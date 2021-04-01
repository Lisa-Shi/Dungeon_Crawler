package sample;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class monsterHPListener implements PropertyChangeListener {
    private double health;
    private Rectangle hp;
    private monsterHPListener() {}
    public monsterHPListener(Rectangle hp) { this.hp = hp;}
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.setHealth((double) evt.getNewValue());
        hp.setWidth(this.health);
    }
    public void setHealth(double health) { this.health = health; }
}
