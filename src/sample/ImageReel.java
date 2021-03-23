package sample;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ImageReel {
    // Variables
    private ArrayList<Image> images;
    private int onImageIndex;

    // Constructors
    public ImageReel() {
        this.images = new ArrayList<>();
        this.onImageIndex = 0;
    }

    // Getters
    public Image getInitialImage() {
        onImageIndex = 0;
        return images.get(0);
    }
    public Image getNextImage() {
        if (images.size() == 0) {
            throw new NoSuchElementException("Cannot get the next image"
                    + " from an empty image reel.");
        }

        onImageIndex++;
        onImageIndex = (onImageIndex % images.size());
        return images.get(onImageIndex);
    }
    public int getSize() {
        return images.size();
    }

    // Setters
    public void add(Image image) {
        images.add(image);
    }
}
