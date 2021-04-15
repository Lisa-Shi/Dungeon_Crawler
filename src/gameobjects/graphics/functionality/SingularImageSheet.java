/**
 * collection of one gameobjects.graphics.image source
 */
package gameobjects.graphics.functionality;

import javafx.scene.image.Image;

public class SingularImageSheet extends ImageSheet {
    // Variables
    private ImageReel singleReel;

    // Constructors
    public SingularImageSheet(Image image) {
        singleReel = new ImageReel();
        singleReel.add(image);

        setInitialReel(singleReel);
    }

    // Getters
    public ImageReel getImage() {
        return singleReel;
    }
}
