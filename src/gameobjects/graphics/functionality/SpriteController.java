/**
 * this class is responsible for any thing that is related to
 * visualization of the game object
 */

package gameobjects.graphics.functionality;

import java.util.Timer;
import java.util.TimerTask;

public class SpriteController {
    // Variables
    /**sprite is essentially an gameobjects.graphics.image with position
     */
    private Sprite sprite;
    private ImageReel reel;
    private static Timer animationTimer = new Timer();

    // Constructors
    public SpriteController(Sprite editSprite, ImageReel initialReel) {
        this.reel = initialReel;
        this.sprite = editSprite;
        sprite.setImage(initialReel.getInitialImage());
        startAnimation();
    }

    // Misc.
    public void startAnimation() {
        animationTimer.schedule(nextAnimationFrame, 100, 100);
    }
    private TimerTask nextAnimationFrame = new TimerTask() {
        @Override
        public void run() {
            sprite.setImage(reel.getNextImage());
        }
    };

    // Getters
    public Sprite getSprite() {
        return sprite;
    }
    public ImageReel getCurrentReel() {
        return reel;
    }

    // Setters
    public void setCurrentReel(ImageReel reel) {
        if (reel != this.reel) {
            this.reel = reel;
            sprite.setImage(reel.getInitialImage());
        }
    }
}
