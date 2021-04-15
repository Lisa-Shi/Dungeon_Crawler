package gameobjects.graphics.functionality;

import javafx.scene.image.Image;

public class DirectionalImageSheet extends ImageSheet {
    // Variables
    private ImageReel leftImage;
    private ImageReel rightImage;
    private ImageReel upImage;
    private ImageReel downImage;

    // Constructors
    public DirectionalImageSheet() {

    }
    public DirectionalImageSheet(Image image) {
        leftImage = new ImageReel();
        leftImage.add(image);

        rightImage = new ImageReel();
        rightImage.add(image);

        upImage = new ImageReel();
        upImage.add(image);

        downImage = new ImageReel();
        downImage.add(image);

        setInitialReel(downImage);
    }

    // Getters
    public ImageReel getLeftImage() {
        return leftImage;
    }
    public ImageReel getRightImage() {
        return rightImage;
    }
    public ImageReel getUpImage() {
        return upImage;
    }
    public ImageReel getDownImage() {
        return downImage;
    }

    // Setters
    public void setLeftImage(ImageReel leftImage) {
        this.leftImage = leftImage;
    }
    public void setRightImage(ImageReel rightImage) {
        this.rightImage = rightImage;
    }
    public void setUpImage(ImageReel upImage) {
        this.upImage = upImage;
    }
    public void setDownImage(ImageReel downImage) {
        this.downImage = downImage;
        setInitialReel(downImage);
    }
}
