package sample;

public abstract class ImageSheet {
    private ImageReel initialReel;

    public ImageReel getInitialReel() {
        return initialReel;
    }

    protected void setInitialReel(ImageReel reel) {
        this.initialReel = reel;
    }
}
