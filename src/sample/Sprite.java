package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite extends ImageView {
    private int x;
    private int y;
    /**
     * Links an image to code, making its behavior easy to modify
     * Heavily adapted from https://www.youtube.com/watch?v=FVo1fm52hz0
     *
     * @param x Absolute x-location of sprite
     * @param y Absolute y-location of sprite
     * @param w Absolute width of sprite
     * @param h Absolute height of sprite
     * @param img Image representing the sprite
     */
    Sprite(int x, int y, int w, int h, Image img) {
        super(img);

        this.setFitWidth(w);
        this.setFitHeight(h);

        this.x = x;
        this.y = y;

        setTranslateX(x);
        setTranslateY(y);
    }

}