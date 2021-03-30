package sample;
import javafx.scene.control.ProgressBar;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class monsterHPListener implements PropertyChangeListener {
    private double health;
    private ProgressBar hp;
    private monsterHPListener() {}
    public monsterHPListener(ProgressBar hp) { this.hp = hp;}
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.setHealth((double) evt.getNewValue());
        hp.setProgress(this.health);
    }
    public void setHealth(double health) { this.health = health; }
}
