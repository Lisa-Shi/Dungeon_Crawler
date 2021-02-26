package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite extends ImageView {
    boolean dead = false;
    final String type;

    Sprite(int x, int y, int w, int h, String type, Image img) {
        super(img);

        this.type = type;
        this.setFitWidth(w);
        this.setFitHeight(h);

        setTranslateX(x);
        setTranslateY(y);
    }

    public void moveLeft() {
        setTranslateX(getTranslateX() - 5);
    }

    public void moveRight() {
        setTranslateX(getTranslateX() + 5);
    }

    public void moveUp() {
        setTranslateY(getTranslateY() - 5);
    }

    public void moveDown() {
        setTranslateY(getTranslateY() + 5);
    }
}